<template>
  <NavBar @overflow="overflow" />
  <div class="gallery" :class="this.showMenu ? 'open-menu' : ''">
    <div class="gallery__top"></div>
    <div class="gallery__main"></div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'
import { BASE_URL } from '@/constant/BASE_URL'
import axios from 'axios'

export default {
  components: {
    NavBar
  },
  data() {
    return {
      showMenu: false,
      userInfo: JSON.parse(localStorage.getItem('userInfo'))
    }
  }
}
</script>

<style scoped></style>
