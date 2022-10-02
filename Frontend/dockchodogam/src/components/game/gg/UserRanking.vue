<template>
  <div class="ranking">
    <div class="top3Box">
      <div class="top3Text TITLE">
        <font-awesome-icon icon="fa-solid fa-crown" />&nbsp;명예의
        전당&nbsp;<font-awesome-icon icon="fa-solid fa-crown" />
      </div>
      <div class="top3" v-for="(item, i) in this.rankList.slice(0, 3)" :key="i">
        <div>
          <img
            class="rankIntImage"
            :src="require('@/assets/medal' + (i + 1) + '.png')"
            alt=""
          />
        </div>
        <img
          :src="
            this.imageBaseUrl +
            '/tier' +
            Math.min((item.rankPoint - (item.rankPoint % 100)) / 100 + 1, 5) +
            '.png'
          "
          alt="tier"
          class="tier"
        />
        <div class="top3RankerName TITLE">
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
        <div class="rankPoint TITLE">{{ item.rankPoint }}점</div>
      </div>
    </div>
    <div class="ect" v-for="(item, i) in this.rankList.slice(3)" :key="i">
      <div class="rankerName TITLE">
        <img
          :src="
            this.imageBaseUrl +
            '/tier' +
            Math.min((item.rankPoint - (item.rankPoint % 100)) / 100 + 1, 5) +
            '.png'
          "
          alt="tier"
          class="tier"
        />{{ item.nickname }}
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
      <div class="rankPoint TITLE">{{ item.rankPoint }}점</div>
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
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import InfiniteLoading from 'v3-infinite-loading'
import 'v3-infinite-loading/lib/style.css'

export default {
  components: {
    InfiniteLoading
  },
  data() {
    return {
      rankList: [],
      rankingPage: 0,
      imageBaseUrl: process.env.VUE_APP_S3_URL
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
        .get(BASE_URL + '/api/v1/gg/ranking?page=' + this.rankingPage, option)
        .then((res) => {
          console.log(res.data)
          if (res.data.content.length) {
            this.rankList = this.rankList.concat(res.data.content)
            this.rankingPage += 1
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
  }
}
</script>

<style scoped>
.ranking {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.top3Box {
  width: 80vw;
  height: 65vh;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
  background-color: #a7c957;
  margin-bottom: 5vh;
}
.top3Text {
  font-size: 4vw;
  display: flex;
  align-items: center;
}
.top3 {
  width: 70vw;
  height: 13vh;
  border-radius: 20px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  background-color: #ececec;
}
.rankIntImage {
  width: 5vw;
  height: 5vw;
}
.top3RankerName {
  width: 13vw;
  height: 10vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2vw;
}
.rankerName {
  width: 15vw;
  height: 13vh;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 2vw;
}
.rankerDeck {
  width: 30vw;
  display: flex;
  justify-content: space-evenly;
}
.rankerDeckItem {
  width: 5vw;
  height: 5vw;
}
.rankPoint {
  font-size: 3vw;
}
.ect {
  width: 65vw;
  height: 13vh;
  margin: 2vh 0;
  border-radius: 20px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  background-color: #ececec;
}
.tier {
  width: 5vw;
  height: 5vw;
}
svg {
  width: 6vw;
  height: 6vw;
  color: gold;
}
.block {
  margin: 5vh;
}
@media screen and (max-width: 660px) {
  .top3Box {
    width: 90vw;
    height: 50vh;
  }
  .top3 {
    width: 80vw;
    height: 10vh;
  }
  .ect {
    width: 75vw;
    height: 10vh;
  }
  .tier {
    width: 7vw;
    height: 7vw;
  }
  .rankerName {
    width: 20vw;
    font-size: 3vw;
  }
  .rankerDeck {
    width: 40vw;
  }
  .rankerDeckItem {
    width: 7vw;
    height: 7vw;
  }
  .rankPoint {
    font-size: 2vw;
  }
}
</style>
