<template>
  <div class="dockCho">
    <div class="hpBar">
      <div class="progress">
        <div
          class="progress-bar bg-danger"
          :style="'width:' + (data.currentHp / data.hp) * 100 + '%'"
          :aria-valuenow="data.currentHp"
          aria-valuemin="0"
          :aria-valuemax="data.hp"
        ></div>
      </div>
    </div>
    <div class="damage">
      <p>{{ damage }}</p>
    </div>
    <div class="dockChoImage"></div>
  </div>
</template>

<script>
export default {
  props: ['data', 'damage']
}
</script>

<style scoped>
.dockCho {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  height: 100%;
}
.hpBar {
  width: 100%;
}
.dockChoImage {
  border: 2px groove black;
  border-radius: 2px;
  width: 100%;
  height: auto;
}
</style>
