<template>
  <div class="lists">
    <div class="left">
      <img
        :src="this.imageBaseUrl + '/' + this.friend.profile_img + '.png'"
        alt=""
      />
      <p class="TITLE name">{{ this.friend.nickname }}</p>
    </div>
    <div class="right">
      <font-awesome-icon
        icon="fa-solid fa-gift"
        @click="this.giveGift()"
        :class="this.friend.gift_today ? 'sentGift' : ''"
      />
      <font-awesome-icon icon="fa-solid fa-hand-fist" ref="button" />
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'

export default {
  props: ['friend'],
  data() {
    return {
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  methods: {
    giveGift() {
      axios
        .put(BASE_URL + '/api/v1/user/friend/gift', this.friend.user_id, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken'),
            'Content-type': 'application/json'
          }
        })
        .then((res) => {
          console.log(res.data)
          this.$refs.button.$el.classList.add('sentGift')
        })
        .catch((err) => console.log(err))
    }
  }
}
</script>

<style scoped>
.lists {
  background-color: white;
  border: none;
  border-radius: 20px;
  width: 70vw;
  height: 15vh;
  margin: 2vh 0;
  display: flex;
  justify-content: space-between;
}
.left {
  display: flex;
}
.left > img {
  width: 11vh;
  height: 11vh;
  object-fit: contain;
  margin: 2vh;
  background-color: #eee;
  border-radius: 50%;
}
.name {
  font-size: 4vh;
  font-weight: bold;
  line-height: 14vh;
  margin-left: 1vw;
}
.sentGift {
  color: #ececec;
  pointer-events: none;
}
svg {
  color: #467302;
  width: 3vw;
  height: 14vh;
  margin-right: 3vw;
  transition: 0.3s;
}
svg:hover {
  color: #a7c957;
}
</style>
