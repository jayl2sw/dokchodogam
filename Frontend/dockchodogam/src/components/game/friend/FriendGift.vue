<template>
  <div class="lists">
    <div class="left">
      <img src="@/assets/loading/1.png" alt="" />
      <p class="TITLE name">username</p>
    </div>
    <div class="right">
      <font-awesome-icon icon="fa-solid fa-heart" />
      <font-awesome-icon icon="fa-solid fa-ban" />
    </div>
  </div>
</template>

<script>
export default {}
</script>

<style scoped>
.lists {
  border: none;
  border-radius: 20px;
  width: 90%;
  height: 100%;
  margin: 2vh auto;
  background-color: white;
  display: flex;
  justify-content: space-between;
}
.left {
  display: flex;
}
.left > img {
  width: 8vh;
  height: 8vh;
  object-fit: contain;
  margin: 2vh;
  background-color: #eee;
  border-radius: 50%;
}
.name {
  font-size: 2.5vh;
  font-weight: bold;
  line-height: 11vh;
  margin-left: 0.5vw;
  margin-bottom: 0;
}
svg {
  color: pink;
  width: 1.5vw;
  height: 100%;
  margin-right: 1.5vw;
  transition: 0.3s;
}
svg:hover {
  color: red;
}
</style>
