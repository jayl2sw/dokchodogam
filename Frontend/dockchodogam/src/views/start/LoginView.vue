<template>
  <div>로그인페이지임</div>

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
