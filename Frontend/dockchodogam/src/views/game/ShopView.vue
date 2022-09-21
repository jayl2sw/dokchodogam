<template>
  <div class="shop">
    <div class="shop__boxes">
      <div class="shop__top">
        <div class="nickname">
          <h1>username</h1>
        </div>
        <div class="money">
          <h1>100냥</h1>
        </div>
      </div>
      <div class="shop__bottom">
        <div class="shop__item">
          <payment-cash />
        </div>
        <div class="shop__item">
          <payment-package />
        </div>
        <div class="shop__item">
          <monster-gacha />
        </div>
      </div>
    </div>
    <div class="shop__exit" @click="goToArenaMain()">
      <font-awesome-icon icon="fa-solid fa-x" size="xl" />
    </div>
  </div>
</template>

<script>
import PaymentCash from '@/components/game/shop/PaymentCash.vue'
import PaymentPackage from '@/components/game/shop/PaymentPackage.vue'
import MonsterGacha from '@/components/game/shop/MonsterGacha.vue'
import MonsterCard from '@/components/encyclopedia/MonsterCard.vue'

export default {
  components: {
    PaymentCash,
    PaymentPackage,
    MonsterGacha
  },
  methods: {
    goToArenaMain() {
      this.$router.push({ path: '/game/arena' })
    }
  }
}
</script>

<style scoped>
.shop {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 10vh 0;
}
.shop__boxes {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.shop__top {
  display: flex;
  justify-content: space-around;
  height: 20vh;
}
.shop__bottom {
  display: flex;
  justify-content: space-around;
}
.shop__item {
  width: 30vw;
  height: 60vh;
  border: 2px groove black;
  border-radius: 10px;
}
.shop__exit {
  width: 30px;
  height: 30px;
  border: 2px groove black;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 20px;
  right: 30px;
  cursor: pointer;
}
@media screen and (max-width: 850px) {
  .game__exit {
    top: 10px;
    right: 10px;
    width: 25px;
    height: 25px;
  }
  svg {
    width: 10px;
  }
}
</style>
