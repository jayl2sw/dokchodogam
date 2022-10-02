<template>
  <NavBar @overflow="overflow" />
  <div class="dcgg" :class="this.showMenu ? 'open-menu' : ''">
    <div class="dcgg__menu">
      <div
        class="menuItem TITLE"
        @click="this.goToUserSearch()"
        :class="this.currentMenu === 'user' ? 'select' : ''"
      >
        유저검색
      </div>
      <div
        class="menuItem TITLE"
        @click="this.goToRanking()"
        :class="this.currentMenu === 'rank' ? 'select' : ''"
      >
        전체랭킹
      </div>
    </div>
    <div class="dcgg__main">
      <UserData v-if="this.currentMenu === 'user'" />
      <UserRanking v-if="this.currentMenu === 'rank'" />
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'
import UserData from '@/components/game/gg/UserData.vue'
import UserRanking from '@/components/game/gg/UserRanking.vue'

export default {
  components: {
    NavBar,
    UserData,
    UserRanking
  },
  data() {
    return {
      showMenu: false,
      currentMenu: 'user'
    }
  },
  methods: {
    overflow(value) {
      this.showMenu = value
    },
    goToUserSearch() {
      this.currentMenu = 'user'
    },
    goToRanking() {
      this.currentMenu = 'rank'
    }
  }
}
</script>

<style scoped>
.dcgg {
  display: flex;
  flex-direction: column;
  position: absolute;
  top: 0;
  width: 100%;
  min-height: 100vh;
  z-index: -9999;
  padding-top: 13vh;
  background-image: url('@/assets/dogam_background.jpg');
}
.dcgg__menu {
  height: 10vh;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 3vh;
}
.menuItem {
  font-size: 2vw;
  width: 20vw;
  text-align: center;
  color: #a7c957;
  cursor: pointer;
}
.select {
  color: #467302;
}
.dcgg__main {
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
@media screen and (max-width: 700px) {
  .menuItem {
    font-size: 4vw;
    width: 30vw;
  }
  .dcgg__menu {
    margin: 0;
  }
}
</style>
