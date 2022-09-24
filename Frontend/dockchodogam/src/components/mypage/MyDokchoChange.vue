<template>
  <div
    class="blur"
    :class="this.showChangeDokcho ? 'isShowBlur' : ''"
    @click="closeChangeDokcho()"
  ></div>
  <div class="myDokchoChange" :class="this.showChangeDokcho ? 'isShow' : ''">
    <div class="closeBtnBox">
      <div class="closeBtn" @click="closeChangeDokcho()"></div>
    </div>
    <div class="mainBox">
      <div class="myDokchos">
        <div
          class="myDokchoItem"
          v-for="(item, i) in this.datas"
          :key="i"
        ></div>
      </div>
      <div class="changeBtnBox">
        <div class="changeBtn">변경</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      datas: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16],
      showChangeDokcho: false
    }
  },
  props: {
    showChangeDokchoMenu: {
      type: Boolean
    }
  },
  methods: {
    closeChangeDokcho() {
      this.showChangeDokcho = false
      this.$emit('closeChangeDokcho', this.showChangeDokcho)
    }
  },
  watch: {
    showChangeDokchoMenu() {
      if (this.showChangeDokchoMenu === true) {
        this.showChangeDokcho = true
      }
    }
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
.closeBtnBox {
  display: flex;
  justify-content: center;
  height: 5vh;
  width: 100vw;
}
.closeBtn {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 5vh;
  width: 10vw;
  cursor: pointer;
  border-top-right-radius: 3vh;
  border-top-left-radius: 3vh;
  background-color: #a7c957;
}
.mainBox {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 65vh;
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
  width: 10vw;
  height: 10vw;
  border: 2px groove black;
  border-radius: 10px;
  margin: 3.7vw;
  display: inline-block;
}
.changeBtnBox {
  width: 90vw;
  height: 10vh;
  display: flex;
  justify-content: flex-end;
  align-items: center;
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
@media screen and (max-width: 850px) {
  .myDokchoChange {
    height: 100vh;
    bottom: -100vh;
  }
  .closeBtnBox {
    background-color: #a7c957;
  }
  .closeBtn {
    border-top-right-radius: 0;
    border-top-left-radius: 0;
  }
  .mainBox {
    height: 95vh;
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
