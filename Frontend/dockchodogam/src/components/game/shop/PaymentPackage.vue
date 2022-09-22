<template>
  <!-- 연결시키면 주석 풀기.. -->
  <!-- <div v-if="userStarter == false" class="package">
    <div class="package__header">
      <h3>스타터팩 구매</h3>
    </div>

    <div class="package__body">
      <img class="package__img" src="@/assets/loading/1.png" />
      <button @click="onPaymentPackage">결제하기</button>
    </div>
  </div>

  <div v-else class="package">
    <div class="package__header">
      <h3>스타터팩 구매</h3>
    </div>

    <div class="package__body">
      <p>새로운 패키지를 준비 중입니다.</p>
    </div>
  </div> -->

  <div class="package">
    <div class="package__header">
      <h3>스타터팩 구매</h3>
    </div>

    <div class="package__body">
      <img class="package__img" src="@/assets/loading/1.png" />
      <button @click="onPaymentPackage">결제하기</button>

      <!-- 결제 후 하민몬 증정 근데 에러 나는 중 -->
      <b-modal id="giveHamin" hide-footer>
        <div class="d-block text-center">
          <h3>패키지 첫 구매 보상!</h3>
        </div>
        <b-button class="mt-3" block @click="goToGameShop">Close Me</b-button>
      </b-modal>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  // data() {
  //   return {
  //     user: {}
  //   }
  // },
  // computed: {
  //   userStarter: this.user.starter
  // },
  data() {
    return {
      checkStarter: false
    }
  },
  methods: {
    fetchUser() {
      axios({
        url: '/user/myinfo',
        method: 'GET',
        headers: {}
      })
        .then((res) => {
          this.user = res
        })
        .catch((err) => console.log(err))
    },
    onPaymentPackage() {
      /* 1. 가맹점 식별하기 */
      const IMP = window.IMP
      IMP.init('imp40805235')

      IMP.request_pay(
        {
          pg: 'html5_inicis', // PG사
          pay_method: 'card', // 결제수단
          merchant_uid: `mid_${new Date().getTime()}`, // 주문번호
          amount: 990, // 결제금액
          name: '독초도감:스타터팩', // 주문명
          buyer_name: '홍길동' // 구매자 이름 //이거 username으로 넣기
        },
        (res) => {
          if (res.sucess) {
            // 결제 성공시 로직
            // axios로 HTTP 요청
            axios({
              url: '/game/monster/pick',
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              data: {
                itemId: 2
              }
            }).then((data) => {
              // 서버 결제 API 성공시 로직
              // 하민몬 띄우기
            })
          } else {
            // 결제 실패시 로직
            this.showModal()
            alert(`결제에 실패하였습니다. 에러 내용 : ${res.error_msg}`)
          }
        }
      )
    },
    goToGameShop() {
      this.$router.push({ path: '/game/shop' })
    },
    showModal() {
      this.$bvModal.show('giveHamin')
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
.shop__modal {
  width: 20vw;
  height: 20vh;
}
</style>
