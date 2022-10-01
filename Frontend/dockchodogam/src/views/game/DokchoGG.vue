<template>
  <NavBar @overflow="overflow" />
  <div class="dcgg" :class="this.showMenu ? 'open-menu' : ''">
    <div class="dcgg__main">
      <UserData />
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'
import UserData from '@/components/game/gg/UserData.vue'

export default {
  components: {
    NavBar,
    UserData
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
.dcgg {
  display: flex;
  flex-direction: column;
}
.dcgg__main {
  margin-top: 10vh;
  padding: 0 10vw;
}
@media screen and (max-width: 850px) {
  .open-menu {
    overflow: hidden;
    width: 100%;
    height: 100%;
    position: fixed;
  }
}
</style>
