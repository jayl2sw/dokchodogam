<template>
  <NavBar @overflow="overflow" />
  <div class="dcgg" :class="this.showMenu ? 'open-menu' : ''">
    <div></div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'

export default {
  components: {
    NavBar
  },
  data() {
    return {
      showMenu: false
    }
  },
  methods: {
    overflow(value) {
      this.showMenu = value
    }
  }
}
</script>

<style scoped>
@media screen and (max-width: 850px) {
  .open-menu {
    overflow: hidden;
    width: 100%;
    height: 100%;
    position: fixed;
  }
}
</style>
