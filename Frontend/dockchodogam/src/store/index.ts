import { createStore } from 'vuex'
import { accounts, accountsState } from './modules/accounts'
import { game, gameState } from './modules/game'

export interface RootState {
  accounts: accountsState
  game: gameState
}

export default createStore({
  modules: { accounts, game }
})
