<template>
  <div class="intro">
    <flipbook
      class="flipbook"
      :pages="[
        null,
        'https://pds.joongang.co.kr//news/component/htmlphoto_mmdata/201712/21/d1a9665f-e292-4444-85ce-05fed763c516.jpg',
        'https://item-shopping.c.yimg.jp/i/z/niyantarose_sngl2108',
        'https://img.hankyung.com/photo/202008/AKR20200831108100005_01_i.jpg',
        'https://item-shopping.c.yimg.jp/i/z/seoul4_97712277340162106a',
        'https://image.aladin.co.kr/product/28009/41/cover500/scm7931752657512.jpg',
        'https://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/04/PS16041900068.jpg'
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
