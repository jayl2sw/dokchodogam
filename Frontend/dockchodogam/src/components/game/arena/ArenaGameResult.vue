<template>
  <div class="gameResult">
    <div class="result__text">{{ resultInfo[1] }}</div>
    <div class="result__score">
      <p v-if="resultInfo[1] === '패배'">-2</p>
      <p v-else>+10</p>
    </div>
    <div class="result__images">
      <div v-for="(item, i) in myDeck" :key="i" class="result__dockcho"></div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['myDeck', 'resultInfo'],
  methods: {
    goToArenaMain() {
      this.$router.push({ path: '/game/arena' })
    }
  },
  mounted() {
    setTimeout(() => {
      this.goToArenaMain()
    }, 2000)
  }
}
</script>

<style scoped>
.gameResult {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 5vh 2vw;
  background-image: url('@/assets/game_background.png');
  background-repeat: no-repeat;
  background-size: cover;
}
.result__text {
  height: 20vh;
  margin: 2.5vh 0;
}
.result__score {
  height: 15vh;
  margin: 2.5vh;
}
.result__images {
  width: 96vw;
  height: 40vh;
  margin: 2.5vh;
  display: flex;
  justify-content: space-around;
}
.result__dockcho {
  border: 2px groove black;
  border-radius: 5px;
  height: 16vw;
  width: 16vw;
  margin: 0 1vw;
  padding: 0 3vw;
}
</style>
