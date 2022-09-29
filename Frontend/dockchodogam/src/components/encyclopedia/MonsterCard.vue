<template>
  <div>
    <!-- <img class="speech" src="@/assets/speech_ballon.png" /> -->
    <div v-if="monster.got == true && monster.monsterId !== 0" class="card">
      <div class="imgBx">
        <img
          :src="this.imageBaseUrl + '/' + monster.monsterId + '.png'"
          class="card__img"
          style="-webkit-user-drag: none"
        />
        <!-- <p>대사 : {{ monster.line }}</p> -->
      </div>
      <div
        :class="{
          card__common: monster.grade == 'COMMON',
          card__rare: monster.grade == 'RARE',
          card__epic: monster.grade == 'EPIC',
          card__legendary: monster.grade == 'LEGENDARY',
          card__special: monster.grade == 'SPECIAL'
        }"
        class="contentBx"
      >
        <div class="contentBx__name">
          <p class="TITLE">00{{ monster.monsterId }}</p>
          <h3 class="TITLE">{{ monster.name }}몬</h3>
        </div>

        <div class="size">
          <p>
            타입 : {{ this.monster.type }} <br />등급 : {{ this.monster.grade }}
            <br />
            체력 : {{ this.monster.hp }} <br />
            공격력 : {{ this.monster.minAttack }} ~ {{ this.monster.maxAttack }}
          </p>
        </div>
      </div>
    </div>

    <div
      v-else-if="monster.got == false && monster.monsterId !== 0"
      class="card"
    >
      <div class="dontHaveimgBx">
        <img
          :src="this.imageBaseUrl + '/' + monster.monsterId + '.png'"
          class="card__dontHaveimg"
          style="-webkit-user-drag: none"
        />
      </div>
      <div class="dontHavecontentBx">
        <div class="dontHavecontentBx__name">
          <p>00{{ monster.monsterId }}</p>
          <h3>?</h3>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import Swal from 'sweetalert2'

export default {
  props: {
    monster: Object
  },
  data() {
    return {
      modal: false,
      monsterDetail: {},
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  methods: {
    async storeMonster(a) {
      this.monsterDetail = a
      // alert(a.name)
      // console.log(a)
      // console.log(this.monsterDetail)
    },

    fetchMonsterDetail() {
      axios({
        url: `https://j7e201.p.ssafy.io/api/v1/game/monster/detail/${this.monster.monsterId}`,
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
/* body {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: #131313;
} */

/* .container {
  position: relative;
} */

.speech {
  position: absolute;
  width: 200px;
  height: auto;
}

.card {
  position: relative;
  width: 200px;
  height: 300px;
  background: white;
  border-radius: 20px;
  overflow: hidden;
  margin-bottom: 2vh;
  /* margin: 0; */
}

.container .card .imgBx {
  /* position: absolute; */
  /* top: 35%; */
  transform: translate(10%, 10%);
  width: 100%;
  height: 120px;
  transition: 0.5s;
  display: flex;
  flex-direction: column;
}

.container .card .imgBx p {
  visibility: hidden;
}

.container .card:hover .imgBx p {
  visibility: visible;
  display: inline-block;
}

.container .card:hover .imgBx {
  /* top: 30%; */
  transform: translate(20%, 22%);
  width: 80%;
}

.container .card .imgBx img {
  position: absolute;
  display: block;
  margin: auto;
  /* top: 50%; */
  /* left: 50%; */
  /* transition: transform 0.25s ease; */
  /* transform: translate(-20%, -20%); */
  width: 80%;
}

/* .arrow_box {
  display: none;
}

.imgBx:hover .arrow_box {
  display: block;
} */
/* .container .card .imgBx .arrow_box {
  position: relative;
  background: #d6c1c1;
  border-radius: 20px;
  margin: 2vh;
  height: 5vh;
  text-align: center;
} */
/* .container .card .imgBx .arrow_box:after {
  top: 100%;
  left: 70%;
  border: solid transparent;
  content: '';
  height: 0;
  width: 0;
  position: absolute;
  pointer-events: none;
  border-top-color: #d6c1c1;
  border-width: 3vh;
  margin-left: -3vw;
} */
.container .card .card__dontHaveimg {
  position: absolute;
  /* top: 50%; */
  transform: translate(30%, 30%);
  /* z-index: 10000; */
  width: 60%;
  /* height: 220px; */
  /* transition: 0.5s; */
  filter: brightness(0%);
}

.container .card .card__dontHaveimg img {
  position: absolute;
  top: 60%;
  left: 50%;
  width: 80%;
  filter: brightness(0%);
}

.container .card .contentBx {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 15vh;
  text-align: center;
  transition: 1s;
  z-index: 10;
  padding: 1vh;
}

.container .card .dontHavecontentBx {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 20vh;
  text-align: center;
  transition: 1s;
  z-index: 10;
}

.container .card:hover .contentBx {
  height: 25vh;
}

.container .card .contentBx h3 {
  position: relative;
  /* font-weight: 600; */
  font-size: 15;
  color: #000000;
  margin-bottom: 1vw;
}

.container .card .contentBx p {
  position: relative;
  font-weight: 500;
  font-size: 0.9vw;
  color: #000000;
  margin: 0;
}

.contentBx__name {
  margin-top: 1.5vh;
}

.dontHavecontentBx__name {
  margin-top: 6vh;
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

.container .card .dontHavecontentBx h3 {
  position: relative;
  /* font-weight: 600; */
  font-size: 15;
  color: #000000;
  font-family: 'UhBeeSe_hyun';
  margin-bottom: 1vw;
}

.container .card .dontHavecontentBx p {
  position: relative;
  font-weight: 500;
  font-size: 0.9vw;
  font-family: 'UhBeeSe_hyun';
  color: #000000;
  margin: 0;
}

/* .container .card .dontHavecontentBx h3,
.container .card .dontHavecontentBx p {
  position: relative;
  font-weight: 600;
  font-size: 1em;
  color: #000000;
  margin-top: 3vh;
  font-family: 'UhBeeSe_hyun';
} */

.container .card:hover .contentBx .size {
  opacity: 1;
  visibility: visible;
  transition-delay: 0.5s;
}

.card__common {
  background-color: gray;
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
</style>
