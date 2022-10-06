<template>
  <div v-if="monsterDetail !== null" class="find">
    <div>
      <div class="find__header">
        <h3 class="TITLE">{{ monsterDetail.name }}몬은 이미 도감에 있어요!</h3>
        <p v-if="monsterDetail.firstFinder">
          ✨ {{ monsterDetail.firstFinder }}님이 제일 먼저 찾아냈어요! ✨
        </p>
      </div>

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
              :src="
                require('@/assets/monster/' + monsterDetail.monsterId + '.png')
              "
              alt="중복으로 뽑은 풀깨비 사진"
              class="card__img"
              style="-webkit-user-drag: none"
            />
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
import { mapGetters } from 'vuex'

export default {
  props: {
    monsterDetail: Object
  },
  data() {
    return {
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  computed: {
    ...mapGetters(['photoResult'])
  },
  methods: {
    goToEncyclopedia() {
      this.$router.push({
        path: '/encyclopedia'
      })
    }
  }
}
</script>

<style scoped>
.find {
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

.find__header p {
  margin: 0;
  padding-top: 1vmin;
}

.find__body {
  height: 80%;
  display: flex;
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
}

.card .imgBx {
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
  width: 100%;
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
}
.card .contentBx .title {
  position: relative;
  font-size: 1.3em;
  color: #000000;
  margin-bottom: 1vw;
}

.card .contentBx p {
  font-weight: 500;
  font-size: 0.9em;
  color: #000000;
  margin: 0;
}

.container .card .contentBx .size,
.container .card .contentBx .color {
  display: flex;
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
  margin-top: 7vh;
  margin-bottom: 5vh;
}

.btn {
  min-width: 50px;
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  color: black;
  text-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
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
</style>
