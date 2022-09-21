import { createStore } from 'vuex'
import { accounts } from './modules/accounts'
import { game } from './modules/game'

export default createStore({
  modules: { accounts, game }
})
