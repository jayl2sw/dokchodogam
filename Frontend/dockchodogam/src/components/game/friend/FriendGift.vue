<template>
  <div class="lists">
    <div class="left">
      <img src="@/assets/loading/1.png" alt="" />
      <p class="TITLE name">username</p>
    </div>
    <div class="right">
      <font-awesome-icon icon="fa-solid fa-ban" />
      <font-awesome-icon icon="fa-solid fa-heart" />
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
  margin: 2vh auto;
  background-color: white;
  display: flex;
  justify-content: space-between;
}
.left {
  display: flex;
  width: 70%;
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
  font-size: 1.5vw;
  font-weight: bold;
  line-height: 11vh;
  margin-left: 0.5vw;
  margin-bottom: 0;
}
.right {
  width: 30%;
}
svg {
  color: pink;
  width: 2vw;
  height: 100%;
  margin-right: 1vw;
  transition: 0.3s;
  float: right;
}
svg:hover {
  color: red;
}
</style>
