<template>
  <div>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">plantId</th>
          <th scope="col">name</th>
          <th scope="col">engNm</th>
          <th scope="col">familyKorNm</th>
          <th scope="col">genusKorNm</th>
          <th scope="col">plantSpecsScnm</th>
          <th scope="col">imgUrl</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(plant, i) in plantList" :key="i">
          <th scope="row">{{ i + 1 }}</th>
          <td>{{ plant.plantId }}</td>
          <td>{{ plant.name }}</td>
          <td>{{ plant.engNm }}</td>
          <td>{{ plant.familyKorNm }}</td>
          <td>{{ plant.genusKorNm }}</td>
          <td>{{ plant.plantSpecsScnm }}</td>
          <td>{{ plant.imgUrl }}</td>
        </tr>
      </tbody>
    </table>
    <InfiniteLoading @infinite="load" />
    <div class="block"></div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import InfiniteLoading from 'v3-infinite-loading'
import 'v3-infinite-loading/lib/style.css'

export default {
  components: {
    InfiniteLoading
  },
  data() {
    return {
      plantList: [],
      plantPage: 0
    }
  },
  methods: {
    load($state) {
      const option = {
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      }
      axios
        .get(
          BASE_URL + '/api/v1/dokcho/list/{page}?page=' + this.plantPage,
          option
        )
        .then((res) => {
          if (res.data.data.length) {
            this.plantList = this.plantList.concat(res.data.data)
            this.plantPage += 1
            $state.loaded()
          } else {
            $state.complete()
          }
        })
        .catch((err) => {
          console.log(err)
          $state.error()
        })
    }
  }
}
</script>

<style scoped>
.block {
  margin: 5vh;
}
</style>
