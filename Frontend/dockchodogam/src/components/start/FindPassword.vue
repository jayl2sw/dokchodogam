<template>
  <div>
    <div>독초도감</div>
    <div>
      회원가입할 때 입력한 이메일과 일치하면 이메일로 비밀번호 변경 메일을
      보내드립니당
    </div>
    <div>
      <input v-model="username" placeholder="아이디를 입력하세요" />
      <input v-model="email" placeholder="이메일을 입력하세요" />
    </div>
    <div>
      <button type="submit" @click="findpassword()">비밀번호찾기</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'

export default {
  data() {
    return {
      email: this.email
    }
  },
  methods: {
    async findpassword() {
      await axios
        .put(
          'http://localhost:8081/api/v1/user/auth/findpw?email=' + this.email
        )
        .then((res) => {
          console.log(res)
          alert('비밀번호 변경 메일을 보내드렸어요!')
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
}
</script>

<style></style>
