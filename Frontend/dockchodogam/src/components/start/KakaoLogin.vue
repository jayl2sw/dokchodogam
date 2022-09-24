<template>
  <div></div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import { mapActions } from 'vuex'

export default {
  mounted() {
    localStorage.setItem('accessToken', this.$route.query.Auth)
    localStorage.setItem('refreshToken', this.$route.query.Refresh)
    this.login()
  },
  methods: {
    ...mapActions(['fetchUserInfo']),
    async login() {
      try {
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
      } catch (e) {
        console.log(e)
      }
    }
  }
}
</script>
