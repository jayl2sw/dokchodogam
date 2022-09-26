<template>
  <div
    v-for="monster in packageMonsters"
    :key="monster.id"
    :class="{
      card__common: newMonster.grade == 'common',
      card__rare: newMonster.grade == 'rare',
      card__epic: newMonster.grade == 'epic',
      card__legendary: newMonster.grade == 'legendary',
      card__special: newMonster.grade == 'special'
    }"
  >
    <v-card elevation="1" outlined>
      <p>00{{ monster.monsterId }}</p>
      <h3>{{ monster.name }}몬</h3>
      <img
        :src="this.imageBaseUrl + '/' + monster.monsterId + '.png'"
        class="card__img"
      />
    </v-card>
  </div>
  <div>
    <button @click="goToDogam">도감에서 확인하기</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      packageMonsters: this.$route.params
    }
  },
  methods: {
    goToDogam() {
      this.$router.push({
        path: '/encyclopedia'
      })
    }
  }
}
</script>

<style scoped>
.card {
  display: flex;
  width: 20vw;
  height: 50vh;
}
.card__img {
  width: 100%;
  height: 0%;
}
.card__common {
  background-color: gray;
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
  background-color: rosybrown;
}
</style>
