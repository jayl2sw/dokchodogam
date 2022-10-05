<template>
  <div class="package">
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
import swal from 'sweetalert'

const IMP = window.IMP
IMP.init('imp40805235')

export default {
  data() {
    return {
      packageMonsters: {}
    }
  },
  computed: {
    ...mapGetters(['nowUserInfo'])
  },
  methods: {
    ...mapActions(['monsterPackage, fetchnowUserInfo']),
    onPaymentPackage: function () {
      IMP.request_pay(
        {
          pg: 'html5_inicis',
          merchant_uid: `mid_${new Date().getTime()}`,
          amount: 100,
          name: '독초도감:스타터팩',
          buyer_name: `${this.nowUserInfo.username}`
        },
        (rsp) => {
          console.log(rsp)
          if (rsp.sucess) {
            axios({
              url: 'https://j7e201.p.ssafy.io/api/v1/game/monster/pick/2',
              method: 'GET',
              headers: {
                AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
              }
            })
              .then((res) => {
                this.fetchMonsterPackage(res.data)
                this.$router.push({
                  path: '/game/shop/package'
                })
              })
              .catch((err) => {
                swal({
                  title: '결제에 실패하였습니다 😢',
                  text: '스타터팩 구매를 실패하였습니다.',
                  icon: 'error',
                  timer: 1500
                })
                this.$router.push({
                  path: '/game/shop/package'
                })
              })
          } else {
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
  },
  created() {
    document.cookie = 'safeCookie1=foo; SameSite=Lax'
    document.cookie = 'safeCookie2=foo'
    document.cookie = 'crossCookie=bar; SameSite=None; Secure'
  }
}
</script>

<style scoped>
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
@media screen and (max-width: 916px) {
  .TITLE {
    font-size: 2.2vw;
  }
  p {
    font-size: 1.5vw;
  }
}
</style>
