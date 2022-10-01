<template>
  <div class="gacha">
    <div class="gacha__header">
      <h3 class="TITLE">🎉 축하합니다! {{ monsterGacha[0].name }}몬 획득 🎉</h3>
      <br />
      <p v-if="monsterGacha[0].got === true">
        이미 획득한 몬스터입니다 🤟 10냥을 돌려드릴게요!
      </p>
    </div>
    <div class="gacha__body">
      <img
        class="gacha__img"
        :src="this.imageBaseUrl + '/' + monsterGacha[0].monsterId + '.png'"
      />
    </div>

    <div class="gacha__footer">
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
      gotcha_audio: new Audio(process.env.VUE_APP_S3_URL + '/gotcha.mp3')
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
}

.gacha__img {
  width: 20vw;
  height: 40vh;
  margin: auto;
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
  width: 10vw;
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
</style>
