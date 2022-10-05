<template>
  <div class="find">
    <div class="find__header">
      <h3 class="TITLE">도감에 없는 식물을 찾았어요!</h3>
    </div>

    <div class="find__body">
      <p class="TITLE">" 스승님께 새로운 식물을 찾았다고 알려드릴게요 🍀 "</p>
      <img src="@/assets/5.png" alt="지현몬" />
    </div>

    <p class="notice">
      학습을 통해 새로운 풀깨비들이 도감에 추가 될 예정입니다.
    </p>
  </div>
</template>

<script>
export default {}
</script>

<style scoped>
.find {
  width: 100%;
  height: 100%;
  padding: 3vmin;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: auto;
}

.find__header {
  margin-top: 5vh;
  margin-bottom: 5vmin;
}

.find__body {
  height: 80%;
  display: flex;
  flex-direction: column;
}

.find__body img {
  width: 50%;
  margin: auto;
}

.notice {
  margin-top: 5vh;
  font-size: small;
  color: darkgray;
}
</style>
