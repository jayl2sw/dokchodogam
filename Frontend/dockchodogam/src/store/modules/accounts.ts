import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import { Module } from 'vuex'
import { RootState } from '../index'

export interface accountsState {
  userInfo: object
  nowUserInfo: object
}

export const accounts: Module<accountsState, RootState> = {
  state: {
    userInfo: {},
    nowUserInfo: {}
  },
  getters: {
    userInfo: (state) => state.userInfo,
    nowUserInfo: (state) => state.nowUserInfo,
    // 로그인 여부를 가져옵니다.
    isAccessTokenExpired() {
      let expire = false
      // accessToken에서 .(도트)로 분리하여 payload를 가져옵니다.
      let base64Payload = localStorage.getItem('accessToken').split('.')[1]
      // URL과 호환되지 않는 문자를 base64 표준 문자로 교체합니다.
      base64Payload = base64Payload.replace(/-/g, '+').replace(/_/g, '/')
      // atob() 메소드로 복호화합니다.
      base64Payload = atob(base64Payload)
      // JSON 객체로 변환합니다.
      const payloadObject = JSON.parse(base64Payload)
      // accessToken의 만료 시간을 확인합니다.
      const currentDate = new Date().getTime() / 1000
      if (payloadObject.exp <= currentDate) {
        expire = true
      }
      return expire
    }
  },
  mutations: {
    SET_USERINFO(state, userInfo) {
      state.userInfo = userInfo
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
    },
    SET_NOWUSERINFO(state, nowUserInfo) {
      localStorage.setItem('userInfo', JSON.stringify(nowUserInfo))
      state.nowUserInfo = nowUserInfo
    }
  },
  actions: {
    async fetchUserInfo({ commit }, userInfo) {
      commit('SET_USERINFO', userInfo)
    },
    fetchnowUserInfo({ commit }) {
      axios({
        url: 'https://j7e201.p.ssafy.io/api/v1/user/myinfo',
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      })
        .then((res) => {
          commit('SET_NOWUSERINFO', res.data)
        })
        .catch((err) => console.log(err))
    },
    // Access-Token를 갱신합니다.
    async doRefreshToken() {
      if (localStorage.getItem('accessToken') !== '') {
        const token = {
          accessToken: localStorage.getItem('accessToken'),
          refreshToken: localStorage.getItem('refreshToken')
        }
        try {
          const result = await axios.post(
            BASE_URL + '/api/v1/user/auth/refresh',
            token
          )
          if (result.status === 200) {
            console.log('Access-Token이 갱신되었습니다.')
            localStorage.setItem('accessToken', result.data.accessToken)
            localStorage.setItem('refreshToken', result.data.refreshToken)
            axios.defaults.headers.common.AUTHORIZATION =
              result.data.accessToken
          } else {
            window.location.href = '/'
          }
        } catch (err) {
          console.log(err)
          window.location.href = '/'
        }
      } else {
        window.location.href = '/'
      }
    }
  }
}
