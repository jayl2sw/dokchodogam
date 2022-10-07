<template>
  <div class="loading">
    <img
      :src="require('@/assets/loading/' + this.random + '.png')"
      alt="loading__img"
      class="loading__image"
    />
    <div class="loading__text TITLE">{{ this.text[this.random - 1] }}</div>
  </div>
</template>

<script>
import _ from 'lodash'

export default {
  data() {
    return {
      random: _.random(1, 5),
      text: [
        '개나리가 식물의 자리를 찾는 중 입니다 ...',
        '코알라가 유칼립투스를 먹는 중 입니다 ...',
        '월계수가 올림픽을 위해 파리로 가는 중 입니다 ...',
        '경고 : 건강을 해치는 담배 그래도 피우시겠습니까?',
        '카네이션이 5월에 사라진 사돈과 팔촌을 찾는중 ...'
      ]
    }
  }
}
</script>

<style scoped>
.loading {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  background-color: #ececec;
}
.loading__image {
  animation: motion 0.5s linear 0s infinite alternate;
  width: 30vh;
  height: 30vh;
  margin: 10vh;
}
.loading__text {
  width: 100%;
  text-align: center;
  font-size: 20px;
}
@keyframes motion {
  from {
    transform: translate3d(0, 0, 0);
  }
  to {
    transform: translate3d(0, -15px, 0);
  }
}
</style>
