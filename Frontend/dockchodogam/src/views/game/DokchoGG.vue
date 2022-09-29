<template>
  <NavBar @overflow="overflow" />
  <div class="dcgg" :class="this.showMenu ? 'open-menu' : ''">
    <div class="menuBar">
      <div class="myDataBtn">내데이터</div>
      <div class="DokchoDataBtn">독초몬데이터</div>
    </div>
    <div class="dcgg__main"></div>
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
.dcgg {
  display: flex;
  flex-direction: column;
}
.menuBar {
  display: flex;
  justify-content: center;
  height: 10vh;
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
