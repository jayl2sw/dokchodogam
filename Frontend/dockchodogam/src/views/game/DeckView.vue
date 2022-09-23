<template>
  <LoadingPage v-if="this.isLoading" />
  <div class="deck" v-show="!this.isLoading">
    <div class="myDeck">
      <div class="myDeck__sum">
        <div class="myDeck__hp"></div>
        <div class="myDeck__power"></div>
      </div>
      <div class="myDeckItems">
        <div
          class="myDeckItem"
          v-for="(item, i) in this.myDeck"
          :key="i"
          @click="onClickDeck(i)"
          :class="this.selectDeck === i ? 'checked' : ''"
        >
          {{ item.name }}
        </div>
      </div>
    </div>
    <div class="myDockcho">
      <div
        class="myDockchoItem"
        v-for="(item, i) in this.myDockcho"
        :key="i"
        @click="onClickDockcho(i)"
        :class="this.selectDockcho === i ? 'checked' : ''"
      >
        <div class="dokchoBlur" :class="this.check[i] ? 'inMyDeck' : ''"></div>
        {{ item.name }}
      </div>
    </div>
    <div class="buttons">
      <div class="cancel" @click="goToArena()">취소</div>
      <div class="complite" @click="changeMyDeck()">완료</div>
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
      isLoading: true
    }
  },
  methods: {
    goToArena() {
      this.$router.push({ path: '/game/arena' })
    },
    getMyDokcho() {
      axios
        .get(BASE_URL + '/api/v1/game/monster/list?size=100', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          res.data.content.forEach((element) => {
            if (element.got) {
              let tmpBool = false
              this.myDeck.forEach((el) => {
                if (el.monsterId === element.monsterId) {
                  tmpBool = true
                }
              })
              this.myDockcho.push(element)
              this.check.push(tmpBool)
            }
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
.deck {
  border: 2px groove black;
  border-radius: 2px;
  height: 90vh;
  width: 90vw;
  margin: 5vh 5vw;
  display: flex;
  flex-direction: column;
}
.myDeck {
  height: 50vh;
  width: 85vw;
  margin: 5vh 2.5vw 0 2.5vw;
  display: flex;
  flex-direction: column;
}
.myDeck__sum {
  display: flex;
  justify-content: flex-end;
  height: 10vh;
  width: 100%;
  margin: 2.5vh 0;
  padding: 0 2vw;
}
.myDeck__hp {
  border: 2px groove black;
  border-radius: 5px;
  width: 15vw;
  height: 5vh;
  margin: 0 0.5vw;
}
.myDeck__power {
  border: 2px groove black;
  border-radius: 5px;
  width: 15vw;
  height: 5vh;
  margin: 0 0.5vw;
}
.myDeckItems {
  display: flex;
  justify-content: space-around;
  margin: 0 0 2vh 0;
  height: 35vh;
  width: 100%;
}
.myDeckItem {
  border: 2px groove black;
  border-radius: 2px;
  height: 100%;
  width: 15vw;
  cursor: pointer;
}
.myDockcho {
  border: 2px groove black;
  border-radius: 2px;
  height: 22vh;
  width: 85vw;
  margin: 0 2.5vw;
  padding: 0 1vw;
  overflow-x: auto;
  align-items: center;
  white-space: nowrap;
}
.myDockchoItem {
  border: 2px groove black;
  border-radius: 2px;
  height: 18vh;
  width: 18vh;
  margin: 0 0.5vw;
  margin-top: 1vh;
  cursor: pointer;
  position: relative;
  display: inline-block;
}
.buttons {
  height: 10vh;
  width: 85vw;
  margin: 0 2.5vw;
  padding: 0 1vw;
  display: flex;
  justify-content: flex-end;
}
.cancel {
  border: 2px groove black;
  border-radius: 2px;
  height: 5vh;
  width: 5vw;
  margin: 2.5vh 2vw;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.complite {
  border: 2px groove black;
  border-radius: 2px;
  height: 5vh;
  width: 5vw;
  margin: 2.5vh 2vw;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.checked {
  border: 4px groove green;
}
.dokchoBlur {
  background-color: rgba(0, 0, 0, 0.4);
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
</style>
