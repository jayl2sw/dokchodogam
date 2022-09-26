<template>
  <div class="card">
    <div
      :class="{
        card__common: newMonster.grade == 'common',
        card__rare: newMonster.grade == 'rare',
        card__epic: newMonster.grade == 'epic',
        card__legendary: newMonster.grade == 'legendary',
        card__special: newMonster.grade == 'special'
      }"
    >
      <div>
        <img src="@/assets/loading/3.png" class="card__img" />
        <p>{{ newMonster.name }}</p>
      </div>
    </div>
  </div>
  <div>
    <div>
      <button @click="goToDogam">도감에서 확인하기</button>
      <button @click="gachaAgain">한번 더 뽑기</button>
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
      newMonster: {},
      userInfo: JSON.parse(localStorage.getItem('userInfo'))
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    fetchMonsterGacha() {
      axios({
        url: `${BASE_URL} + api/v1/game/monster/pick`,
        method: 'POST',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        },
        data: {
          itemId: 3
        }
      })
        .then((res) => (this.newMonster = res))
        .catch((err) => console.log(err))
    },
    goToDogam() {
      this.$router.push({
        path: '/encyclopedia'
      })
    },
    gachaAgain() {
      if (confirm('200냥을 내고 뽑기를 한번 더 진행하시겠습니까?') === true) {
        if (this.userInfo.money >= 200) {
          this.$router.push({
            path: '/game/shop/gacha'
          })
        } else {
          swal({
            title: '보유하신 냥이 부족합니다 😢',
            icon: 'error',
            buttons: false,
            timer: 1500
          })
          this.$router.push({
            path: '/game/shop'
          })
        }
      } else {
        return false
      }
    }
  },
  created() {
    this.fetchMonsterGacha()
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
