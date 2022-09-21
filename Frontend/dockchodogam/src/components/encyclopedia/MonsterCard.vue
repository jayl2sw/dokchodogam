<template>
  <!-- isGot 확인해서 v-if 걸기 -->
  <!-- <div v-if="monster.isGot == true" @click="openModal" >
    <p>{monster.monsterId}</p>
    <h3>{monster.name}</h3>
    <img :src="monster.images" v-b-modal.modalPopover class="card" />
      <div>
        <b-modal
          id="modalPopover"
          title="{{monster.name}}"
          scrollable
          hide-footer="true"
        >
          <p>몬스터 디테일 ..</p>
        </b-modal>
      </div>

  </div>

  <div v-else>
    <p>{monster.monsterId}</p>
    <h3>???</h3>
    <img class="dontHaveImg" :src="monster.images" />
  </div> -->

  <div class="card">
    <div v-b-modal.modalPopover class="monster__card">
      <img
        src="https://i.pinimg.com/550x/06/50/31/065031061c7642d5fa307a6ead4da3f8.jpg"
        class="card__img"
      />
      <p>카드입니다.</p>

      <div>
        <b-modal
          id="modalPopover"
          title="몬스터 이름"
          scrollable
          hide-footer="true"
        >
          <p>몬스터 디테일 ..</p>
        </b-modal>
      </div>
    </div>
  </div>
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
      monsterDetail: {}
    }
  },
  methods: {
    fetchMonsterDetail() {
      axios({
        url: '/game/monster/detail/',
        method: 'get',
        data: {
          data: this.monster.monsterId
        }
      })
        .then((res) => {
          console.log(res)
          this.monsterDetail = res
        })
        .catch((err) => {
          console.log(err)
        })
    }
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
</style>
