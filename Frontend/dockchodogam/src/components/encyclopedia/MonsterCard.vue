<template>
  <!-- isGot 확인해서 v-if 걸기 -->
  <div v-if="monster.got == true && monster.monsterId !== 0" class="card">
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
      <p>00{{ monster.monsterId }}</p>
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
  </div>
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
        title: `${this.monster.name}`,
        text: `타입 : ${this.monster.type}
        등급 : ${this.monster.grade}
        체력 : ${this.monster.hp}
        공격력 : ${this.monster.minAttack} ~ ${this.monster.maxAttack}
        `,
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
.card {
  display: flex;
  width: 20vw;
  height: 50vh;
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
