<template>
  <!-- isGot 확인해서 v-if 걸기 -->
  <div v-if="monster.got == true" class="card">
    <div
      v-b-modal.modalPopover
      :class="{
        card__common: monsterDetail.grade == 'COMMON',
        card__rare: monsterDetail.grade == 'RARE',
        card__epic: monsterDetail.grade == 'EPIC',
        card__legendary: monsterDetail.grade == 'LEGENDARY',
        card__special: monsterDetail.grade == 'SPECIAL'
      }"
    >
      <h3>{{ monster.name }}</h3>
      <img
        src="https://i.pinimg.com/550x/06/50/31/065031061c7642d5fa307a6ead4da3f8.jpg"
        class="card__img"
      />
    </div>
    <div>
      <b-modal id="modalPopover" scrollable hide-footer="true">
        <p>모달이 안와요</p>
      </b-modal>
    </div>
  </div>

  <div v-else class="card">
    <h3>???</h3>
    <img
      class="dontHaveImg, card__img"
      src="https://i.pinimg.com/550x/06/50/31/065031061c7642d5fa307a6ead4da3f8.jpg"
    />
  </div>

  <!-- <div class="card">
    <div
      v-b-modal.modalPopover
      :class="{
        card__common: monsterDetail.grade == 'COMMON',
        card__rare: monsterDetail.grade == 'RARE',
        card__epic: monsterDetail.grade == 'EPIC',
        card__legendary: monsterDetail.grade == 'LEGENDARY',
        card__special: monsterDetail.grade == 'SPECIAL'
      }"
    >
      <img
        src="https://i.pinimg.com/550x/06/50/31/065031061c7642d5fa307a6ead4da3f8.jpg"
        class="card__img"
      />
      <p>{{ monster.name }}</p>

      <div>
        <b-modal id="modalPopover" scrollable hide-footer="true">
          <p>모달이 안와요</p>
        </b-modal>
      </div>
    </div>
  </div> -->
</template>

<script>
import axios from 'axios'

export default {
  props: {
    monster: Object
  },
  data() {
    return {
      modal: false,
      monsterDetail: []
    }
  },
  methods: {
    fetchMonsterDetail() {
      axios({
        url: `http://localhost:8081/api/v1/game/monster/detail/${this.monster.monsterId}`,
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      })
        .then((res) => {
          // console.log(res.data)
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
