<template>
  <div class="dockCho">
    <div class="hpBar">
      <div class="progress">
        <div
          class="progress-bar bg-danger"
          :style="'width:' + (data.currentHp / data.hp) * 100 + '%'"
          :aria-valuenow="data.currentHp"
          aria-valuemin="0"
          :aria-valuemax="data.hp"
        ></div>
      </div>
    </div>
    <div class="dokchoBox">
      <div class="damage">
        <p class="TITLE" ref="dmg">{{ damage }}</p>
      </div>
      <img
        v-if="this.data.monsterId"
        :src="this.imageBaseUrl + '/' + this.data.monsterId + '.png'"
        alt=""
        class="dockChoImage"
        :class="this.damage !== '' ? 'damaged' : ''"
      />
    </div>
  </div>
</template>

<script>
import _ from 'lodash'
export default {
  props: ['data', 'damage'],
  data() {
    return {
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  }
}
</script>

<style scoped>
.dockCho {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  height: 100%;
}
.dokchoBox {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  position: relative;
}
.progress {
  margin-bottom: 2vh;
}
.hpBar {
  width: 100%;
}
.damage {
  font-size: 4vw;
  position: absolute;
  z-index: 999;
  color: red;
  top: 0;
  left: 0;
}
.dockChoImage {
  border-radius: 5px;
  width: 100%;
  height: auto;
}
.damaged {
  -webkit-filter: grayscale(50%) brightness(0.9);
  filter: grayscale(50%) brightness(0.9);
}
</style>
