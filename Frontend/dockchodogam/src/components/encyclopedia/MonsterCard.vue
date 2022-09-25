<template>
  <!-- isGot 확인해서 v-if 걸기 -->
  <div v-if="monster.got == true && monster.monsterId !== 0" class="card">
    <div
      v-b-modal.modal-card
      @click=";[storeMonster(monster), openModal]"
      :class="{
        card__common: monsterDetail.grade == 'COMMON',
        card__rare: monsterDetail.grade == 'RARE',
        card__epic: monsterDetail.grade == 'EPIC',
        card__legendary: monsterDetail.grade == 'LEGENDARY',
        card__special: monsterDetail.grade == 'SPECIAL'
      }"
    >
      <p>00{{ monster.monsterId }}</p>
      <h3>{{ monster.name }}몬</h3>
      <img
        src="https://i.pinimg.com/550x/06/50/31/065031061c7642d5fa307a6ead4da3f8.jpg"
        class="card__img"
      />

      <!-- backdrop에러남 -->
      <!-- <MonsterDetail
        @close="closeModal"
        v-if="modal"
        :monsterDetail="monsterDetail"
      >
      </MonsterDetail> -->
    </div>

    <b-modal
      :monsterDetail="monsterDetail"
      id="modal-card"
      hide-footer
      hide-header
      scrollable
    >
      <p>{{ monsterDetail.name }}</p>
      <p>타입 : {{ monsterDetail.type }}</p>
      <p>등급 : {{ monsterDetail.grade }}</p>
      <p>체력 : {{ monsterDetail.hp }}</p>
      <p>
        공격력 : {{ monsterDetail.minAttack }} ~
        {{ monsterDetail.maxAttack }}
      </p>
    </b-modal>

    <!-- <MonsterDetail :monsterDetail="monster" /> -->
  </div>

  <div v-else-if="monster.got == false && monster.monsterId !== 0" class="card">
    <p>00{{ monster.monsterId }}</p>
    <h3>???</h3>
    <img
      class="card__dontHaveimg"
      src="https://i.pinimg.com/550x/06/50/31/065031061c7642d5fa307a6ead4da3f8.jpg"
    />
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
// import MonsterDetail from '@/components/encyclopedia/MonsterDetail.vue'

export default {
  props: {
    monster: Object
  },
  data() {
    return {
      modal: false,
      monsterDetail: {}
    }
  },
  components: {
    // MonsterDetail
  },
  methods: {
    openModal() {
      this.modal = true
    },
    closeModal() {
      this.modal = false
    },
    async storeMonster(a) {
      this.monsterDetail = a
      // alert(a.name)
      // console.log(a)
      // console.log(this.monsterDetail)
    },

    fetchMonsterDetail() {
      axios({
        url: `${BASE_URL} + api/v1/game/monster/detail/${this.monster.monsterId}`,
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      })
        .then((res) => {
          // console.log(this.monster)
          console.log(`monsterDetail  : ${res.data}`)
          this.monsterDetail = res.data
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
