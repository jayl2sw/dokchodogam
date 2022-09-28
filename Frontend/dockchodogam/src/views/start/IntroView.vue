<template>
  <div class="intro">
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
    >
      <div
        class="lastPageBlur"
        :class="flipbook.page === flipbook.numPages ? 'lastPage' : ''"
      ></div>
      <div class="progress">
        <div
          class="progress-bar"
          :style="
            'width:' +
            ((flipbook.page - 1) / (flipbook.numPages - 1)) * 100 +
            '%'
          "
        ></div>
      </div>
      <!-- <button @click="flipbook.flipLeft">Previous Page</button>
      <button @click="flipbook.flipRight">Next Page</button> -->
      <div
        class="intro__btn"
        :class="flipbook.page === flipbook.numPages ? 'lastPage' : ''"
      >
        <button class="goToMainBtn" @click="this.goToMain()">메인으로</button>
      </div>
    </flipbook>
  </div>
</template>

<script>
import Flipbook from 'flipbook-vue'
export default {
  components: {
    Flipbook
  },
  methods: {
    goToMain() {
      this.$router.push({ path: '/main' })
      this.isLastPage = true
    }
  }
}
</script>

<style scoped>
.intro {
  display: flex;
  justify-content: center;
  overflow: hidden;
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
}
.progress {
  width: 100%;
}
.flipbook {
  width: 90vw;
  height: 90vh;
}
.intro__btn {
  position: absolute;
  display: none;
  top: 45vh;
  left: 45vw;
}
.goToMainBtn {
  width: 10vw;
  height: 10vh;
}
.lastPage {
  display: block;
  z-index: 9999;
}
</style>
