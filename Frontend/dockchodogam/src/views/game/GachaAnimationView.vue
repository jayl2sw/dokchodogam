<template>
  <div
    class="gacha"
    :class="{
      card__common: monsterGacha[0].grade == 'COMMON',
      card__rare: monsterGacha[0].grade == 'RARE',
      card__epic: monsterGacha[0].grade == 'EPIC',
      card__legendary: monsterGacha[0].grade == 'LEGENDARY',
      card__special: monsterGacha[0].grade == 'SPECIAL'
    }"
  >
    <div id="warning-message">
      <p class="TITLE">
        아레나는<br /><br /><span class="emphasize">"가로 화면 전용"</span>
        게임입니다.
      </p>
    </div>
    <div class="gacha__header dpNone">
      <h3 class="TITLE">
        🎉 축하합니다!
        <span class="TITLE">{{ monsterGacha[0].name }}몬</span>
        획득 🎉
      </h3>
      <br />
      <p v-if="monsterGacha[0].got === true">
        이미 획득한 풀깨비입니다 🤟 10냥을 돌려드릴게요!
      </p>
    </div>
    <div class="gacha__body dpNone">
      <div class="bubble">
        <p v-if="monsterGacha[0].line !== null">
          " {{ monsterGacha[0].line }} "
        </p>
      </div>
      <img
        class="gacha__img dpNone"
        :src="this.imageBaseUrl + '/' + monsterGacha[0].monsterId + '.png'"
      />

      <!-- <img src="@/assets/speech.png" /> -->
    </div>

    <div class="gacha__footer dpNone">
      <div class="buttons">
        <button class="btn" @click="goToDogam">도감에서 확인하기</button>
        <button class="btn" @click="goToGameShop">상점으로 돌아가기</button>
      </div>
    </div>
    <!-- <div class="shop__exit" @click="goToGameShop()">
      <font-awesome-icon icon="fa-solid fa-x" size="xl" />
    </div> -->
  </div>
</template>

<script>
import axios from 'axios'
import { mapGetters, mapActions } from 'vuex'
import { BASE_URL } from '@/constant/BASE_URL'
import swal from 'sweetalert'
import JSConfetti from 'js-confetti'

const jsConfetti = new JSConfetti()

// jsConfetti.addConfetti({
//   // emojis: ['🌈', '⚡️', '💥', '✨', '💫', '🌸']
// })

export default {
  data() {
    return {
      // newMonster: {},
      userInfo: JSON.parse(localStorage.getItem('userInfo')),
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      gotcha_audio: new Audio(process.env.VUE_APP_S3_URL + '/gotcha.mp3'),
      line_audio: ''
    }
  },
  computed: {
    ...mapGetters(['monsterGacha'])
  },
  methods: {
    // ...mapActions(['fetchUserInfo']),
    goToDogam() {
      this.$router.replace({
        path: '/encyclopedia'
      })
    },
    goToGameShop() {
      this.$router.replace({
        path: '/game/shop'
      })
    },
    startConfetti() {
      jsConfetti.addConfetti({
        emojis: ['🌈', '⚡️', '💥', '✨', '💫', '🌸']
      })
    }
  },
  created() {
    console.log('시작')
    // this.fetchMonsterGacha()
    // document.cookie = 'safeCookie1=foo; SameSite=Lax'
    // document.cookie = 'safeCookie2=foo'
    // document.cookie = 'crossCookie=bar; SameSite=None; Secure'
    this.startConfetti()
    // this.fetchUserInfo()
  },
  mounted() {
    this.gotcha_audio.play()
    this.line_audio = new Audio(
      process.env.VUE_APP_S3_URL + '/' + this.monsterGacha[0].monsterId + '.m4a'
    )
    this.line_audio.play()
  },
  beforeUnmount() {
    this.line_audio.pause()
  }
}
</script>

<style scoped>
.gacha {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100vh;
  width: 100vw;
  background-color: #ececec;
}

.gacha__header {
  text-align: center;
  margin-top: 5vh;
  margin-bottom: 3vh;
}

.gacha__body {
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  /* height: 70%; */
}

.gacha__img {
  width: 20vw;
  height: 40vh;
  /* margin: 5%; */
  margin-left: auto;
  margin-right: auto;
}

.bubble {
  /* height: 250px; */
  height: 3vmax;
  width: 40%;
  min-width: 200px;
  /* background: ivory; */
  display: block;
  margin: 0 auto;
  border-radius: 50px;
  /* margin-top: 50px; */
  /* font-size: 200px; */
  text-align: center;
  display: table;
}

.bubble p {
  display: table-cell;
  vertical-align: middle;
  text-align: center;
  align-self: center;
  /* margin-bottom: 0; */
  font-family: 'UhBeeSe_hyun';
}

.gacha__footer {
  display: flex;
  flex-direction: row;
  align-self: center;
}

.buttons {
  margin-top: 2vh;
}

.buttons button {
  justify-content: space-around;
  margin: 2vh;
  border-radius: 20px;
}

.btn {
  /* width: ; */
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  color: black;
  text-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  margin: auto;
  box-shadow: 0 0 5px #000;
  background-color: #a7c957;
  background-image: #a7c957;
}
.btn:hover {
  background-position: right center;
  background-color: #467302;
  color: white;
}

.shop__exit {
  width: 30px;
  height: 30px;
  border: 2px groove black;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 20px;
  right: 30px;
  cursor: pointer;
}

/* .card__common {
  color: #8f8f8f;
}
.card__rare {
  color: #4dbcfb;
}
.card__epic {
  color: violet;
}
.card__legendary {
  color: #f1c012;
}px
.card__special {
  color: url(https://img.freepik.com/premium-vector/glitters-rainbow-sky-shiny-rainbows-pastel-color-magic-fairy-starry-skies-and-glitter-sparkles-background-illustration_102902-1299.jpg?w=2000);
} */

.card__common {
  background-color: #8f8f8f;
}
.card__rare {
  background-color: #4dbcfb;
}
.card__epic {
  background-color: violet;
}
.card__legendary {
  background-color: #f1c012;
}
.card__special {
  background-image: url(https://img.freepik.com/premium-vector/glitters-rainbow-sky-shiny-rainbows-pastel-color-magic-fairy-starry-skies-and-glitter-sparkles-background-illustration_102902-1299.jpg?w=2000);
}
@media only screen and (orientation: portrait) {
  .gacha {
    background-image: none;
    background-color: white;
    height: 100vh;
  }
  .dpNone {
    display: none;
  }
  #warning-message {
    display: block;
    font-size: 5vw;
    text-align: center;
  }
  .emphasize {
    font-family: 'UhBeeSe_hyun';
    font-size: 6vw;
    font-weight: bold;
    color: #467302;
  }
  .danger {
    font-size: 7vw;
    font-weight: bold;
    color: red;
    margin-bottom: 1vw;
  }
}
@media only screen and (orientation: landscape) {
  #warning-message {
    display: none;
  }
}
</style>
