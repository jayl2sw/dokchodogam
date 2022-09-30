import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import { Module } from 'vuex'
import { RootState } from '../index'

export interface accountsState {
  userInfo: object
}

export const accounts: Module<accountsState, RootState> = {
  state: {
    userInfo: {}
  },
  getters: {
    userInfo: (state) => state.userInfo,
    // 로그인 여부를 가져옵니다.
    // isLogin(state) {
    //   return localStorage.getItem('refreshToken') === '' ? false : true
    // },
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
        console.log('token expired')
        expire = true
      } else {
        console.log('token valid')
      }
      return expire
    }
  },
  mutations: {
    SET_USERINFO(state, userInfo) {
      state.userInfo = userInfo
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
      console.log(localStorage.getItem('userInfo'))
    }
  },
  actions: {
    fetchUserInfo({ commit }) {
      axios
        .get(BASE_URL + '/api/v1/user/myInfo', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          commit('SET_USERINFO', res.data)
          console.log('내 정보~', res.data)
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
          console.log(result)
          if (result.status === 200) {
            console.log('Access-Token이 갱신되었습니다.')
            localStorage.setItem('accessToken', result.data.accessToken)
            localStorage.setItem('refreshToken', result.data.refreshToken)
            console.log('accessToken : ', result.data.accessToken)
            console.log('refreshToken : ', result.data.refreshToken)
            axios.defaults.headers.common.AUTHORIZATION =
              result.data.accessToken
          } else {
            console.log('다시 로그인 하셈')
            alert('다시 로그인 해주세요🙏')
            window.location.href = '/'
            // let err = new Error("Request failed with status code 401");
            // err.status = 401;
            // err.response = {data:{"success":false, "errormessage":"Access-Token이 갱신되었습니다."}};
            // resultErr = err;
          }
        } catch (err) {
          console.log('다시 로그인 하셈')
          console.log(err)
          alert('다시 로그인 해주세요🙏')
          window.location.href = '/'
          // if (!err.response) {
          // err.response = {data:{"success":false, "errormessage":err.message}};
          // }
          // resultErr = err;
        }
      } else {
        console.log('다시 로그인 하셈')
        alert('다시 로그인 해주세요🙏')
        window.location.href = '/'
        // let err = new Error("Access-Token does not exist");
        // err.status = 401;
        // err.response = {data:{"success":false, "errormessage":"Access-Token이 없습니다."}};
        // resultErr = err;
      }
    }
  }
}
