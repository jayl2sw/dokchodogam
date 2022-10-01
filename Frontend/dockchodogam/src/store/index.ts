import { createStore } from 'vuex'
import { accounts, accountsState } from './modules/accounts'
import { game, gameState } from './modules/game'
import { camera, cameraState } from './modules/camera'

export interface RootState {
  accounts: accountsState
  game: gameState
  camera: cameraState
}

export default createStore({
  modules: { accounts, game, camera }
})
