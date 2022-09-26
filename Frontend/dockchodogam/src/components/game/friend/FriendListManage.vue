<template>
  <div class="lists">
    <div class="left">
      <img src="@/assets/loading/1.png" alt="" />
      <p class="TITLE name">{{ this.friend.nickname }}</p>
    </div>
    <div class="right">
      <font-awesome-icon
        icon="fa-solid fa-trash"
        @click="this.deleteFriend()"
      />
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'

export default {
  props: ['friend'],
  methods: {
    deleteFriend() {
      axios
        .delete(BASE_URL + '/api/v1/user/friend/' + this.friend.user_id, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          console.log(res.data)
          this.$emit('getFriendList')
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
  width: 100%;
  height: 10vh;
  margin: 2vh 0;
  background-color: white;
  display: flex;
  justify-content: space-between;
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
  width: 2vw;
  height: 10vh;
  margin-right: 3vw;
  transition: 0.3s;
}
svg:hover {
  color: #a7c957;
}
</style>
