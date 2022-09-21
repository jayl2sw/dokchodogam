<template>
  <div class="admin">
    <div class="sideBar">
      <div class="userManage__btn" @click="this.onClickUser()">회원 관리</div>
      <div class="plantManage__btn" @click="this.onClickPlant()">
        식물정보 관리
      </div>
      <div class="dockchoManage__btn" @click="this.onClickDockcho()">
        독초몬 관리
      </div>
      <div class="logManage__btn" @click="this.onClickLog()">로그 관리</div>
      <div class="logout__btn">로그아웃</div>
    </div>
    <div class="manage">
      <div v-if="this.now === 'user'">
        <UserManage />
      </div>
    </div>
  </div>
</template>

<script>
import UserManage from '@/components/admin/UserManage.vue'

export default {
  components: {
    UserManage
  },
  data() {
    return {
      now: 'user'
    }
  },
  methods: {
    onClickUser() {
      this.now = 'user'
    },
    onClickPlant() {
      this.now = 'plant'
    },
    onClickDockcho() {
      this.now = 'dockcho'
    },
    onClickLog() {
      this.now = 'log'
    }
  }
}
</script>

<style scoped>
.admin {
  margin: 5vh 0;
  display: flex;
  justify-content: space-evenly;
}
.sideBar {
  border: 5px groove black;
  border-radius: 10px;
  height: 90vh;
  width: 20vw;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
}
.sideBar > div {
  cursor: pointer;
}
.manage {
  border: 5px groove black;
  border-radius: 10px;
  height: 90vh;
  width: 60vw;
  overflow: auto;
}
</style>
