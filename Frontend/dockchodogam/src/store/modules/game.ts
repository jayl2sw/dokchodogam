import { StoreOptions } from 'vuex'

interface State {
  enemyInfo: object
}

export const game: StoreOptions<State> = {
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
