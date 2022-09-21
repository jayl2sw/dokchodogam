<template>
  <div>
    <input v-model="nickname" placeholder="닉네임" />
    <input v-model="username" placeholder="아이디" />
    <input v-model="email" placeholder="이메일" />
    <input v-model="region" placeholder="리젼" />
    <input v-model="password" type="password" placeholder="비밀번호" />
    <input v-model="password2" type="password" placeholder="비밀번호 확인" />
    <button type="submit" @click="signup()">회원가입</button>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      email: this.email,
      nickname: this.nickname,
      password: this.password,
      password2: this.password2,
      username: this.username,
      region: this.region
    }
  },
  methods: {
    async signup() {
      if (this.password === this.password2) {
        // await axios({
        //   url: 'http://localhost:8081/api/v1/user/auth/signup',
        //   method: 'post',
        //   body: {
        //     email: this.email,
        //     nickname: this.nickname,
        //     password: this.password,
        //     region: this.region,
        //     username: this.username
        //   }
        // })
        await axios
          .post('http://localhost:8081/api/v1/user/auth/signup', {
            email: this.email,
            nickname: this.nickname,
            password: this.password,
            region: this.region,
            username: this.username
          })
          .then((res) => {
            console.log(res)
          })
          .catch((err) => {
            console.log(err)
          })
      } else {
        return alert('비밀번호가 일치하지 않습니다')
      }
    }
  }
}
</script>

<style></style>
