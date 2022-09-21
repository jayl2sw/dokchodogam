import { StoreOptions } from 'vuex'

export interface gameState {
  userInfo: object
  userDeck: Array<number>
  enemyInfo: object
}

export const game: StoreOptions<gameState> = {
  state: {
    userInfo: {},
    userDeck: [],
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
