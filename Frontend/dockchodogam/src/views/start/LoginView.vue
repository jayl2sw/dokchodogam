<template>
  <div>로그인페이지임</div>
  <router-link to="/findpassword">비밀번호찾기</router-link>
  <router-link to="/signup">회원가입</router-link>
  <Login />
  <router-view></router-view>
</template>

<script>
import Login from '@/components/start/Login.vue'

export default {
  name: 'LoginView',
  components: {
    Login
  }
}
</script>

<style></style>
