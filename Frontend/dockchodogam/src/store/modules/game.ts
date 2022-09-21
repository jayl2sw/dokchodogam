import axios from 'axios'
import { Module } from 'vuex'
import { RootState } from '../index'

export interface gameState {
  enemyInfo: object
  userDeck: Array<object>
}

export const game: Module<gameState, RootState> = {
  state: {
    enemyInfo: {},
    userDeck: []
  },
  getters: {
    enemyInfo: (state) => state.enemyInfo,
    userDeck: (state) => state.userDeck
  },
  mutations: {
    SET_ENEMYINFO(state, enemyInfo) {
      state.enemyInfo = enemyInfo
      console.log(enemyInfo)
    },
    SET_USERDECK(state, userDeck) {
      state.userDeck = userDeck
      console.log(userDeck)
    }
  },
  actions: {
    fetchEnemyInfo({ commit }, enemyInfo) {
      commit('SET_ENEMYINFO', enemyInfo)
    },
    fetchUserDeck({ commit }) {
      axios
        .get('http://localhost:8081/api/v1/game/deck/myInfo', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          commit('SET_USERDECK', res.data)
          console.log('내 덱', res.data)
        })
        .catch((err) => console.log(err))
    }
  }
}
