<template>
  <div class="container">
    <div v-if="monster.got == true && monster.monsterId !== 0" class="card">
      <div class="imgBx">
        <img
          :src="this.imageBaseUrl + '/' + monster.monsterId + '.png'"
          class="card__img"
          style="-webkit-user-drag: none"
        />
      </div>
      <div
        :class="{
          card__common: monster.grade == 'COMMON',
          card__rare: monster.grade == 'RARE',
          card__epic: monster.grade == 'EPIC',
          card__legendary: monster.grade == 'LEGENDARY',
          card__special: monster.grade == 'SPECIAL'
        }"
        class="contentBx"
      >
        <br />
        <p class="TITLE">00{{ monster.monsterId }}</p>
        <h3 class="TITLE">{{ monster.name }}몬</h3>
        <div class="size">
          <p>
            타입 : {{ this.monster.type }} <br />등급 :
            {{ this.monster.grade }} <br />
            체력 : {{ this.monster.hp }} <br />
            공격력 : {{ this.monster.minAttack }} ~ {{ this.monster.maxAttack }}
          </p>
        </div>
      </div>
    </div>

    <div
      v-else-if="monster.got == false && monster.monsterId !== 0"
      class="card"
    >
      <div class="card__dontHaveimg">
        <img
          :src="this.imageBaseUrl + '/' + monster.monsterId + '.png'"
          class="card__img"
          style="-webkit-user-drag: none"
        />
      </div>
      <div class="contentBx">
        <p>00{{ monster.monsterId }}</p>
        <h2>???</h2>
        <div class="size">
          <p>
            <br />
            뽑아서 확인하세요.
          </p>
        </div>
      </div>
    </div>
  </div>
  <!-- isGot 확인해서 v-if 걸기 -->
  <!-- <div v-if="monster.got == true && monster.monsterId !== 0" class="card">
    <div
      v-b-modal.modal-card
      @click=";[storeMonster(monster), openDetail()]"
      :class="{
        card__common: monster.grade == 'COMMON',
        card__rare: monster.grade == 'RARE',
        card__epic: monster.grade == 'EPIC',
        card__legendary: monster.grade == 'LEGENDARY',
        card__special: monster.grade == 'SPECIAL'
      }"
    >
      <h3>00{{ monster.monsterId }}</h3>
      <h3>{{ monster.name }}몬</h3>
      <img
        :src="this.imageBaseUrl + '/' + monster.monsterId + '.png'"
        class="card__img"
        style="-webkit-user-drag: none"
      />
    </div>
  </div>

  <div v-else-if="monster.got == false && monster.monsterId !== 0" class="card">
    <div>
      <p>00{{ monster.monsterId }}</p>
      <h3>???</h3>
      <img
        class="card__dontHaveimg"
        :src="this.imageBaseUrl + '/' + monster.monsterId + '.png'"
        style="-webkit-user-drag: none"
      />
    </div>
  </div> -->
</template>

<script>
import axios from 'axios'
// import { BASE_URL } from '@/constant/BASE_URL'
// import MonsterDetail from '@/components/encyclopedia/MonsterDetail.vue'
import Swal from 'sweetalert2'

export default {
  props: {
    monster: Object
  },
  data() {
    return {
      modal: false,
      monsterDetail: {},
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  methods: {
    openDetail() {
      Swal.fire({
        title: `${this.monster.name}몬`,
        html: `<br /> 
        <p>타입 : ${this.monster.type}</p>
        <p>등급 : ${this.monster.grade}</p>
        <p>체력 : ${this.monster.hp} </p>
        공격력 : ${this.monster.minAttack} ~ ${this.monster.maxAttack}`,
        imageUrl: `${this.imageBaseUrl}/${this.monster.monsterId}.png`,
        imageWidth: 250,
        imageHeight: 250,
        imageAlt: 'Custom image',
        showConfirmButton: false
      })
    },
    async storeMonster(a) {
      this.monsterDetail = a
      // alert(a.name)
      // console.log(a)
      // console.log(this.monsterDetail)
    },

    fetchMonsterDetail() {
      axios({
        url: `https://j7e201.p.ssafy.io/api/v1/game/monster/detail/${this.monster.monsterId}`,
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      })
        .then((res) => {
          // console.log(this.monster)
          // console.log(`monsterDetail  : ${res.data}`)
          // console.log(this.monster)
          this.monsterDetail = res.data
          // console.log(this.monsterDetail)
        })
        .catch((err) => {
          console.log(err)
        })
    }
  },
  created() {
    this.fetchMonsterDetail()
  }
}
</script>

<style scoped>
body {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: #131313;
}

.container {
  position: relative;
}

.container .card {
  position: relative;
  width: 320px;
  height: 450px;
  background: white;
  border-radius: 20px;
  overflow: hidden;
  margin: 0;
}

.container .card .imgBx {
  position: absolute;
  top: 35%;
  transform: translateY(-50%);
  z-index: 10000;
  width: 100%;
  height: 220px;
  transition: 0.5s;
}

.container .card:hover .imgBx {
  top: 0%;
  transform: translateY(0%);
}

.container .card .imgBx img {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(-25deg);
  width: 270px;
}

.container .card .card__dontHaveimg {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 10000;
  width: 100%;
  height: 220px;
  transition: 0.5s;
  filter: brightness(100%);
}

.container .card:hover .card__dontHaveimg {
  top: 0%;
  transform: translateY(0%);
  filter: brightness(100%);
}

.container .card .card__dontHaveimg img {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(-25deg);
  width: 270px;
  filter: brightness(100%);
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
  letter-spacing: 1px;
  color: #000000;
  margin: 0;
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
</style>
