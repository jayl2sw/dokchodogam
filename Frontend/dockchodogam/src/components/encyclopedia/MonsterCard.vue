<template>
  <div class="container">
    <div class="card">
      <div class="imgBx">
        <img
          :src="this.imageBaseUrl + '/' + monster.monsterId + '.png'"
          class="card__img"
          style="-webkit-user-drag: none"
        />
      </div>
      <div class="contentBx">
        <p>00{{ monster.monsterId }}</p>
        <h2>{{ monster.name }}몬</h2>
        <div class="size">
          <p>
            <br />
            타입 : {{ this.monster.type }} <br />등급 :
            {{ this.monster.grade }} <br />
            체력 : {{ this.monster.hp }} <br />
            공격력 : {{ this.monster.minAttack }} ~ {{ this.monster.maxAttack }}
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
        // text: `타입 : ${this.monster.type}
        // 등급 : ${this.monster.grade}
        // 체력 : ${this.monster.hp}
        // 공격력 : ${this.monster.minAttack} ~ ${this.monster.maxAttack}
        // `,
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
  background: #232323;
  border-radius: 20px;
  overflow: hidden;
}

/* .container .card:before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: #9bdc28;
  clip-path: circle(150px at 80% 20%);
  transition: 0.5s ease-in-out;
} */

/* .container .card:hover:before {
  clip-path: circle(300px at 80% -20%);
} */

/* .container .card:after {
  content: 'Nike';
  position: absolute;
  top: 30%;
  left: -20%;
  font-size: 12em;
  font-weight: 800;
  font-style: italic;
  color: rgba(255, 255, 25, 0.05);
} */

.container .card .imgBx {
  position: absolute;
  top: 50%;
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

.container .card .contentBx {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 100px;
  text-align: center;
  transition: 1s;
  z-index: 10;
}

.container .card:hover .contentBx {
  height: 210px;
}

.container .card .contentBx h2 {
  position: relative;
  font-weight: 600;
  letter-spacing: 1px;
  color: #fff;
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

.container .card:hover .contentBx .color {
  opacity: 1;
  visibility: visible;
  transition-delay: 0.6s;
}

.container .card .contentBx .size h3,
.container .card .contentBx .color h3 {
  color: #fff;
  font-weight: 300;
  font-size: 14px;
  text-transform: uppercase;
  letter-spacing: 2px;
  margin-right: 10px;
}

.container .card .contentBx .size span {
  width: 26px;
  height: 26px;
  text-align: center;
  line-height: 26px;
  font-size: 14px;
  display: inline-block;
  color: #111;
  background: #fff;
  margin: 0 5px;
  transition: 0.5s;
  color: #111;
  border-radius: 4px;
  cursor: pointer;
}

/* .container .card .contentBx .size span:hover {
  background: #9bdc28;
} */

.container .card .contentBx a {
  display: inline-block;
  padding: 10px 20px;
  background: #fff;
  border-radius: 4px;
  margin-top: 10px;
  text-decoration: none;
  font-weight: 600;
  color: rgb(255, 254, 254);
  opacity: 0;
  transform: translateY(50px);
  transition: 0.5s;
  margin-top: 0;
}

.container .card:hover .contentBx a {
  opacity: 1;
  transform: translateY(0px);
  transition-delay: 0.75s;
}
.card {
  position: relative;
  width: 20vw;
  height: 50vh;
  margin: 0 auto;
  background: #000;
  border-radius: 15px;
  box-shadow: 0 15px 60px rgba(0, 0, 0, 0.5);
  display: flex;
}

.card__img {
  width: 100%;
  height: 20vh;
}
.card__dontHaveimg {
  width: 100%;
  height: 20vh;
  filter: brightness(0%);
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
  background-image: linear-gradient(
    to right,
    red,
    orange,
    yellow,
    green,
    blue,
    indigo,
    purple
  );
}
</style>
