import axios from 'axios'
// import { BASE_URL } from '@/constant/BASE_URL'
import { Module } from 'vuex'
import { RootState } from '../index'

export interface cameraState {
  photoResult: object
}

export const camera: Module<cameraState, RootState> = {
  state: {
    photoResult: {}
  },
  getters: {
    photoResult: (state) => state.photoResult
  },
  mutations: {
    SET_PHOTORESULT(state, photoResult) {
      state.photoResult = photoResult
    }
  },
  actions: {
    fetchphotoResult({ commit }, photoResult) {
      commit('SET_PHOTORESULT', photoResult)
    }
  }
}
