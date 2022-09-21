<template>
  <div class="friendRequests">
    <div class="lists" v-for="(data, i) in this.datas" :key="i"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      datas: [1, 1, 1, 1, 1, 1, 1, 1]
    }
  }
}
</script>

<style>
.friendRequests {
  height: 100%;
  overflow: auto;
}
.lists {
  border: 2px groove black;
  border-radius: 5px;
  width: 100%;
  height: 10vh;
  margin: 2vh 0;
}
::-webkit-scrollbar {
  display: none;
}
</style>
