<template>
  <div class="main">
    <div class="main__boxes">
      <div class="box__left">
        <div class="myDeck">
          <div class="nickname">
            <h3>username</h3>
          </div>
          <div class="expBar">
            <div class="progress">
              <div
                class="progress-bar"
                role="progressbar"
                style="width: 75%"
                aria-valuenow="75"
                aria-valuemin="0"
                aria-valuemax="100"
              ></div>
            </div>
          </div>
          <div class="myDeckDetail">
            <div class="myDeckPower">전투력:500</div>
            <div class="myDeckImg"></div>
          </div>
          <div class="myDeckBtn">
            <button @click="goToDeck()">내 덱 수정</button>
          </div>
        </div>
        <div class="ranking">
          <div class="rankingText">
            <h3>랭킹</h3>
          </div>
          <div class="rankingList"></div>
        </div>
      </div>
      <div class="box__right">
        <div class="goToHome">
          <button @click="goToGameMain()">홈으로 돌아가기</button>
        </div>
        <div class="users">
          <div class="info__box">
            <div
              class="game__info"
              data-bs-toggle="modal"
              data-bs-target="#exampleModal"
            >
              <font-awesome-icon icon="fa-solid fa-question" size="xl" />
            </div>
          </div>
          <div class="userList">상대들</div>
        </div>
        <div class="buttons">
          <button @click="goToGameShop()">상점</button>
          <button @click="goToFriend()">친구관리</button>
        </div>
      </div>
    </div>
    <!-- Modal -->
    <div
      class="modal fade"
      id="exampleModal"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">도움말</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">게임설명해줘요ㅎㅎ</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goToGameMain() {
      this.$router.push({ path: '/game' })
    },
    goToGameShop() {
      this.$router.push({ path: '/game/shop' })
    },
    goToFriend() {
      this.$router.push({ path: '/game/friend' })
    },
    goToDeck() {
      this.$router.push({ path: '/game/deck' })
    }
  }
}
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 9vh 0;
}
.main__boxes {
  display: flex;
  justify-content: space-around;
}
.box__left {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0 5vw;
}
.myDeck {
  border: 2px groove;
  border-radius: 5px;
  height: 39vh;
  width: 40vw;
  margin: 1vh 0;
  padding: 0 2vw;
}
.nickname {
  height: 15vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.myDeckDetail {
  height: 10vh;
  display: flex;
  justify-content: space-between;
  margin: 3vh 0;
}
.myDeckPower {
  display: flex;
  align-items: center;
  width: 10vw;
  height: 100%;
}
.myDeckImg {
  border: 2px groove;
  border-radius: 5px;
  display: flex;
  align-items: center;
  width: 35vw;
  height: 100%;
}
.myDeckBtn {
  display: flex;
  justify-content: flex-end;
}
.ranking {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border: 2px groove;
  border-radius: 5px;
  height: 39vh;
  width: 40vw;
  margin: 1vh 0;
  padding: 2vh 2vw;
}
.rankingText {
  text-align: center;
  height: 5vh;
}
.rankingList {
  border: 2px groove;
  border-radius: 5px;
  width: 30vw;
  height: 20vh;
}
.box__right {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0 5vw;
}
.goToHome {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 2.5vh;
  height: 5vh;
}
.users {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border: 2px groove;
  border-radius: 5px;
  height: 65vh;
  width: 40vw;
}
.userList {
  border: 2px groove;
  margin: 1vh 2.5vw;
  border-radius: 5px;
  height: 55vh;
  width: 35vw;
}
.buttons {
  display: flex;
  justify-content: space-evenly;
  margin-top: 2.5vh;
  height: 5vh;
}
.info__box {
  display: flex;
  justify-content: flex-end;
}
.game__info {
  margin: 2vh 2vw;
  width: 30px;
  height: 30px;
  border: 2px groove black;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
@media screen and (max-width: 850px) {
  .game__info {
    top: 10px;
    right: 10px;
    width: 25px;
    height: 25px;
  }
  svg {
    width: 10px;
  }
}
</style>
