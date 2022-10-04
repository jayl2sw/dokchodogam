<template>
  <LoadingPage v-if="this.isLoading" />
  <div class="main" v-show="!this.isLoading">
    <div id="warning-message">
      <p class="TITLE">
        아레나는<br /><br /><span class="emphasize">"가로 화면 전용"</span>
        게임입니다.
      </p>
    </div>
    <div class="main__boxes">
      <div class="box__left">
        <div class="myDeck">
          <div class="nickname">
            <h1 class="TITLE" @click="goToGG()">
              {{ this.userInfo.nickname }}
            </h1>
          </div>
          <div class="expBar">
            <img
              :src="
                this.imageBaseUrl +
                '/tier' +
                Math.min(
                  (this.userInfo.rank_point -
                    (this.userInfo.rank_point % 100)) /
                    100 +
                    1,
                  5
                ) +
                '.png'
              "
              alt="tier"
              class="tier"
            />
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
            <div class="myDeckPower">
              <p>전투력</p>
              <h1 class="TITLE">{{ this.tot }}</h1>
            </div>
            <div class="myDeckImages">
              <div v-for="(item, i) in this.myDeck" :key="i">
                <img
                  :src="this.imageBaseUrl + '/' + item.monsterId + '.png'"
                  alt=""
                  class="myDeckImage"
                />
              </div>
            </div>
          </div>
          <div class="myDeckBtnBox">
            <div class="myDeckBtn TITLE" @click="goToDeck()">나의 덱 수정</div>
          </div>
        </div>
        <div class="ranking">
          <div class="rankingText">
            <p class="TITLE">랭킹</p>
          </div>
          <div class="rankingList">
            <div
              class="rankingListItem"
              v-for="(item, i) in this.ranking"
              :key="i"
            >
              <div>
                <img
                  class="rankIntImage"
                  :src="require('@/assets/medal' + (i + 1) + '.png')"
                  alt=""
                />
              </div>
              <div class="rankerName TITLE">
                {{ item.nickname }}
              </div>
              <div class="rankerDeck">
                <div v-for="(dokcho, j) in item.deck" :key="j">
                  <img
                    :src="this.imageBaseUrl + '/' + dokcho + '.png'"
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
          <div class="goToHomeBtn TITLE" @click="goToGameMain()">
            홈으로 돌아가기
          </div>
        </div>
        <div class="users">
          <font-awesome-icon
            class="game__info"
            data-bs-toggle="modal"
            data-bs-target="#exampleModal"
            @click="sound()"
            icon="fa-solid fa-circle-info"
          />
          <div class="userList">
            <div
              v-for="(enemy, i) in this.Enemys.userInfo"
              :key="i"
              class="userListItem"
            >
              <div class="enemyInfo">
                <div class="enemyName TITLE">
                  {{ enemy.nickname }}
                </div>
              </div>
              <div class="enemyDeck">
                <div v-for="(item, j) in this.Enemys.deck[i]" :key="j">
                  <img
                    :src="this.imageBaseUrl + '/' + item.monsterId + '.png'"
                    alt=""
                    class="enemyDeck__item"
                  />
                </div>
              </div>
              <div class="gameStartBtn TITLE" @click="onClickGameStart(i)">
                START
              </div>
            </div>
          </div>
        </div>
        <div class="buttons">
          <div class="button TITLE" @click="goToGameShop()">상점</div>
          <div class="button TITLE" @click="goToFriend()">친구 관리</div>
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
            <h5 class="modal-title TITLE" id="exampleModalLabel">게임 설명</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <p class="TITLE danger">
              뒤로가기를 누르면 패배로 기록되니 주의 또 주의!
            </p>
            <p class="TITLE">
              아레나에서는 여러분과 비슷한 점수의 사람들과 배틀을 할 수 있어요!
            </p>
            <p class="TITLE">
              대전 상대를 골라 배틀을 시작하면<br />상대의 첫 번째 풀깨비와 나의
              첫 번째 풀깨비가 서로를 공격해요.<br /><span class="emphasize"
                >데미지는 각 풀깨비의 최소 데미지와 최대 데미지 사이의
                랜덤값</span
              >으로, 매번 달라진답니다.<br />상대의 풀깨비의 체력이 먼저 닳게
              되면 그 다음 순서로 자동으로 넘어가요.<br />나의 풀깨비의 체력이
              모두 닳으면 다음으로 상대를 맞설 풀깨비을 덱에서 골라야 해요.
            </p>
            <p class="TITLE">
              배틀 도중 <span class="emphasize">딱 한 번 스킬을 발동</span>시킬
              수 있어요.<br />오른쪽 아래의 버튼을 눌러 스킬을 사용해봐요!
              스킬은 게임이 시작될 때 세 가지 중 랜덤으로 하나를 준답니다.<br />"승리의
              불꽃"은 딱 한 번 나의 풀깨비를 두 배로 강하게 만들어줘요!<br />"무적의
              방패"는 딱 한 번 상대의 공격을 무효화시켜요!<br />"소망의 거울"은
              딱 한 번 상대의 공격을 그대로 상대에게 반사시켜줘요! 대신, 나의
              풀깨비는 상대를 공격할 수 없답니다.
            </p>
            <p class="TITLE">
              풀깨비끼리는 상성이 있는데,
              <span class="emphasize">서로가 붙었을 때 공격 버프</span>를 받을
              수 있어요.<br />잡초몬과 독초몬이 만나면 독초몬이 20% 강해져요!<br />독초몬과
              약초몬이 만나면 약초몬이 20% 강해져요!<br />약초몬과 잡초몬이
              만나면 잡초몬이 20% 강해져요!
            </p>
            <p class="TITLE">
              아레나는 상대가 불리한 규칙으로 진행되기 때문에
              <span class="emphasize"
                >만약 무승부로 게임이 끝난다면 이는 상대의 승리</span
              >랍니다.
              <span class="emphasize"
                >승리하면 10점을 얻고, 진다면 5점을 잃으니</span
              >
              조심하세요!
            </p>
            <p class="TITLE">그럼 즐거운 아레나를 시작해볼까요?</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LoadingPage from '@/components/main/LoadingPage.vue'
import { mapActions, mapGetters } from 'vuex'
import { BASE_URL } from '@/constant/BASE_URL'
import axios from 'axios'

export default {
  components: {
    LoadingPage
  },
  data() {
    return {
      Enemys: { userInfo: [], deck: [] },
      myDeck: [],
      tot: 0,
      ranking: [],
      isLoading: true,
      userInfo: JSON.parse(localStorage.getItem('userInfo')),
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      audio: new Audio(process.env.VUE_APP_S3_URL + '/arena_main.mp3'),
      btn_audio: new Audio(process.env.VUE_APP_S3_URL + '/button.mp3'),
      btn2_audio: new Audio(process.env.VUE_APP_S3_URL + '/button2.mp3')
    }
  },
  methods: {
    ...mapActions(['fetchEnemyInfo', 'fetchUserDeck', 'fetchnowUserInfo']),
    goToGameMain() {
      this.btn_audio.play()
      this.$router.push({ path: '/game' })
    },
    goToGameShop() {
      this.btn_audio.play()
      this.$router.push({ path: '/game/shop' })
    },
    goToFriend() {
      this.btn_audio.play()
      this.$router.push({ path: '/game/friend' })
    },
    goToDeck() {
      this.btn_audio.play()
      this.$router.push({ path: '/game/deck' })
    },
    goToGG() {
      this.btn_audio.play()
      this.$router.push({ path: '/game/gg' })
    },
    getMyEnemy() {
      axios
        .get(BASE_URL + '/api/v1/game/deck/opponentInfo', {
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
        .get(BASE_URL + '/api/v1/game/ranking', {
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
      this.btn_audio.play()
      const info = {
        isChinsun: false,
        nickname: this.Enemys.userInfo[i].nickname,
        enemyDeck: this.Enemys.deck[i]
      }
      this.fetchEnemyInfo(info)
      console.log('상대 덱 저장?', info)
      setTimeout(() => {
        this.$router.push({ path: '/game/arena/ingame' })
      }, 200)
    },
    sound() {
      this.btn2_audio.play()
    }
  },
  created() {
    this.getMyEnemy()
    this.fetchnowUserInfo()
    this.fetchUserDeck()
    this.getRanking()
    setTimeout(() => {
      this.isLoading = false
      this.userInfo = JSON.parse(localStorage.getItem('userInfo'))
      if (this.enemyInfo.nickname) {
        this.fetchEnemyInfo('')
      }
    }, 1500)
  },
  mounted() {
    this.audio.loop = true
    this.audio.volume = 0.5
    this.audio.play()
    console.log('tier : ' + this.mytier)
  },
  beforeUnmount() {
    this.audio.pause()
  },
  computed: {
    ...mapGetters(['userDeck', 'enemyInfo'])
  },
  watch: {
    userDeck() {
      this.myDeck = this.userDeck
      console.log('와치', this.myDeck)
      for (let index = 0; index < 5; index++) {
        this.tot +=
          (this.myDeck[index].maxAttack + this.myDeck[index].minAttack) / 2
      }
      console.log(this.tot)
    }
  }
}
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 9vh 0;
  background-image: url('@/assets/game_background.png');
  background-repeat: no-repeat;
  background-size: cover;
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
  border-radius: 20px;
  height: 39vh;
  width: 40vw;
  margin: 1vh 0;
  padding: 2vh 2vw;
  background-color: #ececec;
}
.nickname {
  height: 10vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.nickname > h1 {
  font-size: 5vh;
}
.nickname > h1:hover {
  color: #467302;
}
.myDeckDetail {
  height: 13vh;
  display: flex;
  justify-content: space-between;
  margin: 2vh 0;
  padding: 0 1vw;
  background-color: rgba(255, 255, 255, 0.3);
  border-radius: 40px;
}
.expBar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 2vh;
}
.tier {
  width: 5vw;
  margin-left: 2vh;
}
.progress {
  background-color: white;
  width: 28vw;
}
.progress-bar {
  background-color: #a7c957;
}
.myDeckPower {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-size: 2vh;
  width: 10vw;
  height: 100%;
}
.myDeckPower > p {
  font-size: 2vh;
  margin: 0;
}
.myDeckPower > h1 {
  font-size: 3vh;
  margin: 0;
}
.myDeckImages {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  width: 35vw;
  height: 100%;
}
.myDeckImage {
  width: 5vw;
  height: 5vw;
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
  font-size: 1.2vw;
  width: 10vw;
  height: 5vh;
  background-color: #a7c957;
  cursor: pointer;
  transition: 0.3s;
}
.myDeckBtn:hover {
  background-color: #467302;
  color: white;
}
.ranking {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 20px;
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
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
  width: 35vw;
  height: 28vh;
}
.rankingListItem {
  border-radius: 15px;
  width: 35vw;
  height: 8vh;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  background-color: rgba(255, 255, 255, 0.3);
  padding: 1vh 1vw;
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
  font-size: 1.1vw;
}

.rankerDeck {
  width: 23vw;
  display: flex;
  justify-content: space-evenly;
}
.rankerDeckItem {
  width: 3vw;
  height: 3vw;
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
  font-size: 1.5vw;
  width: 13vw;
  height: 6vh;
  background-color: white;
  cursor: pointer;
  transition: 0.3s;
}
.goToHomeBtn:hover {
  background-color: #467302;
  color: white;
}
.users {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-radius: 20px;
  height: 55vh;
  width: 50vw;
  background-color: #467302;
  position: relative;
}
.userList {
  margin: 2vh 2.5vw;
  height: 100%;
  width: 45vw;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.userListItem {
  border-radius: 30px;
  /* box-shadow: 5px 5px 10px; */
  height: 12vh;
  width: 100%;
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
  width: 7vw;
}
.enemyName {
  font-size: 1.2vw;
  margin-left: 1vw;
}

.enemyDeck {
  width: 30vw;
  height: 10vh;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
.enemyDeck__item {
  width: 4.5vw;
  height: 4.5vw;
}
.gameStartBtn {
  border-radius: 2.5vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.3vh;
  width: 5vw;
  height: 5vh;
  background-color: #ccc;
  margin-right: 0.5vw;
  cursor: pointer;
  transition: 0.3s;
}
.gameStartBtn:hover {
  background-color: #ffe140;
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
  font-size: 2.5vh;
  width: 10vw;
  height: 6vh;
  background-color: white;
  cursor: pointer;
  transition: 0.3s;
}
.button:hover {
  background-color: #467302;
  color: white;
}
.game__info {
  width: 3vh;
  height: 3vh;
  cursor: pointer;
  color: white;
  position: absolute;
  top: 1vh;
  right: 1vw;
  transition: 0.3s;
}
.game__info:hover {
  color: #ffe140;
}
.modal-dialog {
  min-width: 90vw;
  margin: 5vh auto;
}
.modal-content {
  width: 50vw;
  max-height: 80vh;
  border-radius: 5vh;
  border: none;
  margin: 0 auto;
}
.modal-header {
  padding: 2vw 2vw;
  border: none;
}
.modal-title {
  font-size: 4vh;
}
.modal-body {
  font-size: 1.2vw;
  line-height: 2.5vw;
  padding: 2vh 2vw;
  overflow: scroll;
}
.modal-body::-webkit-scrollbar {
  display: none;
}
.emphasize {
  font-family: 'UhBeeSe_hyun';
  color: #467302;
  font-size: 1.5vw;
}
.danger {
  color: red;
  font-size: 1.7vw;
  margin-bottom: 2vw;
  font-weight: bold;
}
/* @media screen and (max-width: 850px) {
  .nickname > h1 {
    font-size: 1vw;
  }
  .game__info {
    top: 10px;
    right: 10px;
    width: 20px;
    height: 20px;
  }
  svg {
    width: 10px;
  }
  .modal-content {
    min-width: 50vw;
    max-height: 80vh;
    border-radius: 20px;
    border: none;
    overflow: scroll;
  }
} */
@media only screen and (orientation: portrait) {
  .main {
    background-image: none;
    background-color: white;
    height: 100vh;
  }
  .main__boxes {
    display: none;
  }
  #warning-message {
    display: block;
    font-size: 5vw;
    text-align: center;
  }
  .emphasize {
    font-family: 'UhBeeSe_hyun';
    font-size: 6vw;
    font-weight: bold;
    color: #467302;
  }
  .danger {
    font-size: 7vw;
    font-weight: bold;
    color: red;
    margin-bottom: 1vw;
  }
}
@media only screen and (orientation: landscape) {
  #warning-message {
    display: none;
  }
  .enemyInfo {
    width: 9vw;
  }
  .enemyDeck {
    width: 28vw;
  }
  .gameStartBtn {
    width: 7vw;
    font-size: 1vh;
  }
}
</style>
