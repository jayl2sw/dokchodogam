<template>
  <div class="dockCho">
    <div class="hpBar">
      <div class="progress">
        <div
          class="progress-bar"
          :style="'width:' + (data.currentHp / data.hp) * 100 + '%'"
          :aria-valuenow="data.currentHp"
          :class="
            this.data.type === 'JAPCHO'
              ? 'japcho'
              : this.data.type === 'DOKCHO'
              ? 'dokcho'
              : this.data.type === 'YAKCHO'
              ? 'yakcho'
              : 'hidden'
          "
          aria-valuemin="0"
          :aria-valuemax="data.hp"
        ></div>
      </div>
    </div>
    <div
      class="dokchoBox"
      :class="
        this.who === 'me' && this.sangseong === '상성 좋음'
          ? 'good'
          : this.who === 'you' && this.sangseong === '상성 나쁨'
          ? 'good'
          : ''
      "
    >
      <div class="damage">
        <p
          class="TITLE"
          ref="dmg"
          :class="
            this.data.type === 'JAPCHO'
              ? 'japchoDamage'
              : this.data.type === 'DOKCHO'
              ? 'dokchoDamage'
              : this.data.type === 'YAKCHO'
              ? 'yakchoDamage'
              : 'hiddenDamage'
          "
        >
          {{ damage }}
        </p>
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
  props: ['data', 'damage', 'sangseong', 'who'],
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
.japcho {
  background-color: #ffe140;
}
.dokcho {
  background-color: #ff5555;
}
.yakcho {
  background-color: #467302;
}
.hidden {
  background-color: #c493ff;
}
.japchoDamage {
  color: #ffe140;
}
.dokchoDamage {
  color: #ff5555;
}
.yakchoDamage {
  color: #467302;
}
.hiddenDamage {
  color: #c493ff;
}
@keyframes bombom {
  0% {
    transform: none;
  }
  50% {
    transform: scale(1.5);
  }
  100% {
    transform: none;
  }
}
.good > img {
  animation-name: bombom;
  animation-delay: 1s;
  animation-duration: 0.5s;
  animation-fill-mode: none;
  animation-iteration-count: 2;
}
</style>
