<template>
  <div class="inGame" v-if="!this.isGameEndFlag">
    <div class="inGame__top">
      <div class="yourDockChoList">
        <div
          v-for="(yourDockCho, i) in this.yourDockChoList"
          :key="i"
          class="yourDockChoItem"
        >
          <div
            class="deadBlurBox"
            :class="isDead_yourDockCho[i] ? 'deadDockcho' : ''"
          ></div>
        </div>
      </div>
    </div>
    <div class="inGame__middle">
      <div class="currentMyDockCho">
        <div
          class="myDockChoMon"
          :class="
            this.isAttack
              ? 'myDockcho_attack'
              : this.currentMyDockCho
              ? 'myDockCho_inGround'
              : ''
          "
        >
          <DockChoMon :data="this.currentMyDockCho" :damage="this.yourDamage" />
        </div>
      </div>
      <div class="currentYourDockCho">
        <div
          class="yourDockChoMon"
          :class="
            this.isAttack
              ? 'yourDockcho_attack'
              : this.currentYourDockCho
              ? 'yourDockCho_inGround'
              : ''
          "
        >
          <DockChoMon :data="this.currentYourDockCho" :damage="this.myDamage" />
        </div>
      </div>
    </div>
    <div class="inGame__bottom">
      <div class="myDockChoList">
        <div
          v-for="(myDockCho, i) in this.myDockChoList"
          :key="i"
          class="myDockChoItem"
          :class="isMyDockchoDead ? 'ableSelect' : ''"
          @click="this.selectNextDockcho(i)"
        >
          <div
            class="deadBlurBox"
            :class="isDead_myDockCho[i] ? 'deadDockcho' : ''"
          ></div>
        </div>
      </div>
      <div class="skillBtn" @click="onClickSkill()">
        <div
          class="skillBlur"
          :class="this.isUseSkill ? 'usedSkill' : ''"
        ></div>
      </div>
    </div>
  </div>
  <div v-else>
    <ArenaGameResult
      :myDeck="this.myDockChoList"
      :resultInfo="this.resultInfo"
    />
  </div>
</template>

<script>
import DockChoMon from '@/components/game/arena/DockChoMon.vue'
import ArenaGameResult from '@/components/game/arena/ArenaGameResult.vue'
import _ from 'lodash'

export default {
  components: {
    DockChoMon,
    ArenaGameResult
  },
  data() {
    return {
      round: 1,
      isAttack: false,
      isDead_myDockCho: [false, false, false, false, false],
      isDead_yourDockCho: [false, false, false, false, false],
      myDockChoList: [
        { name: 1, minAtt: 5, maxAtt: 10, hp: 100, currentHp: 100 },
        { name: 1, minAtt: 5, maxAtt: 10, hp: 100, currentHp: 100 },
        { name: 1, minAtt: 5, maxAtt: 10, hp: 100, currentHp: 100 },
        { name: 1, minAtt: 5, maxAtt: 10, hp: 100, currentHp: 100 },
        { name: 1, minAtt: 5, maxAtt: 10, hp: 100, currentHp: 100 }
      ],
      yourDockChoList: [
        { name: 2, minAtt: 5, maxAtt: 50, hp: 11, currentHp: 11 },
        { name: 2, minAtt: 5, maxAtt: 50, hp: 11, currentHp: 11 },
        { name: 2, minAtt: 5, maxAtt: 50, hp: 11, currentHp: 11 },
        { name: 2, minAtt: 5, maxAtt: 50, hp: 11, currentHp: 11 },
        { name: 2, minAtt: 5, maxAtt: 50, hp: 11, currentHp: 11 }
      ],
      currentMyDockCho: '',
      currentYourDockCho: '',
      currentMyIdx: 0,
      currentYourIdx: 0,
      myDamage: '',
      yourDamage: '',
      isMyDockchoDead: false,
      isGameEndFlag: false,
      resultInfo: [],
      skill: '',
      nowUseSkill: false,
      isUseSkill: false
    }
  },
  methods: {
    gameStart() {
      setTimeout(() => {
        this.currentMyDockCho = this.myDockChoList[0]
        this.currentYourDockCho = this.yourDockChoList[0]
        console.log('지금 나의 스킬은?', this.skill)
        setTimeout(() => {
          this.attack()
        }, 2000)
      }, 1000)
    },
    attack() {
      this.isAttack = true
      setTimeout(() => {
        this.isAttack = false
        this.myDamage = _.random(
          this.currentMyDockCho.minAtt,
          this.currentMyDockCho.maxAtt
        )
        this.yourDamage = _.random(
          this.currentYourDockCho.minAtt,
          this.currentYourDockCho.maxAtt
        )
        if (this.nowUseSkill) {
          if (this.skill === 1) {
            console.log('데미지 두배임', this.myDamage, this.myDamage * 2)
            this.myDamage *= 2
          } else if (this.skill === 2) {
            console.log('상대 공격 무효')
            this.yourDamage = 0
          } else {
            console.log('상대 공격 반사')
            this.myDamage = this.yourDamage
            this.yourDamage = 0
          }
          this.nowUseSkill = false
          this.isUseSkill = true
        }
        this.judged()
      }, 300)
    },
    judged() {
      this.currentMyDockCho.currentHp -= this.yourDamage
      this.currentYourDockCho.currentHp -= this.myDamage
      console.log(this.myDockChoList, this.currentMyDockCho)
      console.log(this.yourDockChoList, this.currentYourDockCho)
      setTimeout(() => {
        this.myDamage = ''
        this.yourDamage = ''
        if (
          this.currentMyDockCho.currentHp <= 0 &&
          this.currentYourDockCho.currentHp <= 0
        ) {
          this.isDead_myDockCho[this.currentMyIdx] = true
          this.isDead_yourDockCho[this.currentYourIdx] = true
          this.currentMyIdx = this.isDead_myDockCho.indexOf(false)
          this.currentYourIdx = this.isDead_yourDockCho.indexOf(false)
          this.currentMyDockCho = ''
          this.currentYourDockCho = ''
          this.isMyDockchoDead = true
          console.log('둘다죽음')
          setTimeout(() => {
            this.isGameEnd()
          }, 2000)
        } else if (this.currentMyDockCho.currentHp <= 0) {
          this.isDead_myDockCho[this.currentMyIdx] = true
          this.currentMyIdx = this.isDead_myDockCho.indexOf(false)
          this.currentMyDockCho = ''
          this.isMyDockchoDead = true
          console.log('나 죽음')
          setTimeout(() => {
            this.isGameEnd()
          }, 2000)
        } else if (this.currentYourDockCho.currentHp <= 0) {
          this.isDead_yourDockCho[this.currentYourIdx] = true
          this.currentYourIdx = this.isDead_yourDockCho.indexOf(false)
          this.currentYourDockCho = ''
          console.log('상대 죽음')
          setTimeout(() => {
            this.isGameEnd()
          }, 2000)
        } else {
          setTimeout(() => {
            this.attack()
          }, 500)
        }
      }, 1000)
    },
    isGameEnd() {
      console.log('내 인덱스', this.currentMyIdx)
      console.log('상대 인덱스', this.currentYourIdx)
      console.log('내 독초몬', this.currentMyDockCho)
      console.log('상대 독초몬', this.currentYourDockCho)
      if (this.currentMyIdx === -1 && this.currentYourIdx === -1) {
        console.log('상대 승리')
        setTimeout(() => {
          this.isGameEndFlag = true
          this.resultInfo = [0, '패배']
        }, 1000)
      } else if (this.currentMyIdx === -1) {
        console.log('상대 승리')
        setTimeout(() => {
          this.isGameEndFlag = true
          this.resultInfo = [0, '패배']
        }, 1000)
      } else if (this.currentYourIdx === -1) {
        console.log('나 승리')
        setTimeout(() => {
          this.isGameEndFlag = true
          this.resultInfo = [0, '승리!']
        }, 1000)
      } else if (this.currentMyDockCho && this.currentYourDockCho === '') {
        this.currentYourDockCho = this.yourDockChoList[this.currentYourIdx]
        setTimeout(() => {
          this.attack()
        }, 2000)
      }
    },
    selectNextDockcho(idx) {
      if (this.isMyDockchoDead && !this.isDead_myDockCho[idx]) {
        this.currentMyIdx = idx
        this.currentMyDockCho = this.myDockChoList[this.currentMyIdx]
        this.currentYourDockCho = this.yourDockChoList[this.currentYourIdx]
        setTimeout(() => {
          this.attack()
        }, 2000)
      }
    },
    onClickSkill() {
      if (!this.isUseSkill) {
        this.nowUseSkill = true
      }
    }
  },
  mounted() {
    this.gameStart()
    this.skill = _.random(1, 3)
  }
}
</script>

<style scoped>
.inGame {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.inGame__top {
  display: flex;
  justify-content: flex-end;
  margin: 3vh 5vw;
}
.yourDockChoList {
  display: flex;
}
.yourDockChoItem {
  border: 2px groove black;
  border-radius: 5px;
  width: 12vh;
  height: 12vh;
  margin: 1vh;
}
.inGame__middle {
  display: flex;
}
.currentMyDockCho {
  width: 50vw;
  height: 55vh;
}
.myDockChoMon {
  border: 2px groove black;
  width: 20vw;
  height: 25vw;
  position: fixed;
  top: 20vh;
  left: -20vw;
  transition: all 1s;
}
.currentYourDockCho {
  width: 50vw;
  height: 55vh;
}
.yourDockChoMon {
  border: 2px groove black;
  width: 20vw;
  height: 25vw;
  position: fixed;
  top: 20vh;
  right: -20vw;
  transition: all 1s;
}
.inGame__bottom {
  margin: 3vh 5vw;
  display: flex;
  justify-content: space-between;
}
.myDockChoList {
  display: flex;
}
.myDockChoItem {
  border: 2px groove black;
  border-radius: 5px;
  width: 17vh;
  height: 17vh;
  margin: 1vh;
}
.deadBlurBox {
  background-color: rgba(0, 0, 0, 0.4);
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 9999;
  display: none;
}
.skillBtn {
  border: 2px groove black;
  border-radius: 5px;
  width: 17vh;
  height: 17vh;
  margin: 1vh;
  cursor: pointer;
}
.skillBlur {
  background-color: rgba(0, 0, 0, 0.4);
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 9999;
  display: none;
}
.myDockCho_inGround {
  left: 20vw;
  transition: all 1s;
}
.yourDockCho_inGround {
  right: 20vw;
  transition: all 1s;
}
.myDockcho_attack {
  left: 30vw;
  transition: all 0.3s;
  transition-timing-function: linear;
}
.yourDockcho_attack {
  right: 30vw;
  transition: all 0.3s;
  transition-timing-function: linear;
}
.ableSelect {
  cursor: pointer;
}
.deadDockcho {
  display: block;
  cursor: default;
}
.usedSkill {
  display: block;
  cursor: default;
}
/* @media screen and (min-height: 700px) {
  .myDockChoMon {
    top: 30vh;
  }
  .yourDockChoMon {
    top: 30vh;
  }
} */
</style>
