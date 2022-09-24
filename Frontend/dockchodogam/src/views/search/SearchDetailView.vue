<template>
  <NavBar @overflow="overflow" />
  <div class="searchDetail">
    <div class="resultBox">
      <div class="plantText">
        <h1>{{ this.data.name }}</h1>
        <p>{{ this.data.engNm }}</p>
      </div>
      <div class="plantInfo">
        <img :src="this.data.imgUrl" alt="" class="plantImage" />
        <div class="plantDetail__container">
          <div class="plantDetail">
            <p v-if="this.data.familyKorNm">
              과 : {{ this.data.familyKorNm }} / {{ this.data.familyNm }}
            </p>
            <p v-if="this.data.genusKorNm">
              속 : {{ this.data.genusKorNm }} / {{ this.data.genusNm }}
            </p>
            <p v-if="this.data.dstrb">분포지 : {{ this.data.dstrb }}</p>
            <p v-if="this.data.flwrDesc">꽃 정보 : {{ this.data.flwrDesc }}</p>
            <p v-if="this.data.leafDesc">잎 정보 : {{ this.data.leafDesc }}</p>
            <p v-if="this.data.stemDesc">
              줄기 정보 : {{ this.data.stemDesc }}
            </p>
            <p v-if="this.data.fritDesc">
              열매 정보 : {{ this.data.fritDesc }}
            </p>
            <p v-if="this.data.rootDesc">
              뿌리 정보 : {{ this.data.rootDesc }}
            </p>
            <p>출처 : {{ this.data.cprtCtnt }}</p>
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
      showMenu: false,
      queryData: this.$route.query.query,
      data: {}
    }
  },
  methods: {
    overflow(value) {
      this.showMenu = value
    },
    searchDetailResult() {
      const option = {
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      }
      axios
        .get(BASE_URL + '/api/v1/dokcho/detail/' + this.queryData, option)
        .then((res) => {
          this.data = res.data.data
        })
        .catch((err) => console.log(err))
    }
  },
  created() {
    this.searchDetailResult()
  }
}
</script>

<style scoped>
.searchDetail {
  width: 100vw;
  display: flex;
  justify-content: center;
}
.resultBox {
  border-radius: 20px;
  width: 80vw;
  height: 85vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 5vh;
  background-image: url('@/assets/hanji.jpeg');
  background-repeat: no-repeat;
  background-size: cover;
}
.plantText {
  text-align: center;
  margin-top: 5vh;
}
.plantInfo {
  height: 60vh;
  width: 70vw;
  display: flex;
  justify-content: space-between;
}
.plantImage {
  border-radius: 10px;
  height: 50vh;
  width: 30vw;
  margin: 2.5vh;
}
.plantDetail__container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #ececec;
  border-width: 5px;
  border-radius: 40px;
  margin: 2.5vh;
  width: 40vw;
  height: 50vh;
}
.plantDetail {
  overflow: auto;
  width: 33vw;
  height: 40vh;
  padding: 2vh 1vw;
}
.plantDetail > p {
  font-size: 15px;
}
@media screen and (max-width: 850px) {
  .resultBox {
    height: 85vh;
  }
  .plantInfo {
    flex-direction: column;
    align-items: center;
    height: 65vh;
  }
  .plantImage {
    width: 50vw;
    height: 20vh;
  }
  .plantDetail__container {
    width: 60vw;
    height: 40vh;
  }
  .plantDetail {
    height: 30vh;
    width: 50vw;
    padding: 0 1vw;
  }
}
</style>
