<template>
  <div v-if="monsterDetail !== null" class="find">
    <!-- <p>새로찾음</p> -->
    <div class="find__header">
      <h3 class="TITLE">찾았다, {{ monsterDetail.name }}몬!</h3>
      <p v-if="monsterDetail.firstFinder">
        최초 발견자 : {{ monsterDetail.firstFinder }}
      </p>
    </div>

    <div v-if="this.catch === false">
      <div class="find__body">
        <p v-if="monsterDetail.line">{{ monsterDetail.line }}</p>
        <img
          class="silhouette__img"
          :src="this.imageBaseUrl + '/' + monsterDetail.monsterId + '.png'"
          style="-webkit-user-drag: none"
        />
      </div>
      <div class="find__footer">
        <button @click="catchMonster" class="btn">눌러서 잡기!</button>
      </div>
    </div>

    <div v-if="this.catch">
      <div class="find__body">
        <div
          class="card"
          :class="{
            card__dokcho: monsterDetail.type == 'DOKCHO',
            card__yakcho: monsterDetail.type == 'YAKCHO',
            card__japcho: monsterDetail.type == 'JAPCHO',
            card__hidden: monsterDetail.type == 'HIDDEN'
          }"
        >
          <div class="imgBx">
            <img
              :src="this.imageBaseUrl + '/' + monsterDetail.monsterId + '.png'"
              class="card__img"
              style="-webkit-user-drag: none"
            />
            <!-- <p>대사 : {{ monster.line }}</p> -->
          </div>
          <div
            :class="{
              card__common: monsterDetail.grade == 'COMMON',
              card__rare: monsterDetail.grade == 'RARE',
              card__epic: monsterDetail.grade == 'EPIC',
              card__legendary: monsterDetail.grade == 'LEGENDARY',
              card__special: monsterDetail.grade == 'SPECIAL'
            }"
            class="contentBx"
          >
            <div class="contentBx__name">
              <p class="TITLE">00{{ monsterDetail.monsterId }}</p>
              <p class="TITLE title">{{ monsterDetail.name }}몬</p>
            </div>
          </div>
        </div>
      </div>

      <div class="find__footer">
        <button @click="goToEncyclopedia" class="btn">
          <span>도감에서 확인!</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapGetters } from 'vuex'

export default {
  props: {
    monsterDetail: Object
  },
  data() {
    return {
      // pick: false,
      // monsterId: this.photoResult.plant.monsterId,
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      // monsterDetail: {},
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
    }
    //
  }
  // created() {
  //   this.fetchMonsterDetail()
  // }
}
</script>

<style scoped>
.find {
  /* border-style: groove; */
  /* border-color: yellow; */
  /* border-width: 5px; */
  /* border-radius: 10px; */
  /* width: 40vw;
  height: 70vh; */
  width: 100%;
  height: 100%;
  padding: 3vmin;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: auto;
}
.find__header {
  margin-bottom: 5vmin;
}
.find__body {
  height: 80%;
  display: flex;
  /* margin-top: 3vh; */
}

.card {
  align-self: center;
  position: relative;
  width: 200px;
  height: 300px;
  background: white;
  border-radius: 20px;
  overflow: hidden;
  margin-bottom: 2vh;
  margin: auto;
  /* margin: 0; */
}

.card .imgBx {
  /* position: absolute; */
  /* top: 35%; */
  transform: translate(0%, 10%);
  width: 100%;
  height: 120px;
  transition: 0.5s;
  display: flex;
  flex-direction: column;
}

.card .imgBx img {
  position: absolute;
  display: block;
  margin: auto;
  /* top: 50%; */
  /* left: 50%; */
  /* transition: transform 0.25s ease; */
  /* transform: translate(-20%, -20%); */
  width: 100%;
}

.silhouette__img {
  width: 90%;
  filter: brightness(0%);
}

.card .contentBx {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 35%;
  text-align: center;
  transition: 1s;
  z-index: 10;
  padding: 1vh;
}
.contentBx__name {
  margin-top: 1.5vh;
  /* width: 80%; */
}
.card .contentBx .title {
  position: relative;
  /* font-weight: 600; */
  font-size: 1.3em;
  color: #000000;
  margin-bottom: 1vw;
}

.card .contentBx p {
  /* position: relative; */
  font-weight: 500;
  font-size: 0.9em;
  color: #000000;
  margin: 0;
}

.container .card .contentBx .size,
.container .card .contentBx .color {
  display: flex;
  /* height: inherit; */
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 8px 20px;
  transition: 0.5s;
  opacity: 0;
  visibility: hidden;
  padding-top: 0;
  padding-bottom: 0;
  color: white;
}

.find__body img {
  width: 60%;
}

.find__footer {
  margin-top: 3vh;
}

.btn {
  /* align-self: center; */
  min-width: 50px;
  /* width: 30%; */
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  color: black;
  text-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  /* margin: auto; */
  box-shadow: 0 0 10px #000;
  border-radius: 10px;
  background-color: #a7c957;
  background-image: #a7c957;
}
.btn:hover {
  background-position: right center;
  background-color: #467302;
  color: white;
}

.card__common {
  background-color: rgb(166, 166, 166);
  /* box-shadow: 0 0 10px #467302; */
}
.card__rare {
  background-color: skyblue;
}
.card__epic {
  background-color: violet;
}
.card__legendary {
  background-color: yellow;
}
.card__special {
  background-image: url(https://img.freepik.com/premium-vector/glitters-rainbow-sky-shiny-rainbows-pastel-color-magic-fairy-starry-skies-and-glitter-sparkles-background-illustration_102902-1299.jpg?w=2000);
}

.card__dokcho {
  box-shadow: 0 0 8px #ff5555;
}

.card__yakcho {
  box-shadow: 0 0 8px #467302;
}

.card__japcho {
  box-shadow: 0 0 8px #ffe140;
}

.card__hidden {
  box-shadow: 0 0 8px #c493ff;
}

/* @media screen and (max-width: 850px) {
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
} */
</style>
