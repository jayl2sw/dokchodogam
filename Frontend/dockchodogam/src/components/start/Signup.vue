<template>
  <div>
    <div>
      <input v-model="nickname" placeholder="닉네임" />
      <span>한글이나 영문자, 숫자의 조합으로 1~4자리</span>
      <button type="submit" @click="isNicknameDuplicate()">
        닉네임중복확인
      </button>
    </div>
    <div>
      <input v-model="username" placeholder="아이디" />
      <span>영문자나 숫자의 조합으로 5~20자리</span>
    </div>
    <div>
      <input v-model="email" placeholder="이메일" />
      <button type="submit" @click="isEmailDuplicate()">이메일중복확인</button>
    </div>
    <div>
      <input v-model="password" type="password" placeholder="비밀번호" />
      <span>영문자+숫자+특수문자 조합으로 8~25자리</span>
    </div>
    <div>
      <input v-model="password2" type="password" placeholder="비밀번호 확인" />
    </div>
    <button type="submit" @click="signup()">회원가입</button>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'

var usernameCheck = /^[a-zA-Z0-9]{5,20}$/
var nicknameCheck = /^[가-힣a-zA-Z0-9]{1,4}$/
var passwordCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/
var emailCheck =
  /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/

export default {
  data() {
    return {
      email: this.email,
      nickname: this.nickname,
      password: this.password,
      password2: this.password2,
      username: this.username,
      nicknameDuplicate: true,
      emailDuplicate: true
    }
  },
  methods: {
    isEmailDuplicate() {
      if (!emailCheck.test(this.email)) {
        alert('정확한 이메일 주소인지 확인해주세요🙏')
      } else {
        axios
          .get(BASE_URL + '/api/v1/user/auth/check/email/' + this.email, {
            email: this.email
          })
          .then((res) => {
            console.log(res)
            if (res.data === false) {
              this.emailDuplicate = false
              alert('이 이메일은 사용하셔도 좋아용.')
            } else {
              this.emailDuplicate = true
              alert('이미 존재하는 이메일 주소입니다.')
            }
          })
          .catch((err) => {
            console.log(err)
          })
      }
    },
    isNicknameDuplicate() {
      if (!nicknameCheck.test(this.nickname)) {
        alert(
          '닉네임은 한글이나 영문자, 숫자의 조합으로 1~4자리를 사용해야 해요🙏'
        )
      } else {
        axios
          .get(BASE_URL + '/api/v1/user/auth/check/nickname/' + this.nickname, {
            nickname: this.nickname
          })
          .then((res) => {
            console.log(res)
            if (res.data === false) {
              this.nicknameDuplicate = false
              alert('이 닉네임은 사용하셔도 좋아용.')
            } else {
              this.nicknameDuplicate = true
              alert('이미 존재하는 닉네임입니다.')
            }
          })
          .catch((err) => {
            console.log(err)
          })
      }
    },
    async signup() {
      if (this.nicknameDuplicate === true) {
        alert('닉네임중복검사를 먼저 진행해주세요.')
      } else if (this.emailDuplicate === true) {
        alert('이메일중복검사를 먼저 진행해주세요.')
      } else if (!passwordCheck.test(this.password)) {
        alert(
          '비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리를 사용해야 해요🙏'
        )
      } else if (!usernameCheck.test(this.username)) {
        alert('아이디는 영문자나 숫자의 조합으로 5~20자리를 사용해야 해요🙏')
      } else if (!nicknameCheck.test(this.nickname)) {
        alert(
          '닉네임은 한글이나 영문자, 숫자의 조합으로 1~4자리를 사용해야 해요🙏'
        )
      } else if (!emailCheck.test(this.email)) {
        alert('정확한 이메일 주소인지 확인해주세요🙏')
      } else if (this.password === this.password2) {
        await axios
          .post(BASE_URL + '/api/v1/user/auth/signup', {
            email: this.email,
            nickname: this.nickname,
            password: this.password,
            region: this.region,
            username: this.username
          })
          .then((res) => {
            console.log(res)
            alert('회원가입을 축하드립니다!')
          })
          .catch((err) => {
            console.log(err)
          })
        await this.$router.push({
          name: 'start'
        })
      } else {
        return alert('비밀번호가 일치하지 않습니다.')
      }
    }
  }
}
</script>

<style></style>
