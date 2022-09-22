<template>
  <LoadingPage v-if="this.isLoading" />
  <div class="main" v-show="!this.isLoading">
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
                :style="'width: ' + (this.userInfo.rank_point % 100) + '%'"
                :aria-valuenow="this.userInfo.rank_point % 100"
                aria-valuemin="0"
                aria-valuemax="100"
              >
                {{ this.userInfo.rank_point % 100 }}
              </div>
            </div>
          </div>
          <div class="myDeckDetail">
            <div class="myDeckPower">전투력:500</div>
            <div class="myDeckImages">
              <div v-for="(item, i) in this.myDeck" :key="i">
                <img src="@/assets/loading/1.png" alt="" class="myDeckImage" />
              </div>
            </div>
          </div>
          <div class="myDeckBtnBox">
            <div class="myDeckBtn" @click="goToDeck()">내 덱 수정</div>
          </div>
        </div>
        <div class="ranking">
          <div class="rankingText">
            <p>랭킹</p>
          </div>
          <div class="rankingList">
            <div
              class="rankingListItem"
              v-for="(item, i) in this.ranking.opponents"
              :key="i"
            >
              <div class="rankIntImage">{{ i + 1 }}</div>
              <div class="rankerName">{{ item.nickname }}</div>
              <div class="rankerDeck">
                <div v-for="(dokcho, j) in this.ranking.yourDecks[i]" :key="j">
                  <img
                    src="@/assets/loading/2.png"
                    alt=""
                    class="rankerDeckItem"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box__right">
        <div class="goToHome">
          <div class="goToHomeBtn" @click="goToGameMain()">홈으로 돌아가기</div>
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
          <div class="userList">
            <div
              v-for="(enemy, i) in this.Enemys.userInfo"
              :key="i"
              class="userListItem"
            >
              <div class="enemyInfo">
                <div class="enemyName">{{ enemy.nickname }}</div>
              </div>
              <div class="enemyDeck">
                <div v-for="(item, j) in this.Enemys.deck[i]" :key="j">
                  <img
                    src="@/assets/loading/3.png"
                    alt=""
                    class="enemyDeck__item"
                  />
                </div>
              </div>
              <div class="gameStartBtn" @click="onClickGameStart(i)">start</div>
            </div>
          </div>
        </div>
        <div class="buttons">
          <div class="button" @click="goToGameShop()">상점</div>
          <div class="button" @click="goToFriend()">친구관리</div>
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
import LoadingPage from '@/components/main/LoadingPage.vue'
import { mapActions, mapGetters } from 'vuex'
import axios from 'axios'

export default {
  components: {
    LoadingPage
  },
  data() {
    return {
      Enemys: { userInfo: [], deck: [] },
      myDeck: [],
      ranking: [],
      isLoading: true,
      userInfo: {}
    }
  },
  methods: {
    ...mapActions(['fetchEnemyInfo', 'fetchUserDeck']),
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
    },
    getMyEnemy() {
      axios
        .get('http://localhost:8081/api/v1/game/deck/opponentInfo', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          console.log(res.data)
          this.Enemys.userInfo = res.data.opponents
          this.Enemys.deck = res.data.yourDecks
        })
        .catch((err) => console.log(err))
    },
    getRanking() {
      axios
        .get('http://localhost:8081/api/v1/game/deck/opponentInfo', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          console.log('랭커', res.data)
          this.ranking = res.data
        })
        .catch((err) => console.log(err))
    },
    onClickGameStart(i) {
      const info = {
        isChinsun: false,
        nickname: this.Enemys.userInfo[i].nickname,
        enemyDeck: this.Enemys.deck[i]
      }
      this.fetchEnemyInfo(info)
      setTimeout(() => {
        this.$router.push({ path: '/game/arena/ingame' })
      }, 200)
    }
  },
  created() {
    this.getMyEnemy()
    this.fetchUserDeck()
    this.getRanking()
    this.userInfo = JSON.parse(localStorage.getItem('userInfo'))
    setTimeout(() => {
      this.isLoading = false
    }, 1500)
  },
  computed: {
    ...mapGetters(['userDeck'])
  },
  watch: {
    userDeck() {
      this.myDeck = this.userDeck
      console.log('와치', this.myDeck)
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
  margin: 0 2.5vw;
}
.myDeck {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  border-radius: 10px;
  height: 39vh;
  width: 40vw;
  margin: 1vh 0;
  padding: 0 2vw;
  background-color: #ececec;
}
.nickname {
  height: 13vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.myDeckDetail {
  height: 10vh;
  display: flex;
  justify-content: space-between;
  margin: 2vh 0;
}
.progress {
  background-color: white;
}
.progress-bar {
  background-color: #a7c957;
}
.myDeckPower {
  display: flex;
  align-items: center;
  font-size: 2vh;
  width: 10vw;
  height: 100%;
}
.myDeckImages {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  width: 35vw;
  height: 100%;
}
.myDeckImage {
  border-radius: 5px;
  width: 6vw;
  height: 6vw;
}
.myDeckBtnBox {
  display: flex;
  justify-content: flex-end;
}
.myDeckBtn {
  border-radius: 2.5vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 3vh;
  width: 15vw;
  height: 5vh;
  background-color: #a7c957;
  cursor: pointer;
}
.ranking {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  height: 39vh;
  width: 40vw;
  margin: 1vh 0;
  padding: 2vh 2vw;
  background-color: #ececec;
}
.rankingText {
  text-align: center;
  font-size: 3vh;
  height: 5vh;
}
.rankingList {
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
  width: 35vw;
  height: 28vh;
  background-color: white;
}
.rankingListItem {
  border: 2px groove;
  border-radius: 5px;
  width: 33vw;
  height: 8vh;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
.rankIntImage {
  width: 3vw;
  height: 3vw;
}
.rankerName {
  width: 5vw;
  height: 6vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.5vw;
}
.rankerDeck {
  width: 23vw;
  display: flex;
  justify-content: space-evenly;
}
.rankerDeckItem {
  width: 4vw;
  height: 4vw;
  border-radius: 5px;
}
.box__right {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0 2.5vw;
}
.goToHome {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 2.5vh;
  height: 5vh;
}
.goToHomeBtn {
  border-radius: 2.5vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 3vh;
  width: 20vw;
  height: 5vh;
  background-color: #a7c957;
  cursor: pointer;
}
.users {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-radius: 10px;
  height: 65vh;
  width: 50vw;
  background-color: #467302;
}
.userList {
  margin: 2vh 2.5vw;
  height: 55vh;
  width: 45vw;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.userListItem {
  border-radius: 15px;
  box-shadow: 5px 5px 10px;
  height: 13vh;
  width: 40vw;
  margin: 1vh 2vw;
  display: flex;
  justify-content: space-around;
  align-items: center;
  background-color: white;
}
.enemyInfo {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 5vw;
}
.enemyName {
  font-size: 1.5vw;
}
.enemyDeck {
  width: 28vw;
  height: 10vh;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
.enemyDeck__item {
  border-radius: 5px;
  width: 4.5vw;
  height: 4.5vw;
}
.gameStartBtn {
  border-radius: 2.5vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2vh;
  width: 5vw;
  height: 5vh;
  background-color: #ffe140;
  margin-right: 0.5vw;
  cursor: pointer;
}
.buttons {
  display: flex;
  justify-content: space-evenly;
  margin-top: 2.5vh;
  height: 5vh;
}
.button {
  border-radius: 2.5vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 3vh;
  width: 15vw;
  height: 5vh;
  background-color: #a7c957;
  cursor: pointer;
}
.info__box {
  display: flex;
  justify-content: flex-end;
}
.game__info {
  margin: 2vh 2vw;
  width: 25px;
  height: 25px;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  background-color: #ececec;
}
@media screen and (max-width: 850px) {
  .game__info {
    top: 10px;
    right: 10px;
    width: 20px;
    height: 20px;
  }
  svg {
    width: 10px;
  }
}
</style>
