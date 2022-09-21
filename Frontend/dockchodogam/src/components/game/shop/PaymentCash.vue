<template>
  <div class="cash">
    <div class="cash__header">
      <h2>캐시 충전</h2>
    </div>

    <div class="cash__body">
      <img
        class="cash__img"
        src="http://img3.tmon.kr/cdn4/deals/2021/10/04/4040092538/front_252ba_vaemg.jpg"
      />
      <button @click="onPaymentCash">결제하기</button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    onPaymentCash() {
      /* 1. 가맹점 식별하기 */
      const { IMP } = window
      IMP.init('imp45264010')

      /* 2. 결제 데이터 정의하기 */
      const data = {
        pg: 'html5_inicis', // PG사
        pay_method: 'card', // 결제수단
        merchant_uid: `mid_${new Date().getTime()}`, // 주문번호
        amount: 1000, // 결제금액
        name: '재화충전', // 주문명
        buyer_name: '홍길동', // 구매자 이름
        buyer_tel: '01012341234', // 구매자 전화번호
        buyer_email: 'example@example', // 구매자 이메일
        buyer_addr: '신사동 661-16', // 구매자 주소
        buyer_postcode: '06018' // 구매자 우편번호
      }

      /* 4. 결제 창 호출하기 */
      IMP.request_pay(data, this.callback)
    },
    callback(response) {
      /* 3. 콜백 함수 정의하기 */
      const { success, merchantUid, errorMsg } = response

      if (success) {
        alert('결제 성공')
      } else {
        alert(`캐시 충전 결제 실패: ${errorMsg}`)
      }
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
