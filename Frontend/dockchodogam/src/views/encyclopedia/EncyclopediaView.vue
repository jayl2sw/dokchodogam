<template>
  <NavBar @overflow="overflow" />
  <div>
    <h1 class="dogam__title">나의 도감</h1>
    <div>
      <div>checkfilter: {{ checkedType }}</div>

      <input type="checkbox" id="dokcho" value="dokcho" v-model="checkedType" />
      <label for="dokcho">dokcho</label>

      <input type="checkbox" id="yakcho" value="yakcho" v-model="checkedType" />
      <label for="yakcho">yakcho</label>

      <input type="checkbox" id="jobcho" value="jobcho" v-model="checkedType" />
      <label for="jobcho">jobcho</label>

      <input type="checkbox" id="hidden" value="hidden" v-model="checkedType" />
      <label for="hidden">hidden</label>
    </div>
    <div>
      <div>checkfilter: {{ checkedGrade }}</div>

      <input
        type="checkbox"
        id="common"
        value="common"
        v-model="checkedGrade"
      />
      <label for="common">common</label>

      <input type="checkbox" id="rare" value="rare" v-model="checkedGrade" />
      <label for="rare">rare</label>

      <input type="checkbox" id="epic" value="epic" v-model="checkedGrade" />
      <label for="epic">epic</label>

      <input
        type="checkbox"
        id="legendary"
        value="legendary"
        v-model="checkedGrade"
      />
      <label for="legendary">legendary</label>

      <input
        type="checkbox"
        id="special"
        value="special"
        v-model="checkedGrade"
      />
      <label for="special">special</label>
    </div>
    <div>
      <MonsterCard
        v-for="monster in filteredMonsters"
        :key="monster.monsterId"
      />
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'
import MonsterCard from '@/components/encyclopedia/MonsterCard.vue'
import axios from 'axios'
// import { mapGetters } from 'vuex'

export default {
  components: {
    NavBar,
    MonsterCard
  },
  data() {
    return {
      monsters: [],
      checkedType: [],
      checkedGrade: []
    }
  },
  computed: {
    filteredMonsters() {
      // 체크된 것 아무것도 없을 경우
      // 타입 X 등급 X
      if (!this.checkedType.length && !this.checkedGrade.length) {
        return this.monsters
      } else if (this.checkedType.length) {
        // 타입 O 등급 X
        if (!this.checkedGrade.length) {
          return this.monsters.filter((monster) =>
            this.checkedType.includes(monster.type)
          )
        }

        // 타입 O 등급 O
        return this.monsters.filter(
          (monster) =>
            this.checkedType.includes(monster.type) &&
            this.checkedGrade.includes(monster.grade)
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
        url: 'http://localhost:8081/api/v1/game/monster/list',
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      })
        .then((res) => {
          console.log(res)
          this.monsters = res.data
        })
        .catch((err) => console.log(err))
    }
  },
  created() {
    this.fetchMonsterList()
  }
}
</script>

<style scoped>
.dogam__title {
  text-align: center;
  margin: 1vh;
}
</style>
