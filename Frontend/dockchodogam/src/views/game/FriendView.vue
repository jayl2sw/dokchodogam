<template>
  <div class="friendPage">
    <div id="warning-message">
      <p class="TITLE">
        아레나는<br /><br /><span class="emphasize">"가로 화면 전용"</span>
        게임입니다.
      </p>
    </div>
    <div class="friend">
      <TabWrapper>
        <TabItem title="친구 목록" class="friendList__tap">
          <div v-for="(friend, i) in this.friendList" :key="i">
            <FriendListItem :friend="friend" />
          </div>
        </TabItem>
        <TabItem title="친구 관리">
          <div class="friendManage__tap">
            <div class="friendManage">
              <div v-for="(friend, i) in this.friendList" :key="i">
                <FriendListManage
                  :friend="friend"
                  class="friendManageList"
                  @getFriendList="getFriendList()"
                />
              </div>
            </div>
            <div class="friendRequest">
              <div class="friendRequest__top">
                <p class="TITLE">NEW 친구 신청</p>
                <font-awesome-icon
                  icon="fa-solid fa-repeat"
                  class="resetRequest"
                  @click="this.getRequestList()"
                />
              </div>
              <div class="friendRequest__bottom">
                <div v-for="(request, i) in this.requestList" :key="i">
                  <FriendRequest
                    :request="request"
                    @getRequestList="getRequestList()"
                    @getFriendList="getFriendList()"
                  />
                </div>
              </div>
            </div>
          </div>
        </TabItem>
        <TabItem title="친구 추가" class="friendSearch__tap">
          <FriendSearch @getRequestList="getRequestList()" />
        </TabItem>
        <TabItem title="우편함" class="friendGift__tap">
          <FriendGift />
        </TabItem>
      </TabWrapper>
      <div class="friend__exit" @click="goToArenaMain()">
        <font-awesome-icon icon="fa-solid fa-circle-xmark" />
      </div>
    </div>
  </div>
</template>

<script>
import TabWrapper from '@/components/game/friend/TabWrapper.vue'
import TabItem from '@/components/game/friend/TabItem.vue'
import FriendListItem from '@/components/game/friend/FriendListItem.vue'
import FriendListManage from '@/components/game/friend/FriendListManage.vue'
import FriendGift from '@/components/game/friend/FriendGift.vue'
import FriendSearch from '@/components/game/friend/FriendSearch.vue'
import FriendRequest from '@/components/game/friend/FriendRequest.vue'
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'

export default {
  components: {
    TabWrapper,
    TabItem,
    FriendListItem,
    FriendListManage,
    FriendGift,
    FriendSearch,
    FriendRequest
  },
  data() {
    return {
      friendList: [],
      requestList: [],
      audio: new Audio(process.env.VUE_APP_S3_URL + '/friend.wav'),
      btn_audio: new Audio(process.env.VUE_APP_S3_URL + '/button.mp3')
    }
  },
  methods: {
    goToArenaMain() {
      this.btn_audio.play()
      this.$router.push({ path: '/game/arena' })
    },
    giveGiftAll() {
      axios
        .put(BASE_URL + '/api/v1/user/friend/gift/all', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .catch((err) => console.log(err))
    },
    getFriendList() {
      axios
        .get(BASE_URL + '/api/v1/user/friend/', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          this.friendList = res.data
        })
        .catch((err) => console.log(err))
    },
    getRequestList() {
      axios
        .get(BASE_URL + '/api/v1/user/friend/request', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          this.requestList = res.data
        })
        .catch((err) => console.log(err))
    }
  },
  created() {
    this.getFriendList()
    this.getRequestList()
  },
  mounted() {
    this.audio.loop = true
    this.audio.volume = 0.2
    this.audio.play()
  },
  beforeUnmount() {
    this.audio.pause()
  }
}
</script>

<style scoped>
.friendPage {
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-image: url('@/assets/game_background.png');
  background-repeat: no-repeat;
  background-size: cover;
}
.friend {
  margin: 5vh 5vw;
  height: 90vh;
}
.friendList__tap {
  padding: 5vh 5vw;
  height: 80vh;
  overflow: auto;
  display: flex;
  flex-direction: column;
}
.friendManage__tap {
  padding: 5vh 5vw;
  height: 80vh;
  width: 100%;
  display: flex;
  justify-content: space-between;
}
.friendManage {
  height: 70vh;
  width: 45%;
  margin: 0 5vw 0 0;
  overflow: auto;
}
.friendManageList:first-child {
  margin-top: 0;
}
.friendRequest {
  border: none;
  border-radius: 30px;
  height: 100%;
  width: 45%;
  background-color: white;
}
.friendRequest__top {
  height: 15%;
  width: 100%;
  margin-top: 1vh;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}
.friendRequest__top > p {
  font-weight: bold;
  font-size: 2vw;
}
.resetRequest {
  position: absolute;
  right: 3vw;
  top: 2vh;
  width: 3vw;
  color: #467302;
  cursor: pointer;
}
.resetRequest:hover {
  color: #a7c957;
}
.friendRequest__bottom {
  border: none;
  border-radius: 30px;
  background-color: #a7c957;
  height: 80%;
  width: 28vw;
  margin: 0 auto;
  overflow: auto;
}
.friendSearch__tap {
  padding: 5vh 5vw;
  height: 80vh;
}
.friendGift__tap {
  padding: 5vh 5vw;
  height: 80vh;
  width: 100%;
}
.friend__exit {
  width: 4vw;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 6vh;
  right: 11vw;
  cursor: pointer;
}
svg {
  width: 5vh;
  height: 5vh;
  color: #ffe140;
  transition: 0.3s;
}
svg:hover {
  color: #ffef92;
}
::-webkit-scrollbar {
  display: none;
}
@media screen and (max-width: 850px) {
  .friend__exit {
    top: 7vh;
    right: 7vw;
    width: 25px;
    height: 25px;
  }
  svg {
    width: 20px;
  }
}
@media only screen and (orientation: portrait) {
  .friendPage {
    background-image: none;
    background-color: white;
    height: 100vh;
  }
  .friend {
    display: none;
  }
  #warning-message {
    display: block;
    font-size: 5vw;
    text-align: center;
  }
  .emphasize {
    font-family: 'UhBeeSe_hyun';
    font-size: 6vw;
    font-weight: bold;
    color: #467302;
  }
}
@media only screen and (orientation: landscape) {
  #warning-message {
    display: none;
  }
}
</style>
