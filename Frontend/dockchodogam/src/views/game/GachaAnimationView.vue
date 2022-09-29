<template>
  <div class="gacha">
    <div class="gacha__header">
      <h3 class="TITLE">🎉 축하합니다! {{ newMonster.name }}몬 획득 🎉</h3>
    </div>

    <div class="card">
      <div class="imgBx">
        <img
          :src="this.imageBaseUrl + '/' + newMonster.monsterId + '.png'"
          class="card__img"
          style="-webkit-user-drag: none"
        />
      </div>
      <div
        :class="{
          card__common: newMonster.grade == 'COMMON',
          card__rare: newMonster.grade == 'RARE',
          card__epic: newMonster.grade == 'EPIC',
          card__legendary: newMonster.grade == 'LEGENDARY',
          card__special: newMonster.grade == 'SPECIAL'
        }"
        class="contentBx"
      >
        <br />
        <p class="TITLE">00{{ newMonster.monsterId }}</p>
        <h3 class="TITLE">{{ newMonster.name }}몬</h3>
        <!-- <div class="size">
          <p>
            타입 : {{ this.newMonster.type }} <br />등급 :
            {{ this.newMonster.grade }}
            <br />
            체력 : {{ this.newMonster.hp }} <br />
            공격력 : {{ this.newMonster.minAttack }} ~
            {{ this.newMonster.maxAttack }}
          </p>
        </div> -->
      </div>
    </div>
    <div class="gacha__footer">
      <div class="buttons">
        <button class="btn" @click="goToDogam">도감에서 확인하기</button>
        <button class="btn" @click="gachaAgain">한번 더 뽑기</button>
        <!-- <button class="btn" @click="goToGameShop">상점으로 돌아가기</button> -->
      </div>
    </div>
    <div class="shop__exit" @click="goToGameShop()">
      <font-awesome-icon icon="fa-solid fa-x" size="xl" />
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapGetters } from 'vuex'
import { BASE_URL } from '@/constant/BASE_URL'
import swal from 'sweetalert'
import JSConfetti from 'js-confetti'

const jsConfetti = new JSConfetti()

// jsConfetti.addConfetti({
//   // emojis: ['🌈', '⚡️', '💥', '✨', '💫', '🌸']
// })

export default {
  data() {
    return {
      newMonster: {},
      userInfo: JSON.parse(localStorage.getItem('userInfo')),
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    fetchMonsterGacha() {
      axios({
        url: 'https://j7e201.p.ssafy.io/api/v1/game/monster/pick/3',
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          // 'Content-Type': 'application/json'
        }
      })
        .then((res) => console.log(res)((this.newMonster = res.data)))
        .catch((err) => console.log(err))
    },
    goToDogam() {
      this.$router.push({
        path: '/encyclopedia'
      })
    },
    goToGameShop() {
      this.$router.push({
        path: '/game/shop'
      })
    },
    gachaAgain() {
      if (confirm('200냥을 내고 뽑기를 한번 더 진행하시겠습니까?') === true) {
        if (this.userInfo.money >= 200) {
          this.$router.push({
            path: '/game/shop/gacha'
          })
        } else {
          swal({
            title: '보유하신 냥이 부족합니다 😢',
            icon: 'error',
            buttons: false,
            timer: 1500
          })
          this.$router.push({
            path: '/game/shop'
          })
        }
      } else {
        return false
      }
    },
    startConfetti() {
      jsConfetti.addConfetti({
        // emojis: ['🌈', '⚡️', '💥', '✨', '💫', '🌸']
      })
    }
  },
  created() {
    console.log('시작')
    this.fetchMonsterGacha()
    document.cookie = 'safeCookie1=foo; SameSite=Lax'
    document.cookie = 'safeCookie2=foo'
    document.cookie = 'crossCookie=bar; SameSite=None; Secure'
    this.startConfetti()
  }
}
</script>

<style scoped>
.gacha {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100vh;
  width: 100vw;
  background-color: #ececec;
}

.gacha__header {
  text-align: center;
  margin-top: 5vh;
  margin-bottom: 5vh;
}

.card {
  position: relative;
  align-self: center;
  width: 15vw;
  height: 50vh;
  background: white;
  border-radius: 20px;
  overflow: hidden;
  margin-bottom: 2vh;
  margin-top: 2vh;
  /* margin: 0; */
}

.container .card .imgBx {
  /* position: absolute; */
  top: 35%;
  transform: translateY(0%);
  z-index: 10000;
  width: 100%;
  height: 220px;
  transition: 0.5s;
}

.container .card:hover .imgBx {
  top: 30%;
  /* transform: translateY(-20%); */
}

.container .card .imgBx img {
  /* position: absolute; */
  top: 50%;
  /* left: 50%; */
  transition: transform 0.25s ease;
  /* transform: translate(-20%, -20%); */
  width: 50%;
}

.container .card .contentBx {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 20vh;
  text-align: center;
  transition: 1s;
  z-index: 10;
}

.container .card:hover .contentBx {
  height: 30vh;
}

.container .card .contentBx h2,
.container .card .contentBx p {
  position: relative;
  font-weight: 600;
  font-size: 1em;
  color: #000000;
  margin: 0;
  text-align: center;
}

.container .card .contentBx .size,
.container .card .contentBx .color {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 8px 20px;
  transition: 0.5s;
  opacity: 0;
  visibility: hidden;
  padding-top: 0;
  padding-bottom: 0;
  color: white;
}

.container .card:hover .contentBx .size {
  opacity: 1;
  visibility: visible;
  transition-delay: 0.5s;
}

.card__common {
  background-color: gray;
}
.card__rare {
  background-color: skyblue;
}
.card__epic {
  background-color: violet;
}
.card__legendary {
  background-color: yellow;
}
.card__special {
  background-image: url(https://img.freepik.com/premium-vector/glitters-rainbow-sky-shiny-rainbows-pastel-color-magic-fairy-starry-skies-and-glitter-sparkles-background-illustration_102902-1299.jpg?w=2000);
}

.gacha__footer {
  display: flex;
  flex-direction: row;
  align-self: center;
}

.buttons button {
  justify-content: space-around;
  margin: 2vh;
  border-radius: 20px;
}

.btn {
  width: 10vw;
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  color: black;
  text-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  margin: auto;
  box-shadow: 0 0 5px #000;
  background-color: #a7c957;
  background-image: #a7c957;
}
.btn:hover {
  background-position: right center;
  background-color: #467302;
  color: white;
}

.shop__exit {
  width: 30px;
  height: 30px;
  border: 2px groove black;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 20px;
  right: 30px;
  cursor: pointer;
}
</style>
