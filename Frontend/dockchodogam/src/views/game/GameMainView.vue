<template>
  <NavBar @overflow="overflow" />
  <div class="game">
    <div class="game__arena" @click="goToArena()">아레나</div>
    <div class="game__mandu">독초만두</div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'

export default {
  components: {
    NavBar
  },
  data() {
    return {
      showMenu: false,
      audio: new Audio(process.env.VUE_APP_S3_URL + '/game_main.mp3')
    }
  },
  methods: {
    overflow(value) {
      this.showMenu = value
    },
    goToArena() {
      var audio = new Audio(process.env.VUE_APP_S3_URL + '/button.mp3')
      audio.play()
      this.$router.push({ path: '/game/arena' })
    }
  },
  mounted() {
    this.audio.loop = true
    this.audio.volume = 0.5
    this.audio.play()
  },
  beforeUnmount() {
    this.audio.pause()
  }
}
</script>

<style scoped>
.game {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 85vh;
}
.game__arena {
  background-image: url('@/assets/arena/001.png');
  background-repeat: no-repeat;
  background-size: cover;
  border: 2px groove;
  border-radius: 5px;
  width: 39.5vw;
  height: 50vh;
  margin: 3vh 2vw;
  cursor: pointer;
}
.game__mandu {
  background-image: url('@/assets/arena/002.png');
  border: 2px groove;
  border-radius: 5px;
  width: 39.5vw;
  height: 50vh;
  margin: 3vh 2vw;
  cursor: pointer;
}
@media screen and (max-width: 850px) {
  .game {
    flex-direction: column;
    height: auto;
  }
  .game__arena {
    width: 80vw;
    height: 38vh;
  }
  .game__mandu {
    width: 80vw;
    height: 38vh;
  }
}
</style>
