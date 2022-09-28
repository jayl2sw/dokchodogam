<template>
  <div>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">username</th>
          <th scope="col">nickname</th>
          <th scope="col">created_at</th>
          <th scope="col">rank_point</th>
          <th scope="col">money</th>
          <th scope="col">fix</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(user, i) in userData" :key="i">
          <th scope="row">{{ i + 1 }}</th>
          <td>{{ user.username }}</td>
          <td>{{ user.nickname }}</td>
          <td>{{ user.created_at }}</td>
          <td>{{ user.rank_point }}</td>
          <td>{{ user.money }}</td>
          <td>
            <button @click="this.userDelete(user.user_id)">delete</button>
          </td>
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
      userData: [],
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
        .get(BASE_URL + '/api/v1/admin/user/list/' + this.page, option)
        .then((res) => {
          if (res.data.length) {
            this.userData = this.userData.concat(res.data)
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
    userDelete(userId) {
      const option = {
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      }
      axios
        .delete(BASE_URL + '/api/v1/admin/user/' + userId, option)
        .then(() => alert('삭제완료'))
        .catch((err) => console.log(err))
    }
  }
}
</script>

<style scoped>
.block {
  margin: 5vh;
}
</style>
