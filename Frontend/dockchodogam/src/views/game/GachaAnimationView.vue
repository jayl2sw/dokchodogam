<template>
  <div>
    animation

    <div>
      <div>
        <img src="@/assets/loading/3.png" />
        <p>몬스터 이름</p>
      </div>
      <button @click="goToDogam">도감에서 확인하기</button>
      <button @click="gachaAgain">다시 뽑기</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      newMonster: {},
      cash: 100
    }
  },
  methods: {
    fetchMonsterGacha() {
      axios({
        url: '/game/monster/pick',
        method: 'POST'
      })
        .then((res) => (this.newMonster = res))
        .catch((err) => console.log(err))
    },
    goToDogam() {
      this.$router.push({
        path: '/encyclopedia'
      })
    },
    gachaAgain() {
      if (confirm('200냥을 내고 뽑기를 한번 더 진행하시겠습니까?') === true) {
        if (this.cash >= 200) {
          this.$router.push({
            path: '/game/shop/gacha'
          })
        } else {
          alert('보유하신 냥이 부족합니다.')
          this.$router.push({
            path: '/game/shop'
          })
        }
      } else {
        return false
      }
    }
  },
  created() {
    this.fetchMonsterGacha()
  }
}
</script>

<style></style>
