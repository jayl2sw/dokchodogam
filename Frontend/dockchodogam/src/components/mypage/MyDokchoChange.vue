<template>
  <div>
    <div
      class="blur"
      :class="this.showChangeDokcho ? 'isShowBlur' : ''"
      @click="closeChangeDokcho()"
    ></div>
    <div class="myDokchoChange" :class="this.showChangeDokcho ? 'isShow' : ''">
      <div class="mainBox">
        <div class="myDokchos">
          <div
            class="myDokchoItem"
            v-for="(item, i) in this.myDokcho"
            :key="i"
            :class="this.selectDokcho === item.monsterId ? 'checked' : ''"
            @click="this.onClickDokcho(item.monsterId)"
          >
            <img
              :src="this.imageBaseUrl + '/' + item.monsterId + '.png'"
              alt=""
              class="myDokchoItemImage"
            />
          </div>
        </div>
        <div class="changeBtnBox">
          <div class="closeBtn" @click="closeChangeDokcho()">취소</div>
          <div class="changeBtn" @click="onClickChange()">변경</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import { mapActions } from 'vuex'

export default {
  data() {
    return {
      myDokcho: [],
      showChangeDokcho: false,
      selectDokcho: '',
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  props: {
    showChangeDokchoMenu: {
      type: Boolean
    }
  },
  methods: {
    ...mapActions(['fetchUserInfo']),
    closeChangeDokcho() {
      this.showChangeDokcho = false
      this.$emit('closeChangeDokcho', this.showChangeDokcho)
    },
    onClickDokcho(i) {
      this.selectDokcho = i
      console.log('독초 선택', this.selectDokcho)
    },
    onClickChange() {
      const option = {
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      }
      if (this.selectDokcho !== '') {
        axios
          .put(
            BASE_URL + '/api/v1/user/monster/' + this.selectDokcho,
            { monster_id: this.selectDokcho },
            option
          )
          .then((res) => {
            axios.get(BASE_URL + '/api/v1/user/myinfo', option).then((res) => {
              this.fetchUserInfo(res.data)
              this.closeChangeDokcho()
            })
          })
          .catch((err) => console.log(err))
      }
    }
  },
  watch: {
    showChangeDokchoMenu() {
      if (this.showChangeDokchoMenu === true) {
        this.showChangeDokcho = true
      }
    }
  },
  created() {
    axios
      .get(BASE_URL + '/api/v1/game/monster/list?size=100', {
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      })
      .then((res) => {
        res.data.content.forEach((element) => {
          if (element.got) {
            this.myDokcho.push(element)
          }
        })
        console.log(this.myDokcho)
      })
      .catch((err) => console.log(err))
  }
}
</script>

<style scoped>
.myDokchoChange {
  width: 100vw;
  height: 70vh;
  position: fixed;
  bottom: -70vh;
  left: 0;
  z-index: 9999;
  overflow: auto;
  transition: all 1s;
}
.mainBox {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 70vh;
  width: 100vw;
  border-top-right-radius: 3vh;
  border-top-left-radius: 3vh;
  background-color: #a7c957;
}
.myDokchos {
  width: 90vw;
  height: 45vh;
  overflow-y: auto;
  margin: 10vh 10vw 5vh 10vw;
}
.myDokchoItem {
  width: 12vw;
  height: 12vw;
  border-radius: 15px;
  background-color: #ececec;
  margin: 2.7vw;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.myDokchoItemImage {
  width: 9vw;
  height: 9vw;
}
.changeBtnBox {
  width: 90vw;
  height: 10vh;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.closeBtn {
  width: 10vw;
  height: 5vh;
  border-radius: 5vh;
  background-color: #ececec;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  margin-right: 5vw;
}
.changeBtn {
  width: 10vw;
  height: 5vh;
  border-radius: 5vh;
  background-color: #ececec;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.blur {
  background-color: rgba(0, 0, 0, 0.4);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 9998;
  display: none;
  cursor: pointer;
}
.checked {
  border: 4px groove #467302;
}
@media screen and (max-width: 850px) {
  .myDokchoChange {
    height: 100vh;
    bottom: -100vh;
  }
  .mainBox {
    height: 100vh;
    border-top-right-radius: 0;
    border-top-left-radius: 0;
  }
  .myDokchos {
    height: 70vh;
  }
  .myDokchoItem {
    width: 20vw;
    height: 20vw;
    margin: 5vw;
  }
  .myDokchoItemImage {
    width: 18vw;
    height: 18vw;
  }
  .closeBtn {
    width: 20vw;
  }
  .changeBtn {
    width: 20vw;
  }
}
.isShow {
  bottom: 0;
}
.isShowBlur {
  display: block;
}
</style>
