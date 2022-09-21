<template>
  <div class="gacha">
    <div class="gacha__header">
      <h3>독초몬 뽑기</h3>
    </div>
    <div class="gacha__body">
      <img src="@/assets/tree.png" />
      <button>뽑기</button>
    </div>
  </div>
</template>

<script>
export default {}
</script>

<style scoped>
.gacha {
}
.gacha__header {
  text-align: center;
}

.gacha__body {
  display: flex;
  flex-direction: column;
}
</style>
