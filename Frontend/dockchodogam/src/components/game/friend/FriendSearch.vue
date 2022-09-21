<template>
  <div class="search">
    <div class="friend__search">
      <input type="search" v-model="inputData" @keyup.enter="search()" />
    </div>
    <div class="search__result">
      <div v-for="(user, i) in datas" :key="i" class="result__item"></div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      inputData: '',
      datas: [1, 2, 3, 1, 1, 1, 1, 1]
    }
  }
  // methods: {
  //   search() {}
  // }
}
</script>

<style scoped>
.search {
  height: 100%;
  display: flex;
  flex-direction: column;
}
.friend__search {
  height: 10vh;
  width: 70vw;
  margin: 5vh 5vw;
  display: flex;
  justify-content: center;
}
.search__result {
  height: 45vh;
  width: 70vw;
  margin: 0 5vw 5vh 5vw;
  overflow: auto;
}
.result__item {
  border: 2px groove black;
  border-radius: 5px;
  width: 100%;
  height: 10vh;
  margin: 2vh 0;
}
input {
  display: block;
  width: 50vw;
  margin: 8px 0 8px 0;
  padding: 10px 15px 10px 45px;
  background: white url('@/assets/search-icon.png') no-repeat 15px center;
  background-size: 15px 15px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px,
    rgba(0, 0, 0, 0.3) 0px 1px 3px -1px;
}
::-webkit-scrollbar {
  display: none;
}
</style>
