<template>
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
              <FriendListManage :friend="friend" class="friendManageList" />
            </div>
          </div>
          <div class="friendGift">
            <div class="friendGift__top">
              <p class="TITLE">NEW 친구 신청</p>
            </div>
            <div class="friendGift__bottom">
              <div v-for="(gift, i) in this.giftList" :key="i">
                <FriendGift :gift="gift" />
              </div>
            </div>
          </div>
        </div>
      </TabItem>
      <TabItem title="친구 추가" class="friendSearch__tap">
        <FriendSearch />
      </TabItem>
      <TabItem title="우편함" class="friendRequest__tap">
        <FriendRequest />
      </TabItem>
    </TabWrapper>
    <div class="friend__exit" @click="goToArenaMain()">
      <font-awesome-icon icon="fa-solid fa-x" size="xl" />
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
      friendList: [1, 2, 1, 1, 1, 1, 1, 1],
      giftList: [1, 2, 1, 1, 1, 1, 1, 1]
    }
  },
  methods: {
    goToArenaMain() {
      this.$router.push({ path: '/game/arena' })
    }
  }
}
</script>

<style scoped>
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
.friendGift {
  border: none;
  border-radius: 30px;
  height: 100%;
  width: 45%;
  background-color: white;
}
.friendGift__top {
  height: 15%;
  width: 100%;
  margin-top: 1vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.friendGift__top > p {
  font-weight: bold;
  font-size: 2vw;
}
.friendGift__bottom {
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
.friendRequest__tap {
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
