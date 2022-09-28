<template>
  <div class="cash">
    <div class="cash__header">
      <h3 class="TITLE">냥 충전소</h3>
    </div>

    <div class="cash__body">
      <img class="cash__img" :src="require('@/assets/shop/cash.png')" />
      <button class="btn" @click="onPaymentCash">
        <span class="TITLE">💰 9,900원</span>
      </button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import swal from 'sweetalert'
import { mapGetters } from 'vuex'

export default {
  // data() {
  //   return {
  //     userInfo: JSON.parse(localStorage.getItem('userInfo'))
  //   }
  // },
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    onPaymentCash() {
      /* 1. 가맹점 식별하기 */
      const IMP = window.IMP
      IMP.init('imp40805235')

      IMP.request_pay(
        {
          pg: 'html5_inicis', // 카카오페이
          // pay_method: 'card', // 결제수단
          merchant_uid: `mid_${new Date().getTime()}`, // 주문번호
          amount: 9900, // 결제금액
          name: '재화충전:결제테스트', // 주문명
          buyer_name: `${this.userInfo.username}` // 구매자 이름 //이거 username으로 넣기
        },
        (res) => {
          if (res.sucess) {
            // 결제 성공시 로직
            // axios로 HTTP 요청
            axios({
              url: `${BASE_URL} + api/v1/game/monster/pick`,
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
                AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
              },
              data: {
                itemId: 1
              }
            }).then((data) => {
              // 서버 결제 API 성공시 로직
              swal({
                title: '냥 충전 완료! 💰',
                text: '1,000냥이 충전 되었습니다😸',
                icon: 'success',
                buttons: false,
                timer: 1500
              })
            })
          } else {
            // 결제 실패시 로직
            swal({
              title: '결제에 실패하였습니다 😢',
              text: `${res.error_msg}`,
              icon: 'error',
              buttons: false,
              timer: 1500
            })
          }
        }
      )
    }
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
  margin-bottom: 5vh;
}
.cash__body {
  display: flex;
  flex-direction: column;
  height: 20vh;
}
.cash__img {
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
