<template>
  <div class="gacha">
    <div class="gacha__header">
      <h3 h2 class="TITLE">풀깨비 뽑기</h3>
    </div>
    <div class="gacha__body">
      <p>✨ 일반 ~ 스페셜 풀깨비 ✨등장!</p>
      <img
        class="gatcha__img"
        :src="require('@/assets/shop/monster.png')"
        alt="gatcha_img"
      />
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
})

export default {
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
          text: '원하는 풀깨비를 생각하며 고고고 🙂',
          icon: 'question',
          showCancelButton: true,
          confirmButtonText: '예',
          cancelButtonText: '아니오',
          reverseButtons: true
        })
        .then((res) => {
          if (res.value) {
            this.fetchGacha()
          }
        })
    },
    fetchGacha() {
      if (this.nowUserInfo.money >= 100) {
        this.fetchMonsterGacha()
        setTimeout(() => {
          this.$router.replace({
            path: '/game/shop/gacha'
          })
        }, 1000)
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
}
.gacha__header {
  text-align: center;
  margin-top: 5vh;
}

.gacha__header h3 {
  margin-bottom: 1vmin;
}

.gacha__body p {
  margin: 0;
}
.gacha__body {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 40vh;
}

.gatcha__img {
  width: 60%;
  margin-left: auto;
  margin-right: auto;
}

.gacha__footer {
  display: flex;
  justify-content: center;
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
@media screen and (max-width: 916px) {
  .TITLE {
    font-size: 2.2vw;
  }
  p {
    font-size: 1.5vw;
  }
  .btn > .TITLE {
    font-size: 2vw;
  }
  .btn {
    height: 6vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .gatcha__img {
    width: 15vw;
  }
  img {
    width: 15vw;
    height: 15vw;
  }
}
</style>
