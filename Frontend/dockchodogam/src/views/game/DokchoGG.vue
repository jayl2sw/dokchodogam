<template>
  <NavBar @overflow="overflow" />
  <div class="dcgg" :class="this.showMenu ? 'open-menu' : ''">
    <div class="menuBar">
      <div class="userDataBtn" @click="this.onClickMyData()">내데이터</div>
      <div class="DokchoDataBtn" @click="this.onClickDokcho()">
        독초몬데이터
      </div>
    </div>
    <div class="dcgg__main">
      <UserData v-if="this.nowPage === 'myData'" />
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
      showMenu: false,
      nowPage: 'myData'
    }
  },
  methods: {
    overflow(value) {
      this.showMenu = value
    },
    onClickMyData() {
      this.nowPage = 'myData'
    },
    onClickDokcho() {
      this.nowPage = 'Dokcho'
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
.userDataBtn {
  width: 20vw;
  text-align: center;
}
.DokchoDataBtn {
  width: 20vw;
  text-align: center;
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
</style>
