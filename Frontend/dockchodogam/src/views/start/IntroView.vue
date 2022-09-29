<template>
  <IntroCard v-if="this.introCard" />
  <div class="intro" v-else>
    <flipbook
      class="flipbook"
      :pages="[
        null,
        require('@/assets/intro/001.png'),
        require('@/assets/intro/002.png'),
        require('@/assets/intro/003.png'),
        require('@/assets/intro/004.png'),
        require('@/assets/intro/005.png'),
        require('@/assets/intro/006.png'),
        require('@/assets/intro/007.png'),
        require('@/assets/intro/008.png'),
        require('@/assets/intro/009.png'),
        require('@/assets/intro/010.png'),
        require('@/assets/intro/011.png'),
        require('@/assets/intro/012.png')
      ]"
      :zooms="null"
      :flipDuration="2000"
      v-slot="flipbook"
      :startPage="1"
      :dragToFlip="false"
    >
      <div
        class="lastPageBlur"
        :class="flipbook.page === flipbook.numPages ? 'lastPage' : ''"
      ></div>
      <div
        @click="flipbook.flipRight"
        class="nextPage"
        v-if="flipbook.page !== flipbook.numPages"
      >
        <img src="@/assets/arrow.png" alt="" class="nextImage" />
      </div>
      <div
        class="intro__btn"
        :class="flipbook.page === flipbook.numPages ? 'lastPage' : ''"
      >
        <div
          class="goToIntroCard TITLE"
          @click="this.geToIntroCard()"
          v-if="this.isNewbie"
        >
          모험을 떠나기
        </div>
        <div class="goToMainBtn TITLE" @click="this.goToMain()" v-else>
          모험을 떠나기
        </div>
      </div>
    </flipbook>
  </div>
</template>

<script>
import Flipbook from 'flipbook-vue'
import IntroCard from '@/components/main/IntroCard.vue'

export default {
  components: {
    Flipbook,
    IntroCard
  },
  data() {
    return {
      isNewbie: true,
      introCard: false,
      audio: new Audio(process.env.VUE_APP_S3_URL + '/intro.mp3')
    }
  },
  methods: {
    goToMain() {
      this.$router.push({ path: '/main' })
    },
    geToIntroCard() {
      this.introCard = true
    }
  },
  mounted() {
    this.isNewbie = JSON.parse(localStorage.getItem('userInfo')).newbie
    this.audio.loop = true
    this.audio.play()
  },
  beforeUnmount() {
    this.audio.pause()
  }
}
</script>

<style scoped>
.intro {
  display: flex;
  justify-content: center;
  overflow: hidden;
  padding: 5vh;
  background-image: url('@/assets/hanji.jpeg') no-repeat cover;
}
.lastPageBlur {
  background-color: rgba(0, 0, 0, 0.4);
  opacity: 0;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 9998;
  display: none;
  display: block;
  z-index: 9998;
}
.progress {
  width: 100%;
}
.flipbook {
  width: 90vw;
  height: 90vh;
  position: relative;
}
.nextPage {
  position: absolute;
  bottom: 40vh;
  right: 0;
  z-index: 9999;
  cursor: pointer;
}
.nextImage {
  width: 5vw;
  height: 10vh;
}
.intro__btn {
  position: absolute;
  display: none;
  bottom: 20vh;
  left: 35vw;
  cursor: pointer;
  font-size: 3vw;
}
.goToMainBtn {
  width: 20vw;
  text-align: center;
}
.goToIntroCard {
  width: 20vw;
  text-align: center;
}
.lastPage {
  display: block;
  z-index: 9999;
}
</style>
