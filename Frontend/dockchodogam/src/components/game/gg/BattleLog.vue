<template>
  <div v-if="this.searchUser !== '*'">
    <div class="profile">
      <div class="chart">
        <vue3-chart-js v-bind="this.doughnutChart" />
      </div>
      <img
        :src="this.imageBaseUrl + '/' + this.searchUser.profile_img + '.png'"
        alt=""
        class="profileImage"
      />
      <div class="profileMiddle">
        <div class="nickname">{{ this.searchUser.nickname }}</div>
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
    <div class="battleLog" v-if="this.searchUser.nickname">
      <div
        class="battleLogItem"
        v-for="(item, i) in this.userBattleLog"
        :key="i"
        :class="
          item.success && item.attacker === this.searchUser.nickname
            ? 'blue'
            : item.success && item.attacker !== this.searchUser.nickname
            ? 'red'
            : !item.success && item.attacker === this.searchUser.nickname
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
      <div :class="this.last ? '' : 'block'"></div>
    </div>
  </div>
  <div v-else>유저 정보가 존재하지 않습니다.</div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import InfiniteLoading from 'v3-infinite-loading'
import 'v3-infinite-loading/lib/style.css'
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'

export default {
  props: ['searchUser'],
  components: {
    InfiniteLoading,
    Vue3ChartJs
  },
  data() {
    return {
      userBattleLog: [],
      battleLogPage: 0,
      array1: ['monster0', 'monster1', 'monster2', 'monster3', 'monster4'],
      array2: ['monster5', 'monster6', 'monster7', 'monster8', 'monster9'],
      ranking: 0,
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      winRate: 0,
      doughnutChart: {
        type: 'doughnut',
        data: {
          labels: ['win', 'lose'],
          type: 'donut',
          datasets: [
            {
              backgroundColor: ['rgb(255, 161, 161)', 'rgb(167, 167, 244)'],
              data: [40, 20]
            }
          ]
        }
      }
    }
  },
  methods: {
    load($state) {
      const option = {
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      }
      axios
        .get(
          BASE_URL +
            '/api/v1/gg/log/user/' +
            this.searchUser.nickname +
            '/' +
            this.battleLogPage,
          option
        )
        .then((res) => {
          console.log('data', res.data)
          if (res.data.BattleDto.length) {
            this.userBattleLog = this.userBattleLog.concat(res.data.BattleDto)
            this.battleLogPage += 1
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
    axios
      .get(
        BASE_URL +
          '/api/v1/gg/log/user/' +
          this.searchUser.nickname +
          '/' +
          this.battleLogPage,
        {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        }
      )
      .then((res) => {
        const win = res.data.winRate.winAttack + res.data.winRate.winDefence
        this.doughnutChart.data.datasets.data = [
          win,
          res.data.winRate.totalGames - win
        ]
        this.winRate = Math.round(res.data.winRate.winRate * 1000) / 10
        console.log(this.winRate, this.doughnutChart.data.datasets.data)
        console.log(this.doughnutChart)
      })
      .catch((err) => console.log(err))
  }
}
</script>

<style scoped>
.profile {
  display: flex;
  height: 30vh;
  align-items: center;
  justify-content: center;
  margin: 2vh 0;
}
.chart {
  width: 30vh;
  height: 30vh;
}
.profileImage {
  width: 9vh;
  height: 9vh;
  border-radius: 2vh;
  margin: 0 1vw;
}
.profileMiddle {
  width: 10vw;
  height: 10vh;
  margin: 0 1vw;
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
  width: 8vh;
  height: 8vh;
}
.tierBar {
  width: 20vw;
  margin: 0 1vw;
}
.myChoiceDokcho {
  border: 2px groove black;
  border-radius: 10px;
  overflow-x: auto;
  height: 20vh;
  margin: 2vh 0;
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
  width: 75vw;
  height: 10vh;
  display: flex;
  justify-content: space-evenly;
  margin: 1vh 0;
}
.logNickname {
  width: 7.5vw;
  height: 100%;
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
  border: 2px groove black;
  border-radius: 1vw;
  width: 4vw;
  height: 4vw;
  margin: 0.5vw;
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
</style>
