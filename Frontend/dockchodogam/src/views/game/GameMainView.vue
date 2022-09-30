<template>
  <NavBar @overflow="overflow" />
  <div class="game">
    <div class="game__arena" @click="goToArena()"></div>
    <div class="game__gg" @click="geToGG()"></div>
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
    },
    geToGG() {
      this.$router.push({ path: '/game/gg' })
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
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  z-index: -1;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url('@/assets/dogam_background.jpg');
}
.game__arena {
  background-image: url('@/assets/arena/001.png');
  background-repeat: no-repeat;
  background-size: 100%;
  background-position: center;
  border-radius: 50px;
  width: 39.5vw;
  height: 50vh;
  margin: 3vh 2vw;
  cursor: pointer;
}
.game__arena:hover {
  animation: bigger 0.5s linear 0s 1 normal forwards;
}
.game__gg {
  background-image: url('@/assets/arena/002.png');
  background-repeat: no-repeat;
  background-size: 100%;
  background-position: center;
  border-radius: 50px;
  width: 39.5vw;
  height: 50vh;
  margin: 3vh 2vw;
  cursor: pointer;
}
.game__gg:hover {
  animation: bigger 0.5s linear 0s 1 normal forwards;
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
  .game__gg {
    width: 80vw;
    height: 38vh;
  }
}
@keyframes bigger {
  from {
    background-size: 100%;
  }
  to {
    background-size: 50vw;
  }
}
</style>
