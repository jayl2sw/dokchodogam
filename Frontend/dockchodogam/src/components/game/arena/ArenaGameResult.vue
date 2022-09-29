<template>
  <div class="gameResult">
    <div class="result__text">{{ resultInfo[1] }}</div>
    <div class="result__score">
      <p v-if="resultInfo[0]">친선전은 랭크포인트에 영향을 주지 않아요!</p>
      <p v-else-if="resultInfo[1] === '패배'">-2</p>
      <p v-else>+10</p>
    </div>
    <div class="result__images">
      <img
        v-for="(item, i) in myDeck"
        :key="i"
        :src="this.imageBaseUrl + '/' + item.monsterId + '.png'"
        alt=""
        class="result__dockcho"
      />
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  props: ['myDeck', 'resultInfo'],
  data() {
    return {
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  methods: {
    ...mapActions(['fetchEnemyInfo']),
    goToArenaMain() {
      this.$router.push({ path: '/game/arena' })
    },
    goToFriend() {
      this.$router.push({ path: '/game/friend' })
    }
  },
  mounted() {
    this.fetchEnemyInfo('')
    if (this.resultInfo[0]) {
      setTimeout(() => {
        this.goToFriend()
      }, 2000)
    } else {
      setTimeout(() => {
        this.goToArenaMain()
      }, 2000)
    }
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
  border-radius: 5px;
  height: 16vw;
  width: 16vw;
  margin: 0 1vw;
}
</style>
