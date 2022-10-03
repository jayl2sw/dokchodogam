<template>
  <div class="card__container">
    <!-- <img class="speech" src="@/assets/speech_ballon.png" /> -->
    <div
      v-if="monster.got == true && monster.monsterId !== 0"
      class="card"
      :class="{
        card__dokcho: monster.type == 'DOKCHO',
        card__yakcho: monster.type == 'YAKCHO',
        card__japcho: monster.type == 'JAPCHO',
        card__hidden: monster.type == 'HIDDEN'
      }"
    >
      <div class="imgBx">
        <!-- <font-awesome-icon icon="fa-solid fa-magnifying-glass-chart" /> -->
        <img
          :src="this.imageBaseUrl + '/' + monster.monsterId + '.png'"
          class="card__img monster__img"
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
          <p class="TITLE title">
            {{ monster.name }}몬<span class="moveGG">
              |
              <font-awesome-icon
                @click="goToGG"
                icon="fa-solid fa-magnifying-glass-chart"
              /><span>&nbsp;</span>
              <font-awesome-icon
                @click="shareKakao()"
                icon="fa-solid fa-arrow-up-from-bracket"
              />
            </span>
          </p>
        </div>

        <div class="size">
          <p>
            타입 : {{ this.monsterType }} <br />등급 : {{ this.monterGrade }}
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
import { mapGetters } from 'vuex'

export default {
  props: {
    monster: Object
  },
  data() {
    return {
      modal: false,
      monsterDetail: {},
      monsterType: '',
      monterGrade: '',
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    async storeMonster(a) {
      this.monsterDetail = a
      // alert(a.name)
      // console.log(a)
      // console.log(this.monsterDetail)
    },
    goToGG() {
      this.$router.push({
        path: '/dokcho/gg',
        query: { query: this.monster.monsterId }
      })
    },
    checkType() {
      if (this.monster.type === 'DOKCHO') {
        this.monsterType = '독초😈'
      } else if (this.monster.type === 'YAKCHO') {
        this.monsterType = '약초🌿'
      } else if (this.monster.type === 'JAPCHO') {
        this.monsterType = '잡초🌻'
      } else {
        this.monsterType = '히든💜'
      }
    },
    checkGrade() {
      if (this.monster.grade === 'COMMOM') {
        this.monterGrade = '일반'
      } else if (this.monster.grade === 'RARE') {
        this.monterGrade = '희귀'
      } else if (this.monster.grade === 'EPIC') {
        this.monterGrade = '영웅'
      } else if (this.monster.grade === 'LEGENDARY') {
        this.monterGrade = '전설'
      } else {
        this.monterGrade = '스페셜'
      }
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
    },
    shareKakao() {
      const img = this.imageBaseUrl + '/' + this.monster.monsterId + '.png'
      const name = this.monster.name
      const user = this.userInfo.nickname
      window.Kakao.Link.sendDefault({
        objectType: 'feed',
        content: {
          title: `${name}몬을 잡았어요!`,
          description: `${user}님이 ${name}몬을 획득하셨습니다 ✨`,
          imageUrl: img,
          link: {
            // mobileWebUrl: '이미지 클릭시 이동할 사이트',
            webUrl: 'https://j7e201.p.ssafy.io'
          }
        },
        buttons: [
          {
            title: '도감 모으러 이동!',
            link: {
              // mobileWebUrl: '이미지 클릭시 이동할 사이트',
              webUrl: 'https://j7e201.p.ssafy.io'
            }
          }
        ]
      })
    }
  },
  created() {
    this.fetchMonsterDetail()
    this.checkType()
    this.checkGrade()
  }
}
</script>

<style scoped>
.card__container {
  display: flex;
  justify-content: center;
  flex-direction: row;
}
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
  transform: translate(10%, 20%);
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

.contentBx__name .moveGG {
  display: none;
}

.card:hover .moveGG {
  display: inline;
}

.container .card:hover .imgBx {
  /* top: 30%; */
  transform: translate(20%, 20%);
  width: 80%;
}

.container .card .imgBx .monster__img {
  position: absolute;
  display: block;
  margin: auto;
  /* top: 50%; */
  /* left: 50%; */
  /* transition: transform 0.25s ease; */
  /* transform: translate(-20%, -20%); */
  width: 80%;
}

.kakao__img {
  /* margin-top: 5px;
  margin-left: 15px; */
  margin: 2vmin;
  width: 20px;
  position: absolute;
  align-items: flex-end;
  /* top: 5%; */
  top: 1%;
  /* left: 77%; */
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
  height: 35%;
  text-align: center;
  transition: 1s;
  z-index: 10;
  padding: 1vh;
}

.container .card .dontHavecontentBx {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 45%;
  text-align: center;
  transition: 1s;
  z-index: 10;
}

.container .card:hover .contentBx {
  height: 25vh;
}

.container .card .contentBx .title {
  position: relative;
  /* font-weight: 600; */
  font-size: 1.3em;
  color: #000000;
  margin-bottom: 1vw;
}

.container .card .contentBx p {
  /* position: relative; */
  font-weight: 500;
  font-size: 0.9em;
  color: #000000;
  margin: 0;
}

.contentBx__name {
  margin-top: 1.5vh;
  /* width: 80%; */
}

.dontHavecontentBx__name {
  margin-top: 6vh;
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
  font-size: 0.9em;
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
svg {
  cursor: pointer;
  transition: 0.3s;
}
svg:hover {
  color: #467302;
}
</style>
