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
      <div class="dockchoAdd__btn" @click="this.onClickAddDockcho()">
        독초몬 추가
      </div>
      <div class="goToMain__btn" @click="this.goToMain()">메인으로</div>
    </div>
    <div class="manage">
      <div v-if="this.now === 'user'">
        <UserManage />
      </div>
      <div v-if="this.now === 'plant'">
        <PlantManage />
      </div>
      <div v-if="this.now === 'dockcho'">
        <DokchoManage />
      </div>
      <div v-if="this.now === 'addDockcho'">
        <AddDokcho />
      </div>
    </div>
  </div>
</template>

<script>
import UserManage from '@/components/admin/UserManage.vue'
import PlantManage from '@/components/admin/PlantManage.vue'
import DokchoManage from '@/components/admin/DokchoManage.vue'
import AddDokcho from '@/components/admin/AddDokcho.vue'

export default {
  components: {
    UserManage,
    DokchoManage,
    AddDokcho,
    PlantManage
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
    onClickAddDockcho() {
      this.now = 'addDockcho'
    },
    goToMain() {
      this.$router.push({ path: '/main' })
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
