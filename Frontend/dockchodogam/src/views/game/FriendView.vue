<template>
  <div class="friendPage">
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
          <FriendSearch />
        </TabItem>
        <TabItem title="우편함" class="friendGift__tap">
          <FriendGift />
        </TabItem>
      </TabWrapper>
      <div class="friend__exit" @click="goToArenaMain()">
        <font-awesome-icon icon="fa-solid fa-x" size="xl" />
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
      friendPage: 0,
      requestPage: 0
    }
  },
  methods: {
    goToArenaMain() {
      this.$router.push({ path: '/game/arena' })
    },
    giveGiftAll() {
      axios
        .put(BASE_URL + '/api/v1/user/friend/gift/all', {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          console.log(res.data)
        })
        .catch((err) => console.log(err))
    },
    getFriendList() {
      axios
        .get(BASE_URL + '/api/v1/user/friend/' + this.friendPage, {
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
        .get(BASE_URL + '/api/v1/user/friend/request/' + this.requestPage, {
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
}
.friendRequest__top > p {
  font-weight: bold;
  font-size: 2vw;
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
  width: 30px;
  height: 30px;
  border: 2px groove black;
  border-radius: 2px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 6vh;
  right: 6vw;
  cursor: pointer;
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
    width: 10px;
  }
}
</style>
