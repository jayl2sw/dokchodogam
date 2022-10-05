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
          <PaymentCash />
        </div>
        <div class="shop__item">
          <PaymentPackage />
        </div>
        <div class="shop__item">
          <MonsterGacha />
        </div>
      </div>
    </div>
    <div class="shop__exit" @click="goToArenaMain()">
      <font-awesome-icon icon="fa-solid fa-circle-xmark" />
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
.shop__exit {
  width: 4vw;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 4vh;
  right: 8vw;
  cursor: pointer;
}
svg {
  width: 5vh;
  height: 5vh;
  color: #ffe140;
  transition: 0.3s;
}
svg:hover {
  color: #ffef92;
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
  .shop__exit {
    display: none;
  }
}
@media only screen and (orientation: landscape) {
  #warning-message {
    display: none;
  }
}
@media screen and (max-width: 850px) {
  .shop__exit {
    top: 3vh;
    right: 6vw;
    width: 25px;
    height: 25px;
  }
  .TITLE {
    font-size: 3vw;
  }
}
</style>
