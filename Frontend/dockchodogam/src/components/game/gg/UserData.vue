<template>
  <div class="userData">
    <div class="profile">
      <div class="profileImage"></div>
      <div class="profileMiddle">
        <div class="nickname"></div>
        <div class="ranking"></div>
      </div>
      <div class="tier">
        <div class="tierImage"></div>
        <div class="tierBar"></div>
      </div>
    </div>
    <div class="myChoiceDokcho">
      <div class="myChoiceDokchoItem">
        <div class="itemImage"></div>
        <div class="itemName"></div>
        <div class="itemPer"></div>
      </div>
    </div>
    <div class="battleLog">
      <div class="battleLogItem">
        <div class="result"></div>
        <div class="decks">
          <div class="deck"></div>
          <div class="VS"></div>
          <div class="deck"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {}
</script>

<style scoped>
.userData {
  display: flex;
  flex-direction: column;
}
.profile {
  display: flex;
  height: 10vh;
  align-items: center;
  justify-content: center;
}
.profileImage {
  width: 9vh;
  height: 9vh;
  border-radius: 2vh;
}
.profileMiddle {
  width: 10vw;
  height: 10vh;
}
.nickname {
  font-size: 2vw;
}
.ranking {
  font-size: 1vw;
}
.tier {
  display: flex;
  align-items: center;
}
.tierImage {
  width: 6vh;
  height: 6vh;
}
.tierBar {
  width: 10vw;
}
.myChoiceDokcho {
  border: 2px groove black;
  border-radius: 10px;
  overflow-x: auto;
  height: 20vh;
}
.myChoiceDokchoItem {
  border: 2px groove black;
  border-radius: 10px;
  width: 10vh;
  height: 15vh;
  margin: 1vh;
  display: inline-flex;
  flex-direction: column;
  justify-content: space-between;
}
.itemName {
  font-size: 1vh;
}
.itemPer {
  font-size: 1vh;
}
.battleLog {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.battleLogItem {
  border: 2px groove black;
  border-radius: 10px;
  width: 80vw;
  height: 10vh;
  display: flex;
  justify-content: space-evenly;
}
.result {
  width: 10vw;
  height: 100%;
}
.decks {
  width: 70vw;
  height: 100%;
}
.deck {
  width: 32.5vw;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
.VS {
  width: 5vw;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
