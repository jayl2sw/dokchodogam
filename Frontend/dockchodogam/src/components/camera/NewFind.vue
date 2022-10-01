<template>
  <div class="find">
    <p>새로찾음</p>
    <h3 class="find__title TITLE">찾았다, {{ monsterDetail.name }}몬!</h3>
    <p v-if="monsterDetail.firstFinder" class="find_finder">
      최초 발견자 : {{ monsterDetail.firstFinder }}
    </p>
    <div v-if="this.catch === false">
      <div class="find__img">
        <p v-if="monsterDetail.line"></p>
        <img
          class="monster__silhouette"
          :src="this.imageBaseUrl + '/' + plant.monsterId + '.png'"
        />
        <div>
          <button @click="catchMonster" class="TITLE">눌러서 잡기!</button>
        </div>
      </div>
    </div>
    <div v-if="this.catch">
      <div class="find__img">
        <div class="card">
          <img :src="this.imageBaseUrl + '/' + plant.monsterId + '.png'" />
          <div class="contentBx">
            <p>00{{ monsterDetail.monsterId }}</p>
            <h3>{{ monsterDetail.name }}</h3>
          </div>
        </div>

        <div>
          <button @click="goToEncyclopedia" class="TITLE">
            도감에서 확인하기!
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapGetters } from 'vuex'

export default {
  // props: {
  //   monsterId: Number
  // },
  data() {
    return {
      // pick: false,
      monsterId: this.photoResult.plant.monsterId,
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      monsterDetail: {},
      catch: false
    }
  },
  computed: {
    ...mapGetters(['photoResult'])
  },
  methods: {
    catchMonster() {
      this.catch = true
    },
    goToEncyclopedia() {
      this.$router.push({
        path: '/encyclopedia'
      })
    },
    fetchMonsterDetail() {
      axios({
        url: `https://j7e201.p.ssafy.io/api/v1/game/monster/detail/${this.monsterId}`,
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
  /* border-style: groove; */
  /* border-color: black; */
  /* border-width: 5px; */
  border-radius: 10px;
  border-color: green;
  width: 40vw;
  /* height: 70vh; */
  display: flex;
  flex-direction: column;
  align-items: center;
  /* overflow: auto; */
}
.find__title {
  font-size: 3vw;
  font-weight: bold;
}
.find_finder {
  width: 100%;
  text-align: right;
  color: #a7c957;
}
.find__img {
  display: flex;
  flex-direction: column;
}
.monster__silhouette {
  width: 50vh;
  filter: grayscale(100%);
}

button {
  background-color: #a7c957;
  width: 12vw;
  height: 6vh;
  border-radius: 10px;
  border: none;
  opacity: 0.8;
  transition: 0.2s;
  margin: 5vh auto 0;
}
button:hover {
  font-weight: bold;
  opacity: 1;
}
@media screen and (max-width: 850px) {
  .find {
    width: 100%;
    height: auto;
    margin-bottom: 8vh;
    position: relative;
  }
  .find_finder {
    position: absolute;
    bottom: -9vh;
    text-align: left;
    left: 5vw;
    font-size: 0.8rem;
  }
  .find__title {
    font-size: 8vw;
    font-weight: bold;
    word-break: keep-all;
  }
  .find__img > img {
    width: 60vw;
  }
  button {
    width: 50vw;
    opacity: 1;
  }
}
</style>
