<template>
  <div v-if="nowUserInfo.starter == true" class="package">
    <div class="package__header">
      <h3 class="TITLE">🐣 스타터팩 🐣</h3>
      <p>⭐계정 당 1회 한정</p>
    </div>

    <div class="package__body">
      <img class="package__img" :src="require('@/assets/shop/package.png')" />
      <button class="btn" @click="onPaymentPackage">
        <span class="TITLE">💰 990원</span>
      </button>
    </div>
  </div>

  <div v-else class="package">
    <div class="package__header">
      <h3 class="TITLE">패키지 🎮</h3>
      <p>새로운 패키지를 준비중입니다</p>
    </div>

    <div class="package__body">
      <img class="package__img" :src="require('@/assets/shop/waiting.png')" />
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions, mapGetters } from 'vuex'
import { BASE_URL } from '@/constant/BASE_URL'
import swal from 'sweetalert'

const IMP = window.IMP
IMP.init('imp40805235')

export default {
  data() {
    return {
      // userInfo: JSON.parse(localStorage.getItem('userInfo')),
      packageMonsters: {}
    }
  },
  computed: {
    ...mapGetters(['nowUserInfo'])
  },
  methods: {
    ...mapActions(['monsterPackage, fetchnowUserInfo']),
    onPaymentPackage: function () {
      /* 1. 가맹점 식별하기 */
      // const IMP = window.IMP
      // IMP.init('imp40805235')

      IMP.request_pay(
        {
          pg: 'html5_inicis', // PG사
          // pay_method: 'card', // 결제수단
          merchant_uid: `mid_${new Date().getTime()}`, // 주문번호
          amount: 990, // 결제금액
          name: '독초도감:스타터팩', // 주문명
          buyer_name: `${this.userInfo.username}` // 구매자 이름 //이거 username으로 넣기
        },
        (rsp) => {
          if (rsp.sucess) {
            // 결제 성공시 로직
            // axios로 HTTP 요청
            axios({
              url: 'https://j7e201.p.ssafy.io/api/v1/game/monster/pick/2',
              method: 'GET',
              headers: {
                'Content-Type': 'application/json',
                AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
              }
            })
              .then((data) => {
                // 몬스터 3개 정보 올 것,, 아마도 ?
                // 담아서 PackageAnimationView으로 넘기기
                this.fetchMonsterPackage()
                // this.fetchnowUserInfo()
                // this.packageMonsters = data.data
                this.$router.replace({
                  path: '/game/shop/package'
                  // params: this.packageMonsters
                })
              })
              .catch((err) => console.log(err))
          } else {
            this.fetchnowUserInfo()
            // 결제 실패시 로직
            swal({
              title: '결제에 실패하였습니다 😢',
              text: `${rsp.error_msg}`,
              icon: 'error',
              buttons: false,
              timer: 1500
            })
          }
        }
      )
    },
    goToGameShop() {
      this.$router.replace({ path: '/game/shop' })
    }
    // showModal() {
    //   this.$bvModal.show('giveHamin')
    // }
  },
  created() {
    document.cookie = 'safeCookie1=foo; SameSite=Lax'
    document.cookie = 'safeCookie2=foo'
    document.cookie = 'crossCookie=bar; SameSite=None; Secure'
  }
}
</script>

<style scoped>
.package {
}
.package__header {
  text-align: center;
  margin-top: 5vh;
}
.package__header p,
h3 {
  margin: 0;
}
.package__body {
  display: flex;
  flex-direction: column;
}
.package__img {
  width: 20vw;
  margin-left: auto;
  margin-right: auto;
}
.shop__modal {
  width: 20vw;
  height: 20vh;
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
