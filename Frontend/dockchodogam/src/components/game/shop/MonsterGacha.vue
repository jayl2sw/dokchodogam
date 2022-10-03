<template>
  <div class="gacha">
    <div class="gacha__header">
      <h3 h2 class="TITLE">독초몬 뽑기</h3>
      <p>✨ 일반 ~ 스페셜 독초몬 ✨등장!</p>
    </div>
    <div class="gacha__body">
      <img class="gatcha__img" :src="require('@/assets/shop/monster.png')" />
    </div>
    <div class="gacha__footer">
      <button class="btn" @click="doubleCheck">
        <span class="TITLE">💰 100냥</span>
      </button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import swal from 'sweetalert'
import Swal from 'sweetalert2'

const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
    title: 'custom-title-class',
    confirmButton: 'btn btn-success',
    cancelButton: 'btn btn-danger'
  }
  // buttonsStyling: false
})

export default {
  // data() {
  //   return { userInfo: JSON.parse(localStorage.getItem('userInfo')) }
  // },
  data() {
    return {
      btn_audio: new Audio(process.env.VUE_APP_S3_URL + '/button.mp3')
    }
  },
  computed: {
    ...mapGetters(['nowUserInfo'])
  },
  methods: {
    ...mapActions(['fetchMonsterGacha', 'fetchnowUserInfo']),
    doubleCheck() {
      this.btn_audio.play()
      swalWithBootstrapButtons
        .fire({
          title: '100냥을 내고 뽑기를 하시겠어요?',
          text: '원하는 독초몬을 생각하며 고고고 🙂',
          icon: 'question',
          showCancelButton: true,
          confirmButtonText: '예',
          cancelButtonText: '아니오',
          reverseButtons: true
        })
        .then((res) => {
          if (res.value) {
            // console.log(result)
            this.fetchGacha()
          }
        })
    },
    fetchGacha() {
      if (this.nowUserInfo.money >= 100) {
        this.fetchMonsterGacha()
        // this.fetchUserInfo()
        this.$router.replace({
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
    }
  }
}
</script>

<style scoped>
.gacha {
  width: 30vw;
  height: 60vh;
  /* background-color: green; */
}
.gacha__header {
  text-align: center;
  margin-top: 5vh;
}

.gacha__header h3 {
  margin-bottom: 1vmin;
}

.gacha__header p {
  margin: 0;
}
.gacha__body {
  display: flex;
  flex-direction: column;
  justify-content: center;
  min-height: 30vh;
  /* margin-bottom: 0; */
}

.gatcha__img {
  width: 60%;
  margin-left: auto;
  margin-right: auto;
}

.gacha__footer {
  display: flex;
  justify-content: center;
  /* margin-bottom: 2vh; */
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
</style>
