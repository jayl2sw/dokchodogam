<template>
  <div class="introCard">
    <div class="introCard__top">
      <p class="TITLE">허준의 제자,</p>
      <p class="TITLE">
        {{ this.nickname }} 님을 도와 줄 풀깨비들이 찾아왔어요!
      </p>
    </div>
    <div class="introCard__middle">
      <div class="dockchoCard" v-for="(dockcho, i) in this.dockchos" :key="i">
        <div class="imageBG">
          <img
            :src="require('@/assets/intro-card/' + (i + 1) + '.png')"
            alt=""
            class="card__image"
            :class="this.isChecked ? '' : 'dark'"
          />
        </div>
        <p class="TITLE" :class="this.isChecked ? '' : 'hidden'">
          {{ dockcho }}
        </p>
      </div>
    </div>
    <div class="introCard__bottom">
      <div
        class="checkBtn"
        :class="this.isChecked ? 'none' : ''"
        @click="onClickBtn()"
      >
        눌러서 확인하기
      </div>
      <div
        class="checkBtn"
        :class="this.isChecked ? '' : 'none'"
        @click="goToMain()"
      >
        메인 페이지로
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions } from 'vuex'
import { BASE_URL } from '@/constant/BASE_URL'

export default {
  data() {
    return {
      dockchos: ['개나리몬', '해바라기몬', '도꼬마리몬', '석류몬', '박하몬'],
      isChecked: false,
      nickname: JSON.parse(localStorage.getItem('userInfo')).nickname
    }
  },
  methods: {
    ...mapActions(['fetchUserInfo']),
    onClickBtn() {
      this.isChecked = !this.isChecked
      const option = {
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      }
      axios
        .put(BASE_URL + '/api/v1/user/newbie', null, option)
        .then(() => {
          axios
            .get(BASE_URL + '/api/v1/user/myinfo', option)
            .then((res) => {
              this.fetchUserInfo(res.data)
            })
            .catch((err) => console.log(err))
        })
        .catch((err) => console.log(err))
    },
    goToMain() {
      this.$router.push({ path: '/main' })
    }
  }
}
</script>

<style scoped>
.introCard {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url('@/assets/hanji.jpeg');
}
.introCard__top {
  width: 80vw;
  height: 10vh;
  text-align: center;
  margin-top: 5vh;
}
.introCard__top > p {
  font-size: 20px;
  margin: 0;
}
.introCard__middle {
  display: flex;
  justify-content: space-between;
  height: 30vw;
  width: 80vw;
  margin-top: 5vh;
}
.dockchoCard {
  border-radius: 15px;
  background-color: #a7c957;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 25vw;
  width: 15vw;
}
.imageBG {
  border-radius: 10px;
  height: 12vw;
  width: 12vw;
  margin: 2.5vw;
  margin-bottom: 5vw;
  background-color: #ececec;
}
.card__image {
  height: 12vw;
  width: 12vw;
  transition: 1s all;
}
.dockchoCard > p {
  margin: 0;
  font-size: 15px;
}
.introCard__bottom {
  width: 80vw;
  display: flex;
  justify-content: center;
}
.checkBtn {
  border-radius: 20px;
  width: 30vw;
  height: 7vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #a7c957;
  transition: 0.3s;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}
.checkBtn:hover {
  background-color: #467302;
  color: white;
}
.none {
  display: none;
}
.hidden {
  opacity: 0;
}
.dark {
  -webkit-filter: grayscale(100%) brightness(0);
  filter: grayscale(100%) brightness(0);
}
@media screen and (max-width: 850px) {
  .introCard__top > p {
    font-size: 15px;
  }
  .introCard__middle {
    flex-wrap: wrap;
    justify-content: space-evenly;
    height: 70vw;
    width: 70vw;
    margin: 0;
  }
  .dockchoCard {
    margin-top: 5vw;
    height: 30vw;
    width: 20vw;
  }
  .imageBG {
    height: 16vw;
    width: 16vw;
  }
  .card__image {
    height: 16vw;
    width: 16vw;
  }
  .introCard__bottom {
    margin-top: 5vh;
  }
}
</style>
