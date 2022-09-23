<template>
  <div>
    <input v-model="userId" placeholder="아이디를 입력하세요" />
    <input
      v-model="userPassword"
      type="password"
      placeholder="비밀번호를 입력하세요"
    />
    <button @click="login()">로그인</button>
    <button @click="this.isAccessTokenExpired()">토큰만료확인</button>
    <button @click="this.doRefreshToken()">토큰재발급확인</button>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import { mapActions, mapGetters } from 'vuex'

export default {
  data() {
    return {
      userId: '',
      userPassword: ''
    }
  },
  methods: {
    ...mapActions(['doRefreshToken', 'fetchUserInfo', 'fetchUserDeck']),
    ...mapGetters(['isAccessTokenExpired']),
    async login() {
      console.log(this.userId)
      console.log(this.userPassword)
      try {
        const result = await axios.post(
          BASE_URL + '/api/v1/user/auth/login',
          {
            username: this.userId,
            password: this.userPassword
          },
          {
            headers: {
              'Content-type': 'application/json'
            }
          }
        )
        if (result.status === 200) {
          console.log(result)
          localStorage.setItem('accessToken', result.data.accessToken)
          localStorage.setItem('refreshToken', result.data.refreshToken)
          const option = {
            headers: {
              AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
            }
          }
          await axios
            .get(BASE_URL + '/api/v1/user/myinfo', option)
            .then((res) => {
              this.fetchUserInfo(res.data)
            })
          await this.$router.push({ name: 'main' })
        }
      } catch (err) {
        throw new Error(err)
      }
    }
  }
}
</script>
