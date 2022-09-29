<template>
  <div class="card">
    <img :src="result.imgUrl" alt="" class="plantImage" />
    <div class="plantName">
      {{ result.name }}
    </div>
  </div>
</template>

<script>
export default {
  props: ['result']
}
</script>

<style scoped>
.card {
  width: 20vw;
  height: 25vw;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  box-shadow: 5px 5px 10px;
  margin-bottom: 2vh;
  background-color: #a7c957;
}
.plantImage {
  width: 18vw;
  height: 18vw;
  margin: 1vw;
}
.plantName {
  text-align: center;
  font-size: 20px;
  margin-bottom: 2vw;
}
@media screen and (max-width: 768px) {
  .card {
    width: 30vw;
    height: 40vw;
  }
  .plantImage {
    width: 27vw;
    height: 27vw;
    margin: 1.5vw;
  }
  .plantName {
    font-size: 15px;
    margin-bottom: 3vw;
  }
}
</style>
