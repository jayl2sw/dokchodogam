<template>
  <div v-if="this.searchUser !== '*'">
    <div class="profile">
      <img
        :src="this.imageBaseUrl + '/' + this.searchUser.profile_img + '.png'"
        alt=""
        class="profileImage"
      />
      <div class="profileMiddle">
        <div class="nicknameBox">
          <img src="" alt="" class="tierImage" />
          <div class="nickname">{{ this.searchUser.nickname }}</div>
        </div>
        <div class="ranking">현재 랭킹 : {{ this.ranking }}위</div>
        <div class="ranking">
          랭킹 포인트 : {{ this.searchUser.rank_point }}
        </div>
      </div>
      <div class="chart">
        <vue3-chart-js v-bind="this.doughnutChart" ref="graph" />
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
      doughnutChart: {
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
              text: '승률'
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
        const win = res.data.winRate.winAttack + res.data.winRate.winDefence
        this.doughnutChart.data.datasets[0].data = [
          win,
          res.data.winRate.totalGames - win
        ]
        this.doughnutChart.options.plugins.subtitle.text =
          Math.round(res.data.winRate.winRate * 1000) / 10 + '%'
        console.log(this.winRate, this.doughnutChart.data.datasets[0].data)
        this.$refs.graph.update(100)
      })
      .catch((err) => console.log(err))
    axios
      .get(
        BASE_URL + '/api/v1/game/yourranking/' + this.searchUser.user_id,
        option
      )
      .then((res) => (this.ranking = res.data))
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
.tierImage {
  width: 4vh;
  height: 4vh;
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
    margin: 3vh;
    width: 60vw;
    height: auto;
    display: flex;
    flex-direction: column;
    align-items: center;
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
}
</style>
