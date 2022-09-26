<template>
  <div v-if="userInfo.starter == true" class="package">
    <div class="package__header">
      <h3>🐣 스타터팩 🐣</h3>
    </div>

    <div class="package__body">
      <img class="package__img" src="@/assets/loading/1.png" />
      <button @click="onPaymentPackage">구매하기</button>
    </div>
  </div>

  <div v-else class="package">
    <div class="package__header">
      <h3>패키지 🎮</h3>
    </div>

    <div class="package__body">
      <p>💬 새로운 패키지를 준비 중입니다.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapGetters } from 'vuex'
import { BASE_URL } from '@/constant/BASE_URL'
import swal from 'sweetalert'

export default {
  data() {
    return {
      // userInfo: JSON.parse(localStorage.getItem('userInfo')),
      packageMonsters: {}
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
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
                itemId: 2
              }
            }).then((data) => {
              // 몬스터 3개 정보 올 것,, 아마도 ?
              // 담아서 PackageAnimationView으로 넘기기
              this.packageMonsters = data
              this.$router.push({
                path: '/game/shop/package',
                params: this.packageMonsters
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
    },
    goToGameShop() {
      this.$router.push({ path: '/game/shop' })
    }
    // showModal() {
    //   this.$bvModal.show('giveHamin')
    // }
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
