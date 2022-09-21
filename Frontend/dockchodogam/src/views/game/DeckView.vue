<template>
  <div class="deck">
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
          {{ item }}
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
        {{ item }}
      </div>
    </div>
    <div class="buttons">
      <div class="cancel" @click="goToArena()">취소</div>
      <div class="complite">완료</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      myDeck: [1, 2, 3, 4, 5],
      myDockcho: [6, 7, 8, 9, 10, 11, 12],
      selectDeck: '',
      selectDockcho: ''
    }
  },
  methods: {
    onClickDeck(idx) {
      if (this.selectDockcho === '') {
        this.selectDeck = idx
      } else {
        const tmp = this.myDeck[idx]
        this.myDeck[idx] = this.myDockcho[this.selectDockcho]
        this.myDockcho[this.selectDockcho] = tmp
        this.selectDeck = ''
        this.selectDockcho = ''
      }
    },
    onClickDockcho(idx) {
      if (this.selectDeck === '') {
        this.selectDockcho = idx
      } else {
        const tmp = this.myDockcho[idx]
        this.myDockcho[idx] = this.myDeck[this.selectDeck]
        this.myDeck[this.selectDeck] = tmp
        this.selectDeck = ''
        this.selectDockcho = ''
      }
    },
    goToArena() {
      this.$router.push({ path: '/game/arena' })
    }
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
  height: 20vh;
  width: 85vw;
  margin: 0 2.5vw;
  padding: 0 1vw;
  display: flex;
  overflow: auto;
  align-items: center;
}
.myDockchoItem {
  border: 2px groove black;
  border-radius: 2px;
  height: 18vh;
  width: 18vh;
  margin: 0 0.5vw;
  cursor: pointer;
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
::-webkit-scrollbar {
  display: none;
}
</style>
