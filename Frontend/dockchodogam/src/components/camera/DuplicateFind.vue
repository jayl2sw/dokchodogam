<template>
  <div class="find">
    <h3>{{ plant.name }}를 이미 발견하셨어요!</h3>
    <p>최초 발견자 : {}</p>

    <div>
      <img />
      <button @click="goToEncyclopedia">도감에서 확인!</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    plant: Object
  },
  methods: {
    goToEncyclopedia() {
      this.$router.push({
        path: '/encyclopedia'
      })
    }
  }
}
</script>

<style scoped>
.find {
  border-style: groove;
  border-color: black;
  border-width: 5px;
  border-radius: 10px;
  width: 40vw;
  height: 70vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: auto;
}
</style>
