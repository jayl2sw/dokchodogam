<template>
  <div class="lists"></div>
</template>

<script>
export default {
  props: ['friends']
}
</script>

<style scoped>
.lists {
  border: 2px groove black;
  border-radius: 5px;
  width: 100%;
  height: 10vh;
  margin: 2vh 0;
}
</style>
