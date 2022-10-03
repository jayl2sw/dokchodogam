<template>
  <div class="shop">
    <div id="warning-message">
      <p class="TITLE">
        아레나는<br /><br /><span class="emphasize">"가로 화면 전용"</span>
        게임입니다.
      </p>
    </div>
    <div class="shop__boxes">
      <div class="shop__top">
        <div class="nickname">
          <h1 class="TITLE">
            {{ this.nowUserInfo.nickname }}님을 위한 상점 🎁
          </h1>
        </div>
        <div class="money">
          <h1 class="TITLE">👛 : {{ this.nowUserInfo.money }}냥</h1>
        </div>
      </div>
      <div class="shop__bottom">
        <div class="shop__item">
          <!-- <p class="special__cash">특별가격!</p> -->
          <payment-cash />
        </div>
        <div class="shop__item">
          <payment-package />
        </div>
        <div class="shop__item">
          <monster-gacha />
        </div>
      </div>
    </div>
    <div class="shop__exit" @click="goToArenaMain()">
      <font-awesome-icon icon="fa-solid fa-x" size="xl" />
    </div>
  </div>
</template>

<script>
import PaymentCash from '@/components/game/shop/PaymentCash.vue'
import PaymentPackage from '@/components/game/shop/PaymentPackage.vue'
import MonsterGacha from '@/components/game/shop/MonsterGacha.vue'
import { mapGetters, mapActions } from 'vuex'

export default {
  data() {
    return {
      // nowUserInfo: JSON.parse(localStorage.getItem('fetchnowUserInfo')),
      audio: new Audio(process.env.VUE_APP_S3_URL + '/shop.mp3'),
      btn_audio: new Audio(process.env.VUE_APP_S3_URL + '/button.mp3')
    }
  },
  components: {
    PaymentCash,
    PaymentPackage,
    MonsterGacha
  },
  computed: {
    ...mapGetters(['nowUserInfo'])
  },
  methods: {
    ...mapActions(['fetchnowUserInfo']),
    goToArenaMain() {
      this.btn_audio.play()
      this.$router.replace({ path: '/game/arena' })
    }
    // unLoadEvent: function (event) {
    //   if (this.isLeaveSite) return

    //   event.preventDefault()
    //   event.returnValue = ''
    // }
  },
  created() {
    this.fetchnowUserInfo()
  },
  mounted() {
    this.audio.loop = true
    this.audio.volume = 0.5
    setTimeout(() => {
      this.audio.play()
    }, 300)
  },
  beforeUnmount() {
    this.audio.pause()
  }
  // mounted() {
  //   window.addEventListener('beforeunload', this.unLoadEvent)
  // },
  // beforeUnmount() {
  //   window.removeEventListener('beforeunload', this.unLoadEvent)
  // }
}
</script>

<style scoped>
.shop {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 10vh 0;
  background-image: url('@/assets/game_background.png');
  background-repeat: no-repeat;
  background-size: cover;
}
.shop__boxes {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.shop__top {
  display: flex;
  justify-content: space-around;
  height: 20vh;
}
.shop__bottom {
  display: flex;
  justify-content: space-around;
}

.shop__item {
  width: 30vw;
  height: 60vh;
  background-color: #f6f6f6;
  border-radius: 20px;
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

.special__cash {
  width: 50%;
  color: white;
  font-family: 'UhBeeSe_hyun';
  padding: 1vh;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
  border-radius: 50px;
  background-image: url(https://previews.123rf.com/images/surachetkhamsuk/surachetkhamsuk1410/surachetkhamsuk141000003/32388683-%EB%B9%A8%EA%B0%84-%EB%B0%98%EC%A7%9D%EC%9D%B4-%EB%B0%9C%EB%A0%8C%ED%83%80%EC%9D%B8%EC%9D%98-%EB%82%A0-%EC%B6%94%EC%83%81%EC%A0%81-%EC%9D%B8-%EB%B0%B0%EA%B2%BD.jpg);
}
@media only screen and (orientation: portrait) {
  .shop {
    background-image: none;
    background-color: white;
    height: 100vh;
  }
  .shop__boxes {
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
}
@media only screen and (orientation: landscape) {
  #warning-message {
    display: none;
  }
}
/* @media screen and (max-width: 850px) {
  .game__exit {
    top: 10px;
    right: 10px;
    width: 25px;
    height: 25px;
  }
  svg {
    width: 10px;
  }
} */
</style>
