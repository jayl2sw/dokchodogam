<template>
  <div>비번찾기페이지임</div>
  <FindPassword />
  <router-view></router-view>
</template>

<script>
import FindPassword from '@/components/start/FindPassword.vue'

export default {
  name: 'FindPasswordView',
  components: {
    FindPassword
  }
}
</script>

<style></style>
