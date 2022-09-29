<template>
  <div class="find TITLE">
    <h3>{{ monsterDetail.name }}몬을 이미 발견하셨어요!</h3>
    <p v-if="monsterDetail.firstFinder">
      최초 발견자 : {{ monsterDetail.firstFinder }}
    </p>

    <div>
      <p v-if="monsterDetail.line"></p>
      <img :src="this.imageBaseUrl + '/' + plant.monsterId + '.png'" />
    </div>

    <div>
      <button @click="goToEncyclopedia" class="TITLE">도감에서 확인!</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  props: {
    plant: Object
  },
  data() {
    return {
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      monsterDetail: {}
    }
  },
  methods: {
    goToEncyclopedia() {
      this.$router.push({
        path: '/encyclopedia'
      })
    },
    fetchMonsterDetail() {
      axios({
        url: `https://j7e201.p.ssafy.io/api/v1/game/monster/detail/${this.plant.monsterId}`,
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      })
        .then((res) => {
          this.monsterDetail = res.data
        })
        .catch((err) => {
          console.log(err)
        })
    }
  },
  created() {
    this.fetchMonsterDetail()
  }
}
</script>

<style scoped>
.find {
  border-style: groove;
  border-color: black;
  border-width: 5px;
  border-radius: 10px;
  width: 40vw;
  height: 70vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: auto;
}
</style>
