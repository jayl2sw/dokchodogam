<template>
  <div class="package">
    <div class="package__header">
      <h3>스타터팩 구매</h3>
    </div>

    <div class="package__body">
      <img class="package__img" src="@/assets/loading/1.png" />
      <button @click="onPaymentPackage">결제하기</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  methods: {
    onPaymentPackage() {
      /* 1. 가맹점 식별하기 */
      const IMP = window.IMP
      IMP.init('imp45264010')

      IMP.request_pay(
        {
          pg: 'html5_inicis', // PG사
          pay_method: 'card', // 결제수단
          merchant_uid: `mid_${new Date().getTime()}`, // 주문번호
          amount: 1000, // 결제금액
          name: '재화충전', // 주문명
          buyer_name: '홍길동' // 구매자 이름 //이거 username으로 넣기
        },
        (res) => {
          if (res.sucess) {
            // 결제 성공시 로직
            // axios로 HTTP 요청
            axios({
              url: '{서버 결제 정보 받는 endpoint}',
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              data: {
                imp_uid: res.imp_uid,
                merchant_uid: res.merchant_uid
              }
            }).then((data) => {
              // 서버 결제 API 성공시 로직
              // 하민몬 띄우기
            })
          } else {
            // 결제 실패시 로직
            alert(`결제에 실패하였습니다. 에러 내용 : ${res.error_msg}`)
          }
        }
      )
    }
  }
}
</script>

<style scoped>
.package {
}
.package__header {
  text-align: center;
}
.package__body {
  display: flex;
  flex-direction: column;
}
.package__img {
  width: 20vw;
}
</style>
