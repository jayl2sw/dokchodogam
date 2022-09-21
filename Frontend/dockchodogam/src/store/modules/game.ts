import { Module } from 'vuex'
import { RootState } from '../index'

export interface gameState {
  enemyInfo: object
}

export const game: Module<gameState, RootState> = {
  state: {
    enemyInfo: {}
  },
  getters: {
    enemyInfo: (state) => state.enemyInfo
  },
  mutations: {
    SET_ENEMYINFO(state, enemyInfo) {
      state.enemyInfo = enemyInfo
      console.log(enemyInfo)
    }
  },
  actions: {
    fetchEnemyInfo({ commit }, enemyInfo) {
      commit('SET_ENEMYINFO', enemyInfo)
    }
  }
}
