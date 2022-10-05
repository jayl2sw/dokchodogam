<template>
  <div v-if="this.searchUser !== '*'">
    <div class="profile">
      <img
        :src="
          require('@/assets/monster/' + this.searchUser.profile_img + '.png')
        "
        alt=""
        class="profileImage"
      />
      <div class="profileMiddle">
        <div class="nicknameBox">
          <img
            :src="
              this.imageBaseUrl +
              '/tier' +
              Math.min(
                (this.searchUser.rank_point -
                  (this.searchUser.rank_point % 100)) /
                  100 +
                  1,
                5
              ) +
              '.png'
            "
            alt="tierImage"
            class="tierImage"
          />
          <div class="nickname TITLE">&nbsp;{{ this.searchUser.nickname }}</div>
        </div>
        <div class="ranking">
          현재 랭킹 : <span class="TITLE emphasize">{{ this.ranking }}</span
          >위
        </div>
        <div class="ranking">
          랭킹 포인트 :
          <span class="TITLE emphasize">{{ this.searchUser.rank_point }}</span>
        </div>
      </div>
      <div class="chart">
        <vue3-chart-js v-bind="this.doughnutChart" ref="graph" />
      </div>
    </div>
    <div class="infoText TITLE">선택한 풀깨비들</div>
    <div class="myChoiceDokcho">
      <div
        class="myChoiceDokchoItem"
        v-for="(item, i) in this.pickRate"
        :key="i"
      >
        <img
          class="itemImage"
          :src="require('@/assets/monster/' + item.monsterId + '.png')"
          alt=""
        />
        <div class="itemName TITLE">{{ item.monsterName }}몬</div>
        <div class="itemPer">
          픽률 :
          <span class="TITLE emphasize pickrate">{{ item.pickRate }}</span> %
        </div>
      </div>
    </div>
    <div class="infoText TITLE">역대 전적</div>
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
              :src="require('@/assets/monster/' + item[idx] + '.png')"
              alt=""
            />
          </div>
          <div class="VS TITLE">vs</div>
          <div class="deck">
            <img
              class="deckItem"
              v-for="idx in this.array2"
              :key="idx"
              :src="require('@/assets/monster/' + item[idx] + '.png')"
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
      pickRate: [],
      array1: ['monster0', 'monster1', 'monster2', 'monster3', 'monster4'],
      array2: ['monster5', 'monster6', 'monster7', 'monster8', 'monster9'],
      ranking: 0,
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      doughnutChart: {
        type: 'doughnut',
        data: {
          labels: ['승리', '패배'],
          type: 'donut',
          datasets: [
            {
              backgroundColor: ['rgb(153, 204, 255)', 'rgb(255, 161, 161)'],
              data: [40, 20],
              borderColor: ['rgb(0, 0, 0)'],
              borderWidth: 4
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
            console.log(res.data)
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
        this.doughnutChart.data.datasets[0].data = [
          res.data.winRate.winGame,
          res.data.winRate.totalGame - res.data.winRate.winGame
        ]
        this.doughnutChart.options.plugins.subtitle.text =
          Math.round(res.data.winRate.winRate * 1000) / 10 + '%'
        console.log(this.winRate, this.doughnutChart.data.datasets[0].data)
        this.$refs.graph.update(100)
        this.pickRate = res.data.pickRate
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
  margin-bottom: 1vw;
}
.tierImage {
  width: 4vh;
  height: 4vh;
}
.nickname {
  font-size: 4vh;
}
.emphasize {
  font-size: 3vh;
  color: #467302;
}
.ranking {
  font-size: 2vh;
  line-height: 4vh;
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
.myChoiceDokcho {
  border-radius: 15px;
  background-color: #a7c957;
  overflow-x: auto;
  white-space: nowrap;
  margin: 2vh 0;
}
.myChoiceDokchoItem {
  padding: 1vh 0;
  border-radius: 10px;
  width: 13vh;
  height: 18vh;
  margin: 1vh;
  display: inline-flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  background-color: #ececec;
}
.itemImage {
  width: 9vh;
  height: 9vh;
}
.itemName {
  font-size: 1.3vh;
}
.itemPer {
  font-size: 1vh;
}
.pickrate {
  font-size: 2vh;
}
.battleLog {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.battleLogItem {
  border-radius: 30px;
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
@media screen and (max-width: 650px) {
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
  .infoText {
    font-size: 5vw;
  }
  .myChoiceDokcho {
    width: 90vw;
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
}
@media screen and (max-height: 500px) {
  .chart {
    width: 40vh;
    height: 40vh;
  }
  .profile {
    height: 40vh;
  }
  .myChoiceDokcho {
    height: 40vh;
  }
  .myChoiceDokchoItem {
    width: 10vw;
    height: 30vh;
  }
  .itemImage {
    width: 9vw;
    height: 9vw;
  }
}
::-webkit-scrollbar {
  display: block;
}
</style>
