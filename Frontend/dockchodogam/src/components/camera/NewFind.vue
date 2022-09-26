<template>
  <div class="find">
    <p class="find__title TITLE">찾았다, {{ plant.name }}!</p>
    <p class="find_finder">최초 발견자 : {{}}</p>
    <div>
      <div class="find__img" v-if="this.pick == false">
        <img class="monster__silhouette" src="@/assets/loading/5.png" />
        <div>
          <button @click="goToEncyclopedia" class="TITLE">
            도감에서 확인하기!
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    plant: Object
  },
  data() {
    return {
      pick: false
    }
  },
  methods: {
    checkPick() {
      return this.pick === !!this.pick
    },
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
  /* border-style: groove; */
  /* border-color: black; */
  /* border-width: 5px; */
  border-radius: 10px;
  width: 40vw;
  /* height: 70vh; */
  display: flex;
  flex-direction: column;
  align-items: center;
  /* overflow: auto; */
}
.find__title {
  font-size: 3vw;
  font-weight: bold;
}
.find_finder {
  width: 100%;
  text-align: right;
  color: #a7c957;
}
.find__img {
  display: flex;
  flex-direction: column;
}
.monster__silhouette {
  width: 50vh;
  filter: grayscale(100%);
}

button {
  background-color: #a7c957;
  width: 12vw;
  height: 6vh;
  border-radius: 10px;
  border: none;
  opacity: 0.8;
  transition: 0.2s;
  margin: 5vh auto 0;
}
button:hover {
  font-weight: bold;
  opacity: 1;
}
@media screen and (max-width: 850px) {
  .find {
    width: 100%;
    height: auto;
    margin-bottom: 8vh;
    position: relative;
  }
  .find_finder {
    position: absolute;
    bottom: -9vh;
    text-align: left;
    left: 5vw;
    font-size: 0.8rem;
  }
  .find__title {
    font-size: 8vw;
    font-weight: bold;
    word-break: keep-all;
  }
  .find__img > img {
    width: 60vw;
  }
  button {
    width: 50vw;
    opacity: 1;
  }
}
</style>
