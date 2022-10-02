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
        <div class="logNickname">{{ item.attacker }}</div>
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
          <div class="VS">VS</div>
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
        <div class="logNickname">{{ item.defender }}</div>
      </div>
      <InfiniteLoading @infinite="load" />
      <div class="block"></div>
    </div>
  </div>
  <div v-else>내가 가진 독초몬이 아닙니다</div>
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
      myDoughnutChart: {
        type: 'doughnut',
        data: {
          labels: ['win', 'lose'],
          type: 'donut',
          datasets: [
            {
              backgroundColor: ['rgb(167, 167, 244)', 'rgb(255, 161, 161)'],
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
          labels: ['win', 'lose'],
          type: 'donut',
          datasets: [
            {
              backgroundColor: ['rgb(167, 167, 244)', 'rgb(255, 161, 161)'],
              data: [40, 20]
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
    }
  },
  created() {
    const option = {
      headers: {
        AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
      }
    }
    axios
      .get(BASE_URL + '/api/v1/game/monster/mylist?size=100', option)
      .then((res) => {
        console.log(res.data)
        res.data.content.forEach((element) => {
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
  height: 20vh;
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
  margin: 3vh;
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
  height: 12vh;
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
  background-color: rgb(255, 161, 161);
}
.blue {
  background-color: rgb(167, 167, 244);
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
}
</style>
