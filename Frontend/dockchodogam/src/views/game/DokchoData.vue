<template>
  <NavBar @overflow="overflow" />
  <div
    class="dokchoData"
    :class="this.showMenu ? 'open-menu' : ''"
    v-if="this.isMyDokcho"
  >
    <div class="profile">
      <img
        :src="this.imageBaseUrl + '/' + this.dokchoInfo.monsterId + '.png'"
        alt=""
        class="profileImage"
      />
      <div class="profileMiddle">
        <div class="nicknameBox">
          <div class="nickname TITLE">{{ this.dokchoInfo.name }}몬</div>
        </div>
        <div class="ranking TITLE">함께한 경기 수 : {{ this.allMatch }}</div>
      </div>
      <div class="chart">
        <vue3-chart-js v-bind="this.myDoughnutChart" ref="myGraph" />
      </div>
      <div class="chart">
        <vue3-chart-js v-bind="this.allDoughnutChart" ref="allGraph" />
      </div>
    </div>
    <div class="infoText TITLE">역대 전적</div>
    <div class="battleLog">
      <div
        class="battleLogItem"
        v-for="(item, i) in this.userDokchoBattleLog"
        :key="i"
        :class="
          item.success && item.attacker === this.myNickname
            ? 'blue'
            : item.success && item.attacker !== this.myNickname
            ? 'red'
            : !item.success && item.attacker === this.myNickname
            ? 'red'
            : 'blue'
        "
      >
        <div class="logNickname">
          <p class="TITLE">{{ item.attacker }}</p>
          <font-awesome-icon
            :class="item.success ? 'winner__icon' : 'dpNone'"
            icon="fa-solid fa-crown"
          />
        </div>
        <div class="decks">
          <div class="deck">
            <img
              class="deckItem"
              v-for="idx in this.array1"
              :key="idx"
              :src="this.imageBaseUrl + '/' + item[idx] + '.png'"
              alt=""
            />
          </div>
          <div class="VS TITLE">vs</div>
          <div class="deck">
            <img
              class="deckItem"
              v-for="idx in this.array2"
              :key="idx"
              :src="this.imageBaseUrl + '/' + item[idx] + '.png'"
              alt=""
            />
          </div>
        </div>
        <div class="logNickname">
          <p class="TITLE">{{ item.defender }}</p>
          <font-awesome-icon
            :class="item.success ? 'dpNone' : 'winner__icon'"
            icon="fa-solid fa-crown"
          />
        </div>
      </div>
      <InfiniteLoading
        @infinite="load"
        :slots="{
          complete: '더 이상 결과가 없어요..',
          error: '무언가 잘못되었어요!'
        }"
      />
      <div class="block"></div>
    </div>
  </div>
  <div v-else>
    <div class="dokchoData">
      <div class="undefined__body">
        <h3 class="undefined TITLE">내 도감에 들어 있지 않은</h3>
        <h3 class="undefined TITLE">풀깨비에요 👀</h3>
        <img src="@/assets/nomonster.png" />
      </div>
      <div class="undefined__footer">
        <button @click="goToEncyclopedia()" class="btn">
          <span>도감으로 돌아가기</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import NavBar from '@/components/main/NavBar.vue'
import InfiniteLoading from 'v3-infinite-loading'
import 'v3-infinite-loading/lib/style.css'
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'

export default {
  components: {
    NavBar,
    InfiniteLoading,
    Vue3ChartJs
  },
  data() {
    return {
      myNickname: JSON.parse(localStorage.getItem('userInfo')).nickname,
      showMenu: false,
      dokchoInfo: {},
      userDokchoBattleLog: [],
      dokchoBattleLogPage: 0,
      array1: ['monster0', 'monster1', 'monster2', 'monster3', 'monster4'],
      array2: ['monster5', 'monster6', 'monster7', 'monster8', 'monster9'],
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      queryData: this.$route.query.query,
      isMyDokcho: false,
      allMatch: 0,
      line_audio: '',
      myDoughnutChart: {
        type: 'doughnut',
        data: {
          labels: ['승리', '패배'],
          type: 'donut',
          datasets: [
            {
              backgroundColor: ['rgb(153, 204, 255)', 'rgb(255, 161, 161)'],
              borderColor: ['rgb(0, 0, 0)'],
              borderWidth: 4,
              data: [40, 20]
            }
          ]
        },
        options: {
          plugins: {
            title: {
              display: true,
              text: '내 승률'
            },
            subtitle: {
              display: true,
              text: 'a'
            }
          }
        }
      },
      allDoughnutChart: {
        type: 'doughnut',
        data: {
          labels: ['승리', '패배'],
          type: 'donut',
          datasets: [
            {
              backgroundColor: ['rgb(153, 204, 255)', 'rgb(255, 161, 161)'],
              data: [40, 20],
              borderColor: ['rgb(255, 255, 255)'],
              borderWidth: 5
            }
          ]
        },
        options: {
          plugins: {
            title: {
              display: true,
              text: '전체 승률'
            },
            subtitle: {
              display: true,
              text: 'a'
            }
          }
        }
      }
    }
  },
  methods: {
    overflow(value) {
      this.showMenu = value
    },
    load($state) {
      const option = {
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      }
      axios
        .get(
          BASE_URL +
            '/api/v1/gg/log/moster/' +
            this.queryData +
            '/' +
            this.dokchoBattleLogPage,
          option
        )
        .then((res) => {
          if (res.data.battleLog.length) {
            this.userDokchoBattleLog = this.userDokchoBattleLog.concat(
              res.data.battleLog
            )
            this.dokchoBattleLogPage += 1
            $state.loaded()
          } else {
            $state.complete()
          }
        })
        .catch((err) => {
          console.log(err)
          $state.error()
        })
    },
    goToEncyclopedia() {
      this.$router.push({
        path: '/encyclopedia'
      })
    }
  },
  created() {
    const option = {
      headers: {
        AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
      }
    }
    axios
      .get(BASE_URL + '/api/v1/game/monster/mylist', option)
      .then((res) => {
        console.log(res.data)
        res.data.forEach((element) => {
          if (this.queryData === String(element.monsterId)) {
            this.isMyDokcho = true
          }
        })
      })
      .catch((err) => console.log(err))
    axios
      .get(
        BASE_URL +
          '/api/v1/gg/log/moster/' +
          this.queryData +
          '/' +
          this.dokchoBattleLogPage,
        option
      )
      .then((res) => {
        console.log(res.data)
        this.dokchoInfo = res.data.monsterDto
        this.allDoughnutChart.data.datasets[0].data = [
          res.data.totalWinRate.winGame,
          res.data.totalWinRate.totalGame - res.data.totalWinRate.winGame
        ]
        this.myDoughnutChart.data.datasets[0].data = [
          res.data.winRate.winGame,
          res.data.winRate.totalGame - res.data.winRate.winGame
        ]
        this.myDoughnutChart.options.plugins.subtitle.text =
          Math.round(res.data.winRate.winRate * 1000) / 10 + '%'
        this.allDoughnutChart.options.plugins.subtitle.text =
          Math.round(res.data.totalWinRate.winRate * 1000) / 10 + '%'
        this.allMatch = res.data.winRate.totalGame
        this.$refs.myGraph.update(100)
        this.$refs.allGraph.update(100)
      })
  },
  mounted() {
    if (this.isMyDokcho) {
      this.line_audio = new Audio(
        process.env.VUE_APP_S3_URL + '/' + this.dokchoInfo.monsterId + '.m4a'
      )
      this.line_audio.play()
    }
  },
  beforeUnmount() {
    if (this.isMyDokcho) {
      this.line_audio.pause()
    }
  }
}
</script>

<style scoped>
.dokchoData {
  padding: 13vh 10vw 0;
  display: flex;
  flex-direction: column;
  position: absolute;
  top: 0;
  width: 100%;
  min-height: 100vh;
  z-index: -9999;
  padding-top: 13vh;
  background-image: url('@/assets/dogam_background.jpg');
}
.profile {
  display: flex;
  height: 30vh;
  align-items: center;
  justify-content: center;
  margin: 2vh 0;
}
.profileImage {
  width: 19vh;
  height: 19vh;
  border-radius: 2vh;
  background-color: #ececec;
}
.profileMiddle {
  width: 15vw;
  margin: 0 2vw;
}
.nicknameBox {
  display: flex;
  align-items: center;
}
.nickname {
  font-size: 4vh;
}
.ranking {
  font-size: 2vh;
}
.chart {
  width: 25vh;
  height: 25vh;
  margin: 0 2vw;
}
.infoText {
  font-size: 3vw;
  height: 5vw;
  margin: 8vh 3vh 3vh;
  text-align: center;
}
.battleLog {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.battleLogItem {
  border-radius: 15px;
  width: 75vw;
  height: 15vh;
  display: flex;
  justify-content: space-evenly;
  margin: 2vh 0;
}
.logNickname {
  display: flex;
  width: 7.5vw;
  height: 100%;
  justify-content: center;
  align-items: center;
  position: relative;
}
.logNickname > p {
  z-index: 999;
  margin-top: 3vh;
}
.winner__icon {
  position: absolute;
  width: 5vw;
  height: 5vw;
  color: #ffe140;
  transform: translateY(-5%);
}
.decks {
  width: 65vw;
  height: 100%;
  display: flex;
}
.deck {
  width: 27.5vw;
  height: 100%;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
.deckItem {
  border-radius: 1vw;
  width: 5vw;
  height: 5vw;
  background-color: #ececec;
}
.VS {
  width: 5vw;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.block {
  margin: 5vh;
}
.red {
  background-color: rgb(255, 161, 161, 0.3);
}
.blue {
  background-color: rgb(153, 204, 255, 0.3);
}
.dpNone {
  display: none;
}

.undefined__body {
  width: 100%;
  margin-top: 8vmin;
  /* height: 50%; */
  align-items: center;
}

.undefined {
  text-align: center;
  margin-bottom: 2vh;
}

.undefined__body img {
  display: block;
  margin: auto;
  /* object-fit: cover; */
  width: 25vmax;
}

.undefined__footer {
  display: flex;
  flex-direction: center;
}

.btn {
  margin: auto;
  margin-top: 5vmin;
  /* align-self: center; */
  min-width: 50px;
  /* width: 30%; */
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  color: black;
  text-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  /* margin: auto; */
  box-shadow: 0 0 10px #000;
  border-radius: 10px;
  background-color: #a7c957;
  background-image: #a7c957;
  transform: translateY(-50%);
}
.btn:hover {
  background-position: right center;
  background-color: #467302;
  color: white;
}

@media screen and (max-width: 850px) {
  .profile {
    height: auto;
    flex-direction: column;
  }
  .profileMiddle {
    width: 60vw;
    height: 10vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .infoText {
    font-size: 5vw;
  }
  .battleLogItem {
    width: 90vw;
  }
  .logNickname {
    width: 17.5vw;
  }
  .logNickname > p {
    font-size: 1.3vh;
  }
  .winner__icon {
    top: initial;
    width: 6vw;
    height: 6vw;
  }
  .VS {
    width: 10vw;
  }
  .decks {
    align-items: center;
  }
  .deck {
    width: 25vw;
    height: 9vh;
    align-items: center;
    display: flex;
    flex-wrap: wrap;
  }
  .deckItem {
    width: 8vw;
    height: 8vw;
  }
  .open-menu {
    overflow: hidden;
    width: 100%;
    height: 100%;
    position: fixed;
  }

  .undefined__body {
    width: 100%;
    height: 50%;
  }

  .undefined {
    text-align: center;
    margin-bottom: 5vh;
  }

  .undefined__body img {
    display: block;
    margin: auto;
    /* object-fit: cover; */
    width: 30vmax;
  }
}
</style>
