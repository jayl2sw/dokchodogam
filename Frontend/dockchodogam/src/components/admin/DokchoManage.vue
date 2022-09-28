<template>
  <div>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">name</th>
          <th scope="col">maxAttack</th>
          <th scope="col">minAttack</th>
          <th scope="col">hp</th>
          <th scope="col">type</th>
          <th scope="col">grade</th>
          <th scope="col">line</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(dokcho, i) in dokchoList" :key="i">
          <th scope="row">{{ i + 1 }}</th>
          <td>{{ dokcho.name }}</td>
          <td>{{ dokcho.maxAttack }}</td>
          <td>{{ dokcho.minAttack }}</td>
          <td>{{ dokcho.hp }}</td>
          <td>{{ dokcho.type }}</td>
          <td>{{ dokcho.grade }}</td>
          <td>{{ dokcho.line }}</td>
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
      dokchoList: [],
      page: 0
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
          BASE_URL + '/api/v1/game/monster/list?size=10&page=' + this.page,
          option
        )
        .then((res) => {
          if (res.data.content.length) {
            this.dokchoList = this.dokchoList.concat(res.data.content)
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
    }
  }
}
</script>

<style scoped>
.block {
  margin: 5vh;
}
</style>
