<template>
  <div class="page404__padding">
    <div class="page404">
      <div class="page404__top">
        <div class="page404__text">
          <h1 class="TITLE">404</h1>
          <h3 class="TITLE">길을 잘못 드셨습니다.</h3>
        </div>
        <img src="@/assets/404Image.png" alt="" class="page404__image" />
      </div>
      <div class="page404__bottom">
        <div class="backBtn TITLE" @click="goToBack()">뒤로가기</div>
        <div class="mainBtn TITLE" @click="goToMain()">
          메인 페이지로 돌아가기
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goToBack() {
      this.$router.go(-1)
    },
    goToMain() {
      this.$router.push({ path: '/main' })
    }
  }
}
</script>

<style scoped>
.page404__padding {
  width: 100vw;
  height: 100vh;
  background-color: #467302;
}
.page404 {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100vh;
  width: 90vw;
  margin: 0 5vw;
  background-color: white;
}
.page404__top {
  display: flex;
  justify-content: center;
  height: 60vh;
}
.page404__text {
  margin: 0 5vw;
}
.page404__text > h1 {
  font-size: 150px;
}
.page404__text > h3 {
  font-size: 50px;
}
.page404__image {
  width: 50vh;
  height: 50vh;
}
.page404__bottom {
  display: flex;
  padding: 0 10vw;
  justify-content: space-evenly;
}
.backBtn {
  border-radius: 2.5vh;
  width: 20vw;
  height: 5vh;
  background-color: #ececec;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.mainBtn {
  border-radius: 2.5vh;
  width: 20vw;
  height: 5vh;
  background-color: #a7c957;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
</style>
