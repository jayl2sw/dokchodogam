<template>
  <LoadingPage v-if="this.isLoading" />
  <div class="deckPage">
    <div class="deck" v-show="!this.isLoading">
      <div class="myDeck">
        <div class="myDeckItems">
          <div
            class="myDeckItem"
            v-for="(item, i) in this.myDeck"
            :key="i"
            @click="onClickDeck(i)"
            :class="this.selectDeck === i ? 'checked' : ''"
          >
            <img
              :src="this.imageBaseUrl + '/' + item.monsterId + '.png'"
              alt=""
              class="myDeckItemImage"
            />
            {{ item.name }}몬
            <div class="myDeckItemIdx">{{ i + 1 }}</div>
          </div>
        </div>
      </div>
      <div class="myDockcho">
        <div
          class="myDockchoItemBox"
          :class="this.selectDockcho === i ? 'checked' : ''"
          v-for="(item, i) in this.myDockcho"
          :key="i"
          @click="onClickDockcho(i)"
        >
          <div class="myDockchoItem">
            <div
              class="dokchoBlur"
              :class="this.check[i] ? 'inMyDeck' : ''"
            ></div>
            <img
              :src="this.imageBaseUrl + '/' + item.monsterId + '.png'"
              alt=""
              class="myDockchoItemImage"
            />
          </div>
          {{ item.name }}몬
        </div>
      </div>
      <div class="buttons">
        <div class="cancel" @click="goToArena()">취소</div>
        <div class="complite" @click="changeMyDeck()">완료</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapGetters } from 'vuex'
import { BASE_URL } from '@/constant/BASE_URL'
import LoadingPage from '@/components/main/LoadingPage.vue'

export default {
  components: {
    LoadingPage
  },
  data() {
    return {
      myDeck: [],
      myDockcho: [],
      check: [],
      selectDeck: '',
      selectDockcho: '',
      isLoading: false,
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  methods: {
    goToArena() {
      this.$router.push({ path: '/game/arena' })
    },
    getMyDokcho() {
      axios
        .get(BASE_URL + '/api/v1/game/monster/mylist', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          console.log(res.data)
          res.data.content.forEach((element) => {
            let tmpBool = false
            this.myDeck.forEach((el) => {
              if (el.monsterId === element.monsterId) {
                tmpBool = true
              }
            })
            this.myDockcho.push(element)
            this.check.push(tmpBool)
          })
          console.log(this.myDockcho)
          console.log(this.check)
        })
        .catch((err) => console.log(err))
    },
    onClickDeck(idx) {
      if (this.selectDockcho === '' && this.selectDeck === '') {
        this.selectDeck = idx
      } else if (this.selectDeck !== '') {
        const tmp = this.myDeck[this.selectDeck]
        this.myDeck[this.selectDeck] = this.myDeck[idx]
        this.myDeck[idx] = tmp
        this.selectDeck = ''
      } else {
        const monId = this.myDeck[idx].monsterId
        this.myDeck[idx] = this.myDockcho[this.selectDockcho]
        this.myDockcho.forEach((el, i) => {
          if (el.monsterId === monId) {
            this.check[i] = false
          }
        })
        this.check[this.selectDockcho] = true
        this.selectDeck = ''
        this.selectDockcho = ''
      }
    },
    onClickDockcho(idx) {
      if (!this.check[idx]) {
        if (this.selectDeck === '') {
          this.selectDockcho = idx
        } else {
          const monId = this.myDeck[this.selectDeck].monsterId
          this.myDeck[this.selectDeck] = this.myDockcho[idx]
          this.check[idx] = true
          this.myDockcho.forEach((el, i) => {
            if (el.monsterId === monId) {
              this.check[i] = false
            }
          })
          this.selectDeck = ''
          this.selectDockcho = ''
        }
      }
    },
    changeMyDeck() {
      const params = {
        monster1: this.myDeck[0].monsterId,
        monster2: this.myDeck[1].monsterId,
        monster3: this.myDeck[2].monsterId,
        monster4: this.myDeck[3].monsterId,
        monster5: this.myDeck[4].monsterId
      }
      console.log(params)
      axios
        .put(BASE_URL + '/api/v1/game/deck/save', params, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          alert('변경완료!')
          this.$router.push({ path: '/game/arena' })
        })
        .catch((err) => console.log(err))
    }
  },
  computed: {
    ...mapGetters(['userDeck'])
  },
  created() {
    this.getMyDokcho()
    this.myDeck = this.userDeck
    setTimeout(() => {
      this.isLoading = false
    }, 3000)
  }
}
</script>

<style scoped>
.deckPage {
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-image: url('@/assets/game_background.png');
  background-repeat: no-repeat;
  background-size: cover;
}
.deck {
  border: 2px groove black;
  border-radius: 2px;
  height: 90vh;
  width: 90vw;
  margin: 5vh 5vw;
  display: flex;
  flex-direction: column;
  background-color: white;
}
.myDeck {
  height: 45vh;
  width: 85vw;
  margin: 5vh 2.5vw;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: #ececec;
}
.myDeckItems {
  display: flex;
  justify-content: space-around;
  margin: 0 0 2vh 0;
  height: 40vh;
  width: 100%;
}
.myDeckItem {
  border: 2px #ececec;
  border-radius: 2px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 15vw;
  cursor: pointer;
}
.myDeckItemImage {
  width: 12vw;
  height: 12vw;
}
.myDeckItemIdx {
  margin-top: 5vh;
  border-radius: 1.5vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 3vh;
  width: 10vw;
  height: 3vh;
  background-color: #a7c957;
}
.myDockcho {
  border-radius: 20px;
  height: 25vh;
  width: 85vw;
  margin: 0 2.5vw;
  padding: 0 1vw;
  overflow-x: auto;
  align-items: center;
  white-space: nowrap;
  background-color: #a7c957;
}
.myDockchoItemBox {
  height: 20vh;
  width: 20vh;
  border: 4px dotted #a7c957;
  margin: 0 0.5vw;
  margin-top: 2vh;
  display: inline-flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.myDockchoItem {
  margin-top: 1vh;
  border-radius: 10px;
  background-color: #ececec;
  height: 15vh;
  width: 15vh;
  cursor: pointer;
  position: relative;
  display: inline-block;
}
.myDockchoItemImage {
  margin: 0 0.5vh;
  width: 14vh;
  height: 14vh;
}
.buttons {
  height: 10vh;
  width: 85vw;
  margin: 0 2.5vw;
  padding: 0 1vw;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.cancel {
  border-radius: 2.5vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 3vh;
  width: 10vw;
  height: 5vh;
  background-color: #a7c957;
  cursor: pointer;
  margin-right: 5vw;
}
.complite {
  border-radius: 2.5vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 3vh;
  width: 10vw;
  height: 5vh;
  background-color: #a7c957;
  cursor: pointer;
}
.checked {
  border: 4px groove green;
}
.dokchoBlur {
  background-color: rgba(0, 0, 0, 0.4);
  border-radius: 10px;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 9999;
  display: none;
  position: absolute;
  cursor: default;
}
.inMyDeck {
  display: block;
}
@media screen and (max-width: 850px) {
  .myDockchoItemBox {
    font-size: 2vh;
  }
  .myDeckItem {
    font-size: 3vh;
  }
  .myDeckItemIdx {
    margin-top: 1vh;
  }
}
</style>
