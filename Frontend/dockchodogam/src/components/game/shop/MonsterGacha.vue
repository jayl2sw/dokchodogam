<template>
  <div class="gacha">
    <div class="gacha__header">
      <h3 h2 class="TITLE">독초몬 뽑기</h3>
      <p>커먼 ~ 스페셜까지 모두 등장!</p>
    </div>
    <div class="gacha__body">
      <img class="gatcha__img" :src="require('@/assets/shop/monster.png')" />
      <button class="btn" @click="doubleCheck">
        <span class="TITLE">💰 200냥</span>
      </button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import swal from 'sweetalert'

export default {
  data() {
    return { userInfo: JSON.parse(localStorage.getItem('userInfo')) }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    ...mapActions(['fetchMonsterGacha']),
    doubleCheck() {
      // swal({
      //   text: '200냥을 내고 뽑기를 진행하시겠습니까?',
      //   buttons: ['취소', '확인']
      // }).then(function (result) {
      //   console.log(result)

      //   if (result === true) {
      //     if (this.userInfo.money >= 200) {
      //       this.$router.push({
      //         path: '/game/shop/gacha'
      //       })
      //     } else {
      //       swal({
      //         title: '보유하신 냥이 부족합니다. 😢',
      //         text: '냥을 모아서 다시 도전하세요!',
      //         icon: 'error',
      //         buttons: false,
      //         timer: 1500
      //       })
      //     }
      //   }
      if (confirm('100냥을 내고 뽑기를 진행하시겠습니까?') === true) {
        if (this.userInfo.money >= 100) {
          this.fetchMonsterGacha()
          this.$router.push({
            path: '/game/shop/gacha'
          })
        } else {
          swal({
            title: '보유하신 냥이 부족합니다 😢',
            text: '냥을 모아서 다시 도전하세요!',
            icon: 'error',
            buttons: false,
            timer: 1500
          })
          return false
        }
      } else {
        return false
      }
    }
  }
}
</script>

<style scoped>
.gacha {
}
.gacha__header {
  text-align: center;
  margin-top: 5vh;
}

.gacha__header h3 {
  margin-bottom: 1vh;
}

.gacha__header p {
  margin: 0;
}
.gacha__body {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.gatcha__img {
  width: 20vw;
  margin-left: auto;
  margin-right: auto;
}

.btn {
  width: 10vw;
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  color: black;
  text-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  margin: auto;
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
</style>
