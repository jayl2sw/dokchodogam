<template>
  <div class="cash">
    <div class="cash__header">
      <h3 class="TITLE">λ₯ μΆ©μ μ</h3>
    </div>

    <div class="cash__body">
      <img
        class="cash__img"
        :src="require('@/assets/shop/cash.png')"
        alt="cash__img"
      />
    </div>

    <div class="cash__footer">
      <button class="btn" @click="onPaymentCash">
        <span class="TITLE">π° 9,900μ</span>
      </button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import swal from 'sweetalert'
import { mapActions } from 'vuex'

const IMP = window.IMP
IMP.init('imp40805235')

export default {
  data() {
    return {
      userInfo: JSON.parse(localStorage.getItem('userInfo')),
      btn_audio: new Audio(process.env.VUE_APP_S3_URL + '/button.mp3')
    }
  },
  methods: {
    ...mapActions(['fetchnowUserInfo']),
    onPaymentCash: function () {
      this.btn_audio.play()
      IMP.request_pay(
        {
          pg: 'danal_tpay',
          pay_method: 'card',
          merchant_uid: `mid_${new Date().getTime()}`,
          name: 'μ¬νμΆ©μ :1,000λ₯',
          amount: 9900,
          buyer_email: `${this.userInfo.email}`,
          buyer_name: `${this.userInfo.username}`,
          buyer_tel: '010-1234-5678'
        },
        (rsp) => {
          if (rsp.success) {
            axios({
              url: 'https://j7e201.p.ssafy.io/api/v1/game/monster/pick/1',
              method: 'GET',
              headers: {
                AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
              }
            })
              .then((data) => {
                this.fetchnowUserInfo()
                swal({
                  title: 'λ₯ μΆ©μ  μλ£! π°',
                  text: '1,000λ₯μ΄ μΆ©μ  λμμ΅λλ€πΈ',
                  icon: 'success',
                  buttons: false,
                  timer: 1500
                })
              })
              .catch((err) => console.log(err))
          } else {
            swal({
              title: 'κ²°μ μ μ€ν¨νμμ΅λλ€ π’',
              text: `${rsp.error_msg}`,
              icon: 'error',
              buttons: false,
              timer: 1500
            })
          }
        }
      )
    }
  },
  created() {
    document.cookie = 'safeCookie1=foo; SameSite=Lax'
    document.cookie = 'safeCookie2=foo'
    document.cookie = 'crossCookie=bar; SameSite=None; Secure'
  }
}
</script>

<style scoped>
.cash {
  width: 30vw;
  height: 60vh;
}
.cash__header {
  text-align: center;
  margin-top: 5vh;
}
.cash__header h3 {
  margin-bottom: 1vmin;
}
.cash__body {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 40vh;
}
.cash__img {
  width: 60%;
  margin-left: auto;
  margin-right: auto;
}

.cash__footer {
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
    font-size: 2vw;
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
  .cash__img {
    width: 15vw;
  }
  img {
    width: 15vw;
    height: 15vw;
  }
}
</style>
