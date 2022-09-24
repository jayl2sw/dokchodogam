<template>
  <div class="lists">
    <div class="left">
      <img src="@/assets/loading/1.png" alt="" />
      <p class="TITLE name">username</p>
    </div>
    <div class="right">
      <font-awesome-icon icon="fa-solid fa-gift" />
      <font-awesome-icon icon="fa-solid fa-hand-fist" />
    </div>
  </div>
</template>

<script>
export default {
  props: ['friends']
}
</script>

<style scoped>
.lists {
  background-color: white;
  border: none;
  border-radius: 20px;
  width: 70vw;
  height: 15vh;
  margin: 2vh 0;
  display: flex;
  justify-content: space-between;
}
.left {
  display: flex;
}
.left > img {
  width: 11vh;
  height: 11vh;
  object-fit: contain;
  margin: 2vh;
  background-color: #eee;
  border-radius: 50%;
}
.name {
  font-size: 4vh;
  font-weight: bold;
  line-height: 14vh;
  margin-left: 1vw;
}
svg {
  color: #467302;
  width: 3vw;
  height: 14vh;
  margin-right: 3vw;
  transition: 0.3s;
}
svg:hover {
  color: #a7c957;
}
</style>
