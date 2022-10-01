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
      <div class="result__item" v-if="this.searchUser.nickname">
        <div class="left">
          <img
            :src="
              this.imageBaseUrl + '/' + this.searchUser.profile_img + '.png'
            "
            alt=""
          />
          <p class="TITLE name">{{ searchUser.nickname }}</p>
        </div>
        <div class="right">
          <font-awesome-icon
            icon="fa-solid fa-clover"
            @click="this.requestFriend(searchUser.user_id)"
            class="icon"
          />
        </div>
      </div>
      <div v-if="this.searchUser === '*'" class="noResult TITLE">
        유저 정보가 존재하지 않습니다.
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
      searchUser: '',
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      btn_audio: new Audio(process.env.VUE_APP_S3_URL + '/button.mp3')
    }
  },
  methods: {
    requestFriend(userId) {
      this.btn_audio.play()
      axios
        .put(BASE_URL + '/api/v1/user/friend/request', userId, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken'),
            'Content-type': 'application/json'
          }
        })
        .then((res) => {
          console.log(res.data)
          this.$emit('getRequestList')
        })
        .catch((err) => console.log(err))
    },
    search() {
      axios
        .get(BASE_URL + '/api/v1/user/search/' + this.inputData, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          if (
            res.data.nickname !==
            JSON.parse(localStorage.getItem('userInfo')).nickname
          ) {
            this.searchUser = res.data
          }
        })
        .catch((err) => {
          console.log(err)
          this.searchUser = '*'
        })
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
.icon {
  cursor: pointer;
}
.noResult {
  font-size: 4vh;
  text-align: center;
  color: white;
}
::-webkit-scrollbar {
  display: none;
}
</style>
