<template>
  <NavBar @overflow="overflow" />
  <div class="search">
    <div class="search__text">
      <h1 class="TITLE">"{{ this.inputData }}" 의 검색 결과</h1>
    </div>
    <div class="search__result">
      <div class="row row-cols-2 row-cols-md-3 g-4">
        <div
          v-for="(searchResult, i) in this.searchResults"
          :key="i"
          class="col"
          @click="onClickDetail(searchResult.plantId)"
        >
          <SearchCard :result="searchResult" />
        </div>
      </div>
    </div>
    <InfiniteLoading
      @infinite="load"
      :slots="{
        complete: '더 이상 검색 결과가 없어요..',
        error: '무언가 잘못되었어요!'
      }"
    />
    <div class="block"></div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'
import SearchCard from '@/components/search/SearchCard.vue'
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import InfiniteLoading from 'v3-infinite-loading'
import 'v3-infinite-loading/lib/style.css'

export default {
  components: {
    NavBar,
    SearchCard,
    InfiniteLoading
  },
  data() {
    return {
      showMenu: false,
      inputData: this.$route.query.query,
      searchResults: [],
      page: 0
    }
  },
  methods: {
    overflow(value) {
      this.showMenu = value
    },
    load($state) {
      const queryParams = '/' + this.inputData + '/' + this.page + '/12'
      const option = {
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      }
      axios
        .get(BASE_URL + '/api/v1/dokcho/search' + queryParams, option)
        .then((res) => {
          if (res.data.data.length) {
            this.searchResults = this.searchResults.concat(res.data.data)
            this.page += 1
            $state.loaded()
          } else {
            $state.complete()
          }
        })
        .catch((err) => {
          console.log(err)
          $state.error()
        })
    },
    onClickDetail(num) {
      this.$router.push({
        path: '/search/detail',
        query: { query: num }
      })
    }
  }
}
</script>

<style scoped>
.search {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 8vh 10vw 0 10vw;
  overflow: auto;
  position: absolute;
  top: 0;
  width: 100vw;
  height: 100vh;
  z-index: -9999;
  background-image: url('@/assets/hanji.jpeg');
  background-repeat: no-repeat;
  background-size: cover;
}
.search__text {
  margin: 5vh 0;
}
.search__result {
  width: 100%;
}
.col {
  display: flex;
  justify-content: center;
  cursor: pointer;
}
.block {
  margin: 1vh;
}
</style>
