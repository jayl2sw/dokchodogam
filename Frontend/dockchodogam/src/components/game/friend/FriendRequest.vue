<template>
  <div class="lists">
    <div class="left">
      <img src="@/assets/loading/1.png" alt="" />
      <p class="TITLE name">{{ this.request.sender_nickname }}</p>
    </div>
    <div class="right">
      <font-awesome-icon
        icon="fa-solid fa-ban"
        @click="this.refuseFriend()"
        class="icon"
      />
      <font-awesome-icon
        icon="fa-solid fa-heart"
        @click="this.acceptFriend()"
        class="icon"
      />
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'

export default {
  props: ['request', 'getRequestList'],
  data() {
    return {
      btn_audio: new Audio(process.env.VUE_APP_S3_URL + '/button.mp3')
    }
  },
  methods: {
    acceptFriend() {
      this.btn_audio.play()
      axios
        .put(BASE_URL + '/api/v1/user/friend/accept', this.request.propose_id, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken'),
            'Content-type': 'application/json'
          }
        })
        .then((res) => {
          console.log(res.data)
          this.$emit('getRequestList')
          this.$emit('getFriendList')
        })
        .catch((err) => console.log(err))
    },
    refuseFriend() {
      this.btn_audio.play()
      axios
        .delete(
          BASE_URL + '/api/v1/user/friend/refuse/' + this.request.propose_id,
          {
            headers: {
              AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
            }
          }
        )
        .then((res) => {
          console.log(res.data)
          this.$emit('getRequestList')
        })
        .catch((err) => console.log(err))
    }
  }
}
</script>

<style scoped>
.lists {
  border: none;
  border-radius: 20px;
  width: 90%;
  margin: 2vh auto;
  background-color: white;
  display: flex;
  justify-content: space-between;
}
.left {
  display: flex;
  width: 70%;
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
  font-size: 1.5vw;
  font-weight: bold;
  line-height: 11vh;
  margin-left: 0.5vw;
  margin-bottom: 0;
}
.right {
  width: 30%;
}
svg {
  color: pink;
  width: 2vw;
  height: 100%;
  margin-right: 1vw;
  transition: 0.3s;
  float: right;
}
svg:hover {
  color: red;
}
.icon {
  cursor: pointer;
}
</style>
