<template>
  <LoadingPage v-if="this.isLoading" />
  <NavBar @overflow="overflow" />
  <div>
    <h1 class="dogam__title">나의 도감</h1>
    <img
      class="new-result-btn"
      @click="shareKakao"
      src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png"
    />

    <!-- 보유 여부 : 얘 filter 걸다가 오류 나서 추후 수정 예정-->
    <div>
      <div>checkfilter: {{ checkedGot }}</div>

      <input type="checkbox" id="true" value="true" v-model="checkedGot" />
      <label for="true">보유</label>

      <input type="checkbox" id="false" value="false" v-model="checkedGot" />
      <label for="false">미보유</label>
    </div>

    <!-- 타입별 -->
    <div>
      <div>checkfilter: {{ checkedType }}</div>

      <input type="checkbox" id="DOKCHO" value="DOKCHO" v-model="checkedType" />
      <label for="DOKCHO">독초😈</label>

      <input type="checkbox" id="YAKCHO" value="YAKCHO" v-model="checkedType" />
      <label for="YAKCHO">약초🥗</label>

      <input type="checkbox" id="JAPCHO" value="JAPCHO" v-model="checkedType" />
      <label for="JAPCHO">잡초🌻</label>

      <input type="checkbox" id="HIDDEN" value="HIDDEN" v-model="checkedType" />
      <label for="HIDDEN">히든✨</label>
    </div>

    <!-- 등급별 -->
    <div>
      <div>checkfilter: {{ checkedGrade }}</div>

      <input
        type="checkbox"
        id="COMMON"
        value="COMMON"
        v-model="checkedGrade"
      />
      <label for="COMMON">COMMON</label>

      <input type="checkbox" id="RARE" value="RARE" v-model="checkedGrade" />
      <label for="RARE">RARE</label>

      <input type="checkbox" id="EPIC" value="EPIC" v-model="checkedGrade" />
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
    <div>{{ filteredMonsters.length }} / 99</div>
    <div v-for="monster in filteredMonsters" :key="monster.monsterId">
      <MonsterCard :monster="monster" />
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
      checkedGot: [],
      isLoading: true
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
            title: '웹으로 보기',
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
    }, 1500)
  }
}
</script>

<style scoped>
.dogam__title {
  text-align: center;
  margin: 1vh;
}
.new-result-btn {
  height: 42px;
  border-radius: 10px;
  border: 2px solid #636366;
}
</style>
