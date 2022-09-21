<template>
  <div class="gacha">
    <div class="gacha__header">
      <h3>독초몬 뽑기</h3>
    </div>
    <div class="gacha__body">
      <img class="gatcha__img" src="@/assets/tree.png" />
      <button @click="doubleCheck">뽑기</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cash: 300
    }
  },
  methods: {
    doubleCheck() {
      if (confirm('200냥을 내고 뽑기를 진행하시겠습니까?') === true) {
        if (this.cash >= 200) {
          this.$router.push({
            path: '/game/shop/gacha'
          })
        } else {
          alert('보유하신 냥이 부족합니다.')
          return false
        }
      } else {
        return false
      }
    }
  }
}
</script>

<style scoped>
.gacha {
}
.gacha__header {
  text-align: center;
}

.gacha__body {
  display: flex;
  flex-direction: column;
}

.gatcha__img {
  width: 20vw;
}
</style>
