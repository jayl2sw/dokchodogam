<template>
  <div class="dogam__container" :class="this.showMenu ? 'open-menu' : ''">
    <LoadingPage v-if="this.isLoading" />
    <NavBar v-show="!this.isLoading" @overflow="overflow" />

    <h3 v-show="!this.isLoading" @click="backToTop" class="toTheTop">π</h3>
    <div v-show="!this.isLoading">
      <div class="dogam__title">
        <h1 class="TITLE">βοΈ {{ userInfo.nickname }}μ λκ° βοΈ</h1>
      </div>

      <div class="dogam__checkbox">
        <div class="checkbox1">
          <p class="TITLE">νκΉ¨λΉ π±</p>

          <input type="checkbox" id="true" value="true" v-model="checkedGot" />
          <label for="true">λ³΄μ </label>

          <input
            type="checkbox"
            id="false"
            value="false"
            v-model="checkedGot"
          />
          <label for="false">λ―Έλ³΄μ </label>
        </div>

        <!-- νμλ³ -->
        <div class="checkbox2">
          <p class="TITLE">νκΉ¨λΉ νμ</p>

          <input
            type="checkbox"
            id="DOKCHO"
            value="DOKCHO"
            v-model="checkedType"
          />
          <label for="DOKCHO">λμ΄π</label>

          <input
            type="checkbox"
            id="YAKCHO"
            value="YAKCHO"
            v-model="checkedType"
          />
          <label for="YAKCHO">μ½μ΄πΏ</label>

          <input
            type="checkbox"
            id="JAPCHO"
            value="JAPCHO"
            v-model="checkedType"
          />
          <label for="JAPCHO">μ‘μ΄π»</label>

          <input
            type="checkbox"
            id="HIDDEN"
            value="HIDDEN"
            v-model="checkedType"
          />
          <label for="HIDDEN">νλ π</label>
        </div>

        <!-- λ±κΈλ³ -->
        <div class="checkbox3">
          <p class="TITLE">νκΉ¨λΉ λ±κΈ</p>

          <input
            type="checkbox"
            id="COMMON"
            value="COMMON"
            v-model="checkedGrade"
          />
          <label for="COMMON">μΌλ°</label>

          <input
            type="checkbox"
            id="RARE"
            value="RARE"
            v-model="checkedGrade"
          />
          <label for="RARE">ν¬κ·</label>

          <input
            type="checkbox"
            id="EPIC"
            value="EPIC"
            v-model="checkedGrade"
          />
          <label for="EPIC">μμ</label>

          <input
            type="checkbox"
            id="LEGENDARY"
            value="LEGENDARY"
            v-model="checkedGrade"
          />
          <label for="LEGENDARY">μ μ€</label>

          <input
            type="checkbox"
            id="SPECIAL"
            value="SPECIAL"
            v-model="checkedGrade"
          />
          <label for="SPECIAL">μ€νμ</label>
        </div>
        <div class="selected">{{ filteredMonsters.length }} / 98</div>
      </div>
      <div class="container">
        <div class="row">
          <div
            class="col"
            v-for="monster in filteredMonsters"
            :key="monster.monsterId"
          >
            <MonsterCard :monster="monster" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'
import MonsterCard from '@/components/encyclopedia/MonsterCard.vue'
import axios from 'axios'
import LoadingPage from '@/components/main/LoadingPage.vue'

export default {
  components: {
    NavBar,
    MonsterCard,
    LoadingPage
  },
  data() {
    return {
      monsters: [],
      checkedType: [],
      checkedGrade: [],
      checkedGot: ['true'],
      isLoading: true,
      userInfo: JSON.parse(localStorage.getItem('userInfo')),
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      audio: new Audio(process.env.VUE_APP_S3_URL + '/encyclopedia.mp3'),
      showMenu: false
    }
  },
  computed: {
    filteredMonsters() {
      if (
        !this.checkedType.length &&
        !this.checkedGrade.length &&
        !this.checkedGot.length
      ) {
        return this.monsters
      } else if (
        !this.checkedType.length &&
        !this.checkedGrade.length &&
        this.checkedGot.length
      ) {
        return this.monsters.filter((monster) =>
          this.checkedGot.includes(monster.got.toString())
        )
      } else if (
        this.checkedType.length &&
        !this.checkedGrade.length &&
        !this.checkedGot.length
      ) {
        return this.monsters.filter((monster) =>
          this.checkedType.includes(monster.type)
        )
      } else if (
        this.checkedType.length &&
        !this.checkedGrade.length &&
        this.checkedGot.length
      ) {
        return this.monsters.filter(
          (monster) =>
            this.checkedType.includes(monster.type) &&
            this.checkedGot.includes(monster.got.toString())
        )
      } else if (
        this.checkedType.length &&
        this.checkedGrade.length &&
        !this.checkedGot.length
      ) {
        return this.monsters.filter(
          (monster) =>
            this.checkedType.includes(monster.type) &&
            this.checkedGrade.includes(monster.grade)
        )
      } else if (
        this.checkedType.length &&
        this.checkedGrade.length &&
        this.checkedGot.length
      ) {
        return this.monsters.filter(
          (monster) =>
            this.checkedType.includes(monster.type) &&
            this.checkedGrade.includes(monster.grade) &&
            this.checkedGot.includes(monster.got.toString())
        )
      } else if (
        !this.checkedType.length &&
        this.checkedGrade.length &&
        this.checkedGot.length
      ) {
        return this.monsters.filter(
          (monster) =>
            this.checkedGrade.includes(monster.grade) &&
            this.checkedGot.includes(monster.got.toString())
        )
      } else {
        return this.monsters.filter((monster) =>
          this.checkedGrade.includes(monster.grade)
        )
      }
    }
  },
  methods: {
    overflow(value) {
      this.showMenu = value
    },
    backToTop() {
      document.body.scrollTop = 0
      document.documentElement.scrollTop = 0
    },
    fetchMonsterList() {
      axios({
        url: 'https://j7e201.p.ssafy.io/api/v1/game/monster/list?size=100',
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      })
        .then((res) => {
          this.monsters = res.data.content
        })
        .catch((err) => console.log(err))
    }
  },
  created() {
    this.fetchMonsterList()
    setTimeout(() => {
      this.isLoading = false
    }, 2000)
  },
  mounted() {
    this.audio.loop = true
    this.audio.volume = 0.2
    this.audio.play()
  },
  beforeUnmount() {
    this.audio.pause()
  }
}
</script>

<style scoped>
.toTheTop {
  position: fixed;
  bottom: 20px;
  right: 20px;
  cursor: pointer;
}

.toTheTop:hover {
  position: fixed;
  bottom: 20px;
  right: 20px;
}

.dogam__container {
  height: 100%;
  min-height: 100vh;
  background-image: url('@/assets/dogam_background.jpg');
}

.dogam__title {
  text-align: center;
  margin-top: 10vh;
}

.dogam__checkbox {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.checkbox1,
.checkbox2,
.checkbox3 {
  flex-direction: row;
  text-align: start;
  margin-bottom: 5vh;
  margin-left: 10vw;
}

.selected {
  text-align: end;
  margin-right: 10vw;
  margin-bottom: 3vh;
  font-family: 'UhBeeSe_hyun', sans-serif;
}

p {
  display: inline;
  background-color: #a7c957;
  padding: 0.5vw;
  border-radius: 5px;
}
.new-result-btn {
  height: 42px;
  border-radius: 10px;
  border: 2px solid #636366;
}

input[type='checkbox'] {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  background: #d0d0d0;
  border-radius: 4px;
  cursor: pointer;
  height: 16px;
  outline: 0;
  width: 16px;
  margin-left: 1vw;
  margin-right: 0.5vw;
}
input[type='checkbox']::after {
  border: solid #fff;
  border-width: 0 2px 2px 0;
  content: '';
  display: none;
  height: 40%;
  left: 40%;
  position: relative;
  top: 20%;
  transform: rotate(45deg);
  width: 15%;
}
input[type='checkbox']:checked {
  background: #a7c957;
}
input[type='checkbox']:checked::after {
  display: block;
}

.container {
  display: grid;
}
.col {
  padding: 2vh;
}
.open-menu {
  overflow: hidden;
  position: fixed;
}
</style>
