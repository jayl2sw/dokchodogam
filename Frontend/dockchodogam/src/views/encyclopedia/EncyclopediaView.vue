<template>
  <div class="dogam__container">
    <LoadingPage v-if="this.isLoading" />
    <NavBar v-show="!this.isLoading" @overflow="overflow" />
    <div v-show="!this.isLoading">
      <div class="dogam__title">
        <h1 class="TITLE">☘️ {{ userInfo.nickname }}의 도감 ☘️</h1>
      </div>

      <div class="dogam__checkbox">
        <div class="checkbox1">
          <p class="TITLE">독초몬 보유</p>

          <input type="checkbox" id="true" value="true" v-model="checkedGot" />
          <label for="true">보유</label>

          <input
            type="checkbox"
            id="false"
            value="false"
            v-model="checkedGot"
          />
          <label for="false">미보유</label>
        </div>

        <!-- 타입별 -->
        <div class="checkbox2">
          <p class="TITLE">독초몬 타입</p>

          <input
            type="checkbox"
            id="DOKCHO"
            value="DOKCHO"
            v-model="checkedType"
          />
          <label for="DOKCHO">독초😈</label>

          <input
            type="checkbox"
            id="YAKCHO"
            value="YAKCHO"
            v-model="checkedType"
          />
          <label for="YAKCHO">약초🥗</label>

          <input
            type="checkbox"
            id="JAPCHO"
            value="JAPCHO"
            v-model="checkedType"
          />
          <label for="JAPCHO">잡초🌻</label>

          <input
            type="checkbox"
            id="HIDDEN"
            value="HIDDEN"
            v-model="checkedType"
          />
          <label for="HIDDEN">히든✨</label>
        </div>

        <!-- 등급별 -->
        <div class="checkbox3">
          <p class="TITLE">독초몬 등급</p>

          <input
            type="checkbox"
            id="COMMON"
            value="COMMON"
            v-model="checkedGrade"
          />
          <label for="COMMON">COMMON</label>

          <input
            type="checkbox"
            id="RARE"
            value="RARE"
            v-model="checkedGrade"
          />
          <label for="RARE">RARE</label>

          <input
            type="checkbox"
            id="EPIC"
            value="EPIC"
            v-model="checkedGrade"
          />
          <label for="EPIC">EPIC</label>

          <input
            type="checkbox"
            id="LEGENDARY"
            value="LEGENDARY"
            v-model="checkedGrade"
          />
          <label for="LEGENDARY">LEGENDARY</label>

          <input
            type="checkbox"
            id="SPECIAL"
            value="SPECIAL"
            v-model="checkedGrade"
          />
          <label for="SPECIAL">SPECIAL</label>
        </div>
        <div class="selected">
          {{ filteredMonsters.length }} / 98
          <span>
            <img
              class="new-result-btn"
              @click="shareKakao"
              src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png"
          /></span>
        </div>
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

// import { BASE_URL } from '@/constant/BASE_URL'
// import { mapGetters } from 'vuex'

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
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  computed: {
    // ...mapGetters(['userInfo']),
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
    fetchMonsterList() {
      axios({
        url: 'https://j7e201.p.ssafy.io/api/v1/game/monster/list?size=100',
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      })
        .then((res) => {
          // console.log(res.data.content)

          this.monsters = res.data.content
        })
        .catch((err) => console.log(err))
    },
    shareKakao() {
      window.Kakao.Link.sendDefault({
        objectType: 'feed',
        content: {
          title: '독초도감☘',
          description: '독초? 약초? 독초도감에서 즐겨봐 ...',
          imageUrl:
            'https://1.gall-img.com/hygall/files/attach/images/82/378/769/165/5f617e6da9ed21981ad1280f727dd8b3.jpg',
          link: {
            // mobileWebUrl: '이미지 클릭시 이동할 사이트',
            webUrl: 'https://j7e201.p.ssafy.io'
          }
        },
        buttons: [
          {
            title: '도감 모으러 이동!',
            link: {
              // mobileWebUrl: '이미지 클릭시 이동할 사이트',
              webUrl: 'https://j7e201.p.ssafy.io'
            }
          }
        ]
      })
    }
  },
  created() {
    this.fetchMonsterList()
    setTimeout(() => {
      this.isLoading = false
    }, 2000)
  }
}
</script>

<style scoped>
.dogam__container {
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
  /* justify-content: space-between; */
  /* background-color: aqua; */
  text-align: start;
  margin-bottom: 5vh;
  margin-left: 10vw;
}

.selected {
  text-align: end;
  margin-right: 10vw;
  margin-bottom: 3vh;
  font-family: 'UhBeeSe_hyun';
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
</style>
