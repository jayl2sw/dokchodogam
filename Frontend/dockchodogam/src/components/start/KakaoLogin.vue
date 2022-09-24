<template>
  <div>KAKAO LOGIN</div>
</template>

<script>
export default {
  mounted() {
    localStorage.setItem('accessToken', this.$route.query.Auth)
    localStorage.setItem('refreshToken', this.$route.query.Refresh)
  }
}
</script>
