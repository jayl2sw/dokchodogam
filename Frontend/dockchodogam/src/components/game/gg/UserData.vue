<template>
  <div class="userData">
    <div class="search">
      <input
        type="search"
        v-model="inputData"
        @keyup.enter="this.search()"
        placeholder="유저 아이디를 검색해주세요"
      />
    </div>
    <div v-if="this.searchUser.nickname">
      <BattleLog :searchUser="this.searchUser" />
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import BattleLog from './BattleLog.vue'

export default {
  components: {
    BattleLog
  },
  data() {
    return {
      inputData: '',
      searchUser: JSON.parse(localStorage.getItem('userInfo'))
    }
  },
  methods: {
    search() {
      const option = {
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      }
      axios
        .get(BASE_URL + '/api/v1/user/search/' + this.inputData, option)
        .then((res) => {
          console.log(res.data)
          this.searchUser = res.data
        })
        .catch((err) => {
          console.log(err)
          this.searchUser = '*'
        })
    }
  },
  watch: {
    inputData() {
      this.searchUser = ''
    }
  }
}
</script>

<style scoped>
.userData {
  display: flex;
  flex-direction: column;
}
.search {
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
  /* box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px,
    rgba(0, 0, 0, 0.3) 0px 1px 3px -1px; */
}
input::placeholder {
  color: #467302;
}
input:focus {
  outline: none;
  border: #467302 solid 2px;
}
</style>
