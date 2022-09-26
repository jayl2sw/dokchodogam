<template>
  <div class="search">
    <div class="friend__search">
      <input
        type="search"
        v-model="inputData"
        @keyup.enter="search()"
        placeholder="아이디를 통해 친구를 찾아보세요!"
      />
    </div>
    <div class="search__result">
      <div v-for="(user, i) in this.searchUsers" :key="i" class="result__item">
        <div class="left">
          <img src="@/assets/loading/1.png" alt="" />
          <p class="TITLE name">{{ user.nickname }}</p>
        </div>
        <div class="right">
          <font-awesome-icon
            icon="fa-solid fa-clover"
            @click="this.requestFriend(user.user_id)"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'

export default {
  data() {
    return {
      inputData: '',
      searchUsers: [],
      searchPage: 0
    }
  },
  methods: {
    requestFriend(userId) {
      axios
        .put(BASE_URL + '/api/v1/user/friend/request', userId, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken'),
            'Content-type': 'application/json'
          }
        })
        .then((res) => {
          console.log(res.data)
        })
        .catch((err) => console.log(err))
    },
    search() {
      axios
        .get(
          BASE_URL +
            '/api/v1/user/search/' +
            this.inputData +
            '/' +
            this.searchPage,
          {
            headers: {
              AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
            }
          }
        )
        .then((res) => {
          this.searchUsers = res.data
        })
        .catch((err) => console.log(err))
    }
  }
}
</script>

<style scoped>
.search {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
}
.friend__search {
  height: 10vh;
  margin: 5vh 5vw;
  display: flex;
  justify-content: center;
}
.search__result {
  height: 45vh;
  margin: 0 5vw 5vh 5vw;
  overflow: auto;
}
.result__item {
  border: none;
  border-radius: 30px;
  width: 100%;
  height: 10vh;
  margin: 2vh auto;
  display: flex;
  justify-content: space-between;
  background-color: white;
}
input {
  display: block;
  width: 100%;
  height: 7vh;
  margin: 8px 0 8px 0;
  padding: 10px 15px 10px 55px;
  background: #ececec url('@/assets/search-icon.png') no-repeat 20px center;
  background-size: 20px 20px;
  font-size: 20px;
  border: #ececec solid 2px;
  border-radius: 20px;
}
input::placeholder {
  color: #467302;
}
input:focus {
  outline: none;
  border: #a7c957 solid 2px;
}
.left {
  display: flex;
}
.left > img {
  width: 6vh;
  height: 6vh;
  object-fit: contain;
  margin: 2vh;
  background-color: #eee;
  border-radius: 50%;
}
.name {
  font-size: 2.5vh;
  font-weight: bold;
  line-height: 10vh;
  margin-left: 0.5vw;
}
svg {
  color: #467302;
  width: 2.5vw;
  height: 10vh;
  margin-right: 3vw;
  transition: 0.3s;
}
svg:hover {
  color: #a7c957;
}
::-webkit-scrollbar {
  display: none;
}
</style>
