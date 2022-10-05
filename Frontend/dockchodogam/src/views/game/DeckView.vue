<template>
  <div class="deckPage">
    <div id="warning-message">
      <p class="TITLE">
        아레나는<br /><br /><span class="emphasize">"가로 화면 전용"</span>
        게임입니다.
      </p>
    </div>
    <div class="deck" v-show="!this.isLoading">
      <div class="myDeck">
        <div class="myDeckItems">
          <div
            class="myDeckItem TITLE"
            v-for="(item, i) in this.myDeck"
            :key="i"
            @click="onClickDeck(i)"
            :class="this.selectDeck === i ? 'checked' : ''"
          >
            <img
              :src="require('@/assets/monster/' + item.monsterId + '.png')"
              alt=""
              class="myDeckItemImage"
            />
            <p class="myDeckItem__name TITLE">{{ item.name }}몬</p>
            <div class="myDeckItemIdx TITLE">{{ i + 1 }}</div>
          </div>
        </div>
      </div>
      <div class="myDockcho">
        <div
          class="myDockchoItemBox TITLE"
          :class="this.selectDockcho === i ? 'candidate__checked' : ''"
          v-for="(item, i) in this.myDockcho"
          :key="i"
          @click="onClickDockcho(i)"
        >
          <div
            class="myDockchoItem"
            :class="{
              card__common: item.grade == 'COMMON',
              card__rare: item.grade == 'RARE',
              card__epic: item.grade == 'EPIC',
              card__legendary: item.grade == 'LEGENDARY',
              card__special: item.grade == 'SPECIAL',
              card__dokcho: item.type == 'DOKCHO',
              card__yakcho: item.type == 'YAKCHO',
              card__japcho: item.type == 'JAPCHO',
              card__hidden: item.type == 'HIDDEN'
            }"
          >
            <div
              class="dokchoBlur"
              :class="this.check[i] ? 'inMyDeck' : ''"
            ></div>
            <img
              :src="require('@/assets/monster/' + item.monsterId + '.png')"
              alt=""
              class="myDockchoItemImage"
            />
          </div>
          <p class="myDockchoItem__name TITLE">{{ item.name }}몬</p>
        </div>
      </div>
      <div class="buttons">
        <div class="cancel TITLE" @click="goToArena()">취소</div>
        <div class="complete TITLE" @click="changeMyDeck()">완료</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapGetters } from 'vuex'
import { BASE_URL } from '@/constant/BASE_URL'
import swal from 'sweetalert'

export default {
  data() {
    return {
      myDeck: [],
      myDockcho: [],
      check: [],
      selectDeck: '',
      selectDockcho: '',
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      audio: new Audio(process.env.VUE_APP_S3_URL + '/deck.mp3')
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
          res.data.forEach((element) => {
            let tmpBool = false
            this.myDeck.forEach((el) => {
              if (el.monsterId === element.monsterId) {
                tmpBool = true
              }
            })
            this.myDockcho.push(element)
            this.check.push(tmpBool)
          })
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
          if (this.selectDockcho === idx) {
            this.selectDockcho = ''
          } else {
            this.selectDockcho = idx
          }
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
      axios
        .put(BASE_URL + '/api/v1/game/deck/save', params, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then(() => {
          swal('변경 완료!', '덱 변경이 완료되었습니다 😺', 'success')
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
    axios
      .get(BASE_URL + '/api/v1/game/deck/myInfo', {
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      })
      .then((res) => {
        this.myDeck = res.data
      })
      .catch((err) => console.log(err))
  },
  mounted() {
    this.audio.loop = true
    this.audio.volume = 0.3
    this.audio.play()
  },
  beforeUnmount() {
    this.audio.pause()
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
  border-radius: 20px;
  height: 90vh;
  width: 90vw;
  margin: 5vh 5vw;
  display: flex;
  flex-direction: column;
  background-color: white;
  justify-content: space-evenly;
  align-items: center;
}
.myDeck {
  height: 40vh;
  width: 85vw;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: #ececec;
}
.myDeckItems {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 90%;
  width: 100%;
}
.myDeckItem {
  font-size: 1.5vh;
  border-radius: 5vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 90%;
  width: 15vw;
  cursor: pointer;
}
.myDeckItemImage {
  min-width: 20vh;
  min-height: 20vh;
}
.myDeckItemIdx {
  border-radius: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 3vh;
  width: 8vw;
  height: 5vh;
  background-color: #a7c957;
}
.myDockcho {
  border-radius: 20px;
  height: 30vh;
  width: 85vw;
  padding: 0 1vw;
  overflow-x: auto;
  align-items: center;
  white-space: nowrap;
  background-color: #a7c957;
}
.myDockchoItemBox {
  height: 80%;
  width: 20vh;
  border: 4px dotted #a7c957;
  margin: 0 0.5vw;
  margin-top: 2vh;
  display: inline-flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-size: 1.5vh;
}
.myDockchoItem {
  margin-top: 5vh;
  border-radius: 10px;
  height: 15vh;
  width: 15vh;
  cursor: pointer;
  position: relative;
  display: inline-block;
}
.myDockchoItemImage {
  width: 15vh;
  height: 15vh;
}
.buttons {
  height: 5vh;
  width: 85vw;
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
  font-size: 2vh;
  width: 7vw;
  height: 5vh;
  background-color: #ccc;
  cursor: pointer;
  margin-right: 2vw;
  transition: 0.3s;
}
.cancel:hover {
  background-color: rgb(174, 0, 0);
  color: white;
}
.complete {
  border-radius: 2.5vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2vh;
  width: 7vw;
  height: 5vh;
  background-color: #a7c957;
  cursor: pointer;
}
.complete:hover {
  background-color: #467302;
  color: white;
}
.checked {
  background-color: rgba(167, 201, 87, 0.3);
}
.candidate__checked {
  background-color: rgba(255, 255, 255, 0.3);
}
.dokchoBlur {
  background-color: rgba(0, 0, 0, 0.4);
  border-radius: 10px;
  top: -10%;
  left: -7%;
  width: 115%;
  height: 140%;
  z-index: 9999;
  display: none;
  position: absolute;
  cursor: default;
}
.inMyDeck {
  display: block;
}

.myDockchoItem__name {
  margin-top: 0.5vmin;
  z-index: 9999;
}

.card__common {
  background-color: rgb(166, 166, 166);
}
.card__rare {
  background-color: skyblue;
}
.card__epic {
  background-color: violet;
}
.card__legendary {
  background-color: yellow;
}
.card__special {
  background-image: url(https://img.freepik.com/premium-vector/glitters-rainbow-sky-shiny-rainbows-pastel-color-magic-fairy-starry-skies-and-glitter-sparkles-background-illustration_102902-1299.jpg?w=2000);
}

.card__dokcho {
  box-shadow: 0 0 20px #ff5555;
}

.card__yakcho {
  box-shadow: 0 0 20px #467302;
}

.card__japcho {
  box-shadow: 0 0 20px #ffe140;
}

.card__hidden {
  box-shadow: 0 0 20px #c493ff;
}

.dokcho__filter {
  display: flex;
  flex-direction: row;
  margin-top: 1.5vh;
  justify-content: space-around;
}

.checkbox2 .checkbox3 {
  display: flex;
  flex-direction: row;
}

.checkbox2 label,
.checkbox3 label {
  font-size: 1.2vw;
}

.checkbox2 span,
.checkbox3 span {
  font-size: 1.2vw;
}
@media only screen and (orientation: portrait) {
  .deckPage {
    background-image: none;
    background-color: white;
    height: 100vh;
  }
  .deck {
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
}
@media only screen and (orientation: landscape) {
  #warning-message {
    display: none;
  }
}
</style>
