<template>
  <div class="friendGifts">
    <button class="all__button" @click="this.receiptGiftAll()">
      한 번에 받기
    </button>
    <div class="lists" v-for="(gift, i) in this.giftList" :key="i">
      <div class="left">
        <img
          :src="this.imageBaseUrl + '/' + gift.profile_img + '.png'"
          alt=""
        />
        <p class="TITLE name">{{ gift.nickname }} 님이 선물을 보냈습니다!</p>
      </div>
      <div class="right">
        <font-awesome-icon
          icon="fa-solid fa-hand-holding-heart"
          class="icon"
          @click="this.receiptGift(gift.friend_id)"
        />
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
      giftList: [],
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      btn_audio: new Audio(process.env.VUE_APP_S3_URL + '/button.mp3')
    }
  },
  methods: {
    receiptGift(friendId) {
      this.btn_audio.play()
      axios
        .put(BASE_URL + '/api/v1/user/friend/receipt', friendId, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken'),
            'Content-type': 'application/json'
          }
        })
        .then((res) => {
          console.log(res.data)
          this.getGiftList()
        })
        .catch((err) => console.log(err))
    },
    receiptGiftAll() {
      this.btn_audio.play()
      axios
        .put(BASE_URL + '/api/v1/user/friend/receipt/all', null, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          console.log(res.data)
          this.getGiftList()
        })
        .catch((err) => console.log(err))
    },
    getGiftList() {
      axios
        .get(BASE_URL + '/api/v1/user/friend/gift', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          console.log('선물', res.data)
          this.giftList = res.data
        })
        .catch((err) => console.log(err))
    }
  },
  created() {
    this.getGiftList()
  }
}
</script>

<style>
.friendGifts {
  width: 100%;
  height: 100%;
  overflow: auto;
}
.all__button {
  width: 10vw;
  height: 5vh;
  float: right;
  margin-bottom: 4vh;
  border: none;
  background-color: white;
  border-radius: 20px;
  font-weight: bold;
  font-size: 1.5vw;
  transition: 0.3s;
}
.all__button:hover {
  color: #467302;
}
.lists {
  border: none;
  border-radius: 30px;
  width: 100%;
  height: 12vh;
  margin: 2vh 0;
  background-color: white;
  display: flex;
  justify-content: space-between;
}
.left {
  display: flex;
  width: 80%;
}
.left > img {
  width: 8vh;
  height: 8vh;
  object-fit: contain;
  margin: 2vh;
  background-color: #eee;
  border-radius: 50%;
}
.name {
  font-size: 2.5vh;
  font-weight: bold;
  line-height: 11vh;
  margin-left: 0.5vw;
  margin-bottom: 0;
}
.right {
  height: 100%;
}
svg.icon {
  color: #467302;
  width: 4vw;
  height: 100% !important;
  margin-right: 3vw;
  transition: 0.3s;
  cursor: pointer;
}
svg:hover {
  color: #a7c957;
}
::-webkit-scrollbar {
  display: none;
}
</style>
