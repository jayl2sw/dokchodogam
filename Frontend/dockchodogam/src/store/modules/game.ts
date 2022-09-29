import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import { Module } from 'vuex'
import { RootState } from '../index'

export interface gameState {
  enemyInfo: object
  userDeck: Array<object>
  monsterGacha: Array<object>
  monsterPackage: Array<object>
}

export const game: Module<gameState, RootState> = {
  state: {
    enemyInfo: {},
    userDeck: [],
    monsterGacha: [],
    monsterPackage: []
  },
  getters: {
    enemyInfo: (state) => state.enemyInfo,
    userDeck: (state) => state.userDeck,
    monsterGacha: (state) => state.monsterGacha,
    monsterPackage: (state) => state.monsterPackage
  },
  mutations: {
    SET_ENEMYINFO(state, enemyInfo) {
      state.enemyInfo = enemyInfo
      console.log(enemyInfo)
    },
    SET_USERDECK(state, userDeck) {
      state.userDeck = userDeck
      console.log(userDeck)
    },
    SET_MONSTERGACHA(state, monsterGacha) {
      state.monsterGacha = monsterGacha
      console.log(monsterGacha)
    },
    SET_MONSTERPACKAGE(state, monsterPackage) {
      state.monsterPackage = monsterPackage
      console.log(monsterPackage)
    }
  },
  actions: {
    fetchEnemyInfo({ commit }, enemyInfo) {
      commit('SET_ENEMYINFO', enemyInfo)
    },
    fetchUserDeck({ commit }) {
      axios
        .get(BASE_URL + '/api/v1/game/deck/myInfo', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          commit('SET_USERDECK', res.data)
          console.log('내 덱', res.data)
        })
        .catch((err) => console.log(err))
    },
    fetchMonsterGacha({ commit }) {
      axios({
        url: 'https://j7e201.p.ssafy.io/api/v1/game/monster/pick/3',
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          // 'Content-Type': 'application/json'
        }
      })
        .then((res) => {
          commit('SET_MONSTERGACHA', res.data)
          console.log('몬스터 단뽑', res.data)
        })
        .catch((err) => console.log(err))
    },
    fetchMonsterPackage({ commit }) {
      axios({
        url: 'https://j7e201.p.ssafy.io/api/v1/game/monster/pick/2',
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          // 'Content-Type': 'application/json'
        }
      })
        .then((res) => {
          commit('SET_MONSTERPACKAGE', res.data)
          console.log('몬스터 패키지', res.data)
        })
        .catch((err) => console.log(err))
    }
  }
}
