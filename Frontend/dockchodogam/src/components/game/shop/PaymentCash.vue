<template>
  <div class="cash">
    <div class="cash__header">
      <h2>💰 냥 충전소 💰</h2>
    </div>

    <div class="cash__body">
      <img
        class="cash__img"
        src="http://img3.tmon.kr/cdn4/deals/2021/10/04/4040092538/front_252ba_vaemg.jpg"
      />
      <button @click="onPaymentCash">충전하기</button>
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
}
.cash__header {
  text-align: center;
}
.cash__body {
  display: flex;
  flex-direction: column;
}
.cash__img {
  width: 20vw;
}
</style>
