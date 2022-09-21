<template>
  <div class="friend">
    <b-card no-body>
      <b-tabs card>
        <b-tab no-body title="친구 목록" class="friendList__tap">
          <div v-for="(friend, i) in this.friendList" :key="i">
            <FriendListItem :friend="friend" />
          </div>
        </b-tab>

        <b-tab no-body title="친구 관리">
          <div class="friendManage__tap">
            <div class="friendManage">
              <div v-for="(friend, i) in this.friendList" :key="i">
                <FriendListManage :friend="friend" />
              </div>
            </div>
            <div class="friendGift">
              <div class="friendGift__top">
                <p>NEW 친구 신청</p>
              </div>
              <div class="friendGift__bottom">
                <div v-for="(gift, i) in this.giftList" :key="i">
                  <FriendGift :gift="gift" />
                </div>
              </div>
            </div>
          </div>
        </b-tab>

        <b-tab no-body title="친구 추가" class="friendSearch__tap">
          <FriendSearch />
        </b-tab>

        <b-tab title="우편함" class="friendRequest__tap">
          <FriendRequest />
        </b-tab>
      </b-tabs>
    </b-card>
    <div class="friend__exit" @click="goToArenaMain()">
      <font-awesome-icon icon="fa-solid fa-x" size="xl" />
    </div>
  </div>
</template>

<script>
import FriendListItem from '@/components/game/friend/FriendListItem.vue'
import FriendListManage from '@/components/game/friend/FriendListManage.vue'
import FriendGift from '@/components/game/friend/FriendGift.vue'
import FriendSearch from '@/components/game/friend/FriendSearch.vue'
import FriendRequest from '@/components/game/friend/FriendRequest.vue'

export default {
  components: {
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
  display: flex;
}
.friendManage {
  height: 70vh;
  width: 37.5vw;
  margin: 0 5vw 0 0;
  overflow: auto;
}
.friendGift {
  border: 2px groove black;
  border-radius: 5px;
  height: 70vh;
  width: 37.5vw;
}
.friendGift__top {
  height: 10vh;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.friendGift__bottom {
  border: 2px groove black;
  border-radius: 5px;
  height: 50vh;
  width: 32.5vw;
  margin: 5vh 2.5vw;
  overflow: auto;
}
.friendSearch__tap {
  padding: 5vh 5vw;
  height: 80vh;
}
.friendRequest__tap {
  padding: 5vh 5vw;
  height: 80vh;
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
