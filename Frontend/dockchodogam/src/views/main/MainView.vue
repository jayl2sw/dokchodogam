<template>
  <NavBar @overflow="overflow" />
  <div class="main" :class="this.showMenu ? 'open-menu' : ''">
    <div class="nickname">
      <h3 class="TITLE">{{ this.userInfo.nickname }} 님, 안녕하세요!</h3>
    </div>
    <div class="main__search">
      <input
        type="search"
        v-model="inputData"
        @keyup.enter="goToSearch"
        placeholder="궁금한 식물을 찾아보세요!"
      />
    </div>
    <div class="main__bottom">
      <div class="main__left">
        <div class="myDockcho">
          <div class="dockchoName">
            <h3 class="TITLE">
              같이 <a class="TITLE easter" href="/intro">여행</a>을 떠날 풀깨비
            </h3>
          </div>
          <img
            :src="
              require('@/assets/monster/' + this.userInfo.profile_img + '.png')
            "
            alt=""
            class="dockchoImg"
            @click="this.playAudio(this.userInfo.profile_img)"
          />
        </div>
      </div>
      <div class="main__right">
        <div class="todayPlant__container">
          <div class="todayPlant">
            <h2 class="TITLE">
              <font-awesome-icon
                icon="fa-solid fa-seedling"
              />&nbsp;&nbsp;오늘의 식물&nbsp;
              <font-awesome-icon icon="fa-solid fa-seedling" />
            </h2>
            <h4>{{ this.datas.name }}</h4>
            <h6>{{ this.datas.lang }}</h6>
            <div
              id="carouselExampleIndicators"
              class="carousel slide"
              data-bs-ride="true"
            >
              <div class="carousel-indicators">
                <button
                  type="button"
                  data-bs-target="#carouselExampleIndicators"
                  data-bs-slide-to="0"
                  class="active"
                  aria-current="true"
                  aria-label="Slide 1"
                ></button>
                <button
                  type="button"
                  data-bs-target="#carouselExampleIndicators"
                  data-bs-slide-to="1"
                  aria-label="Slide 2"
                ></button>
                <button
                  type="button"
                  data-bs-target="#carouselExampleIndicators"
                  data-bs-slide-to="2"
                  aria-label="Slide 3"
                ></button>
              </div>
              <div class="carousel-inner 300-px-wide">
                <div class="carousel-item active">
                  <img :src="this.datas.img1" class="d-block w-100" alt="..." />
                </div>
                <div class="carousel-item">
                  <img :src="this.datas.img2" class="d-block w-100" alt="..." />
                </div>
                <div class="carousel-item">
                  <img :src="this.datas.img3" class="d-block w-100" alt="..." />
                </div>
              </div>
              <button
                class="carousel-control-prev"
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide="prev"
              >
                <span
                  class="carousel-control-prev-icon"
                  aria-hidden="true"
                ></span>
                <span class="visually-hidden">Previous</span>
              </button>
              <button
                class="carousel-control-next"
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide="next"
              >
                <span
                  class="carousel-control-next-icon"
                  aria-hidden="true"
                ></span>
                <span class="visually-hidden">Next</span>
              </button>
            </div>
            <p class="carousel__content">{{ this.datas.content }}</p>
            <i>출처 : 농촌진흥청 국립원예특작과학원</i>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'
import { BASE_URL } from '@/constant/BASE_URL'
import axios from 'axios'

export default {
  components: {
    NavBar
  },
  data() {
    return {
      queryParams: process.env.VUE_APP_TODAYPLANT_API_KEY + '&dataNo=1',
      datas: {},
      showMenu: false,
      inputData: '',
      userInfo: JSON.parse(localStorage.getItem('userInfo')),
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  methods: {
    goToSearch() {
      this.$router.push({
        path: '/search',
        query: { query: this.inputData }
      })
    },
    overflow(value) {
      this.showMenu = value
    },
    getTodayPlant() {
      axios
        .get(BASE_URL + '/api/v1/dokcho/today', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          this.datas = res.data
        })
        .catch((err) => console.log(err))
    },
    playAudio(id) {
      new Audio(this.imageBaseUrl + '/' + id + '.m4a').play()
    }
  },
  created() {
    this.getTodayPlant()
  }
}
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: column;
}
.nickname {
  text-align: center;
  margin: 4vh 0 2vh;
}
.main__bottom {
  display: flex;
  justify-content: space-evenly;
}
.myDockcho {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  border-width: 5px;
  border-radius: 40px;
  width: 30vw;
  height: 60vh;
  background-color: #467302;
}
.dockchoName h3 {
  font-weight: bold;
  color: #fff;
  word-break: keep-all;
}
.easter {
  text-decoration: none;
  cursor: text;
  color: white;
}
.dockchoImg {
  border-radius: 5px;
  width: 20vw;
  height: 20vw;
  margin-top: 2vw;
  cursor: pointer;
}
.main__left {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  text-align: center;
}
.main__right {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 68vh;
}
.todayPlant__container {
  position: relative;
  background-color: #ececec;
  border-width: 5px;
  border-radius: 40px;
  width: 40vw;
  height: 60vh;
  opacity: 0.8;
  transition: 0.3s;
}
.todayPlant__container:hover {
  opacity: 1;
}
.todayPlant {
  position: absolute;
  top: 3vh;
  width: 40vw;
  height: 54vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: auto;
  padding: 2vh 3vw 0;
  scroll-margin-bottom: 10vh;
}
svg {
  color: #467302;
}
.todayPlant h2 {
  font-weight: bold;
  margin: 1.5vh 0 2.5vh;
}
.todayPlant h6 {
  font-style: italic;
  color: gray;
  margin-bottom: 2vh;
}
.carousel {
  width: 75%;
  height: auto;
}
.carousel__content {
  margin-top: 2rem;
}
.main__search {
  display: flex;
  justify-content: center;
}
input {
  display: block;
  width: 70vw;
  height: 7vh;
  margin: 8px 0 8px 0;
  padding: 10px 15px 10px 55px;
  background: #ececec url('@/assets/search-icon.png') no-repeat 20px center;
  background-size: 20px 20px;
  font-size: 16px;
  border: #ececec solid 2px;
  border-radius: 20px;
}
input::placeholder {
  color: #467302;
}
input:focus {
  outline: none;
  border: #467302 solid 2px;
}
@media screen and (max-width: 850px) {
  .main {
    align-items: center;
    flex-direction: column;
  }
  .nickname {
    text-align: center;
    margin: 3vh 0 1vh;
  }
  .main__bottom {
    align-content: center;
    flex-direction: column;
  }
  .myDockcho {
    width: 70vw;
    height: 70vw;
    margin: 4vw 0;
    padding: 3vh 0;
  }
  .myDockcho h3 {
    font-size: 4vw;
  }
  .dockchoImg {
    width: 50vw;
    height: 50vw;
  }
  .main__right {
    height: auto;
    margin-top: 2vh;
  }
  .todayPlant__container {
    width: 70vw;
    height: 50vh;
    padding-bottom: 3vh;
    opacity: 1;
  }
  .todayPlant {
    width: 70vw;
    height: 45vh;
    font-size: 2vw;
  }
  .todayPlant h2 {
    font-size: 4vw;
  }
  .todayPlant h4 {
    font-size: 4vw;
  }
  .todayPlant h6 {
    font-size: 2vw;
    font-style: italic;
    color: gray;
  }
  input {
    width: 70vw;
    height: 5vh;
    font-size: 1.5vh;
    background-size: 3vw 3vw;
    padding-left: 10vw;
  }
  .open-menu {
    overflow: hidden;
    width: 100%;
    height: 100%;
    position: fixed;
  }
}
</style>
