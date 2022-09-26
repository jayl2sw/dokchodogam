<template>
  <LoadingPage v-if="this.isLoading" />
  <div v-show="!this.isLoading">
    <div class="inGame" v-if="!this.isGameEndFlag">
      <div class="inGame__top">
        <div class="yourDockChoList">
          <div
            v-for="(yourDockCho, i) in this.yourDockChoList"
            :key="i"
            class="yourDockChoItem"
          >
            <img
              :src="this.imageBaseUrl + '/' + yourDockCho.monsterId + '.png'"
              alt=""
              class="yourDockChoItemImage"
            />
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
            <DockChoMon
              :data="this.currentMyDockCho"
              :damage="this.yourDamage"
            />
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
            <DockChoMon
              :data="this.currentYourDockCho"
              :damage="this.myDamage"
            />
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
            <img
              :src="this.imageBaseUrl + '/' + myDockCho.monsterId + '.png'"
              alt=""
              class="myDockChoItemImage"
            />
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
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import DockChoMon from '@/components/game/arena/DockChoMon.vue'
import ArenaGameResult from '@/components/game/arena/ArenaGameResult.vue'
import LoadingPage from '@/components/main/LoadingPage.vue'
import _ from 'lodash'

export default {
  components: {
    DockChoMon,
    ArenaGameResult,
    LoadingPage
  },
  data() {
    return {
      isLoading: true,
      round: 1,
      isAttack: false,
      isDead_myDockCho: [false, false, false, false, false],
      isDead_yourDockCho: [false, false, false, false, false],
      myDockChoList: [],
      yourDockChoList: [],
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
      isUseSkill: false,
      audio: new Audio(process.env.VUE_APP_S3_URL + '/game.mp3'),
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  methods: {
    ...mapActions(['fetchUserDeck', 'fetchEnemyInfo']),
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
      if (this.enemyInfo.nickname) {
        var audio = new Audio(process.env.VUE_APP_S3_URL + '/attack.mp3')
        audio.volume = 0.8
        audio.play()
        this.isAttack = true
        setTimeout(() => {
          this.isAttack = false
          this.myDamage = _.random(
            this.currentMyDockCho.minAttack,
            this.currentMyDockCho.maxAttack
          )
          this.yourDamage = _.random(
            this.currentYourDockCho.minAttack,
            this.currentYourDockCho.maxAttack
          )
          // 상성 체크
          if (this.currentMyDockCho.type === 'JAPCHO') {
            if (this.currentYourDockCho.type === 'DOKCHO') {
              console.log(
                '잡 독 쎄게맞음!',
                this.yourDamage,
                this.yourDamage * 1.2
              )
              this.yourDamage *= 1.2
            } else if (this.currentYourDockCho.type === 'YAKCHO') {
              console.log('잡 약 쎄게떄림!', this.myDamage, this.myDamage * 1.2)
              this.myDamage *= 1.2
            } else if (this.currentYourDockCho.type === 'HIDDEN') {
              console.log(
                '잡 히 쎄게맞음!',
                this.yourDamage,
                this.yourDamage * 1.2
              )
              this.yourDamage *= 1.2
            }
          } else if (this.currentMyDockCho.type === 'DOKCHO') {
            if (this.currentYourDockCho.type === 'JAPCHO') {
              console.log('독 잡 쎄게때림!', this.myDamage, this.myDamage * 1.2)
              this.myDamage *= 1.2
            } else if (this.currentYourDockCho.type === 'YAKCHO') {
              console.log(
                '독 약 쎄게맞음!',
                this.yourDamage,
                this.yourDamage * 1.2
              )
              this.yourDamage *= 1.2
            } else if (this.currentYourDockCho.type === 'HIDDEN') {
              console.log(
                '독 히 쎄게맞음!',
                this.yourDamage,
                this.yourDamage * 1.2
              )
              this.yourDamage *= 1.2
            }
          } else if (this.currentMyDockCho.type === 'YAKCHO') {
            if (this.currentYourDockCho.type === 'DOKCHO') {
              console.log('약 독 쎄게때림!', this.myDamage, this.myDamage * 1.2)
              this.myDamage *= 1.2
            } else if (this.currentYourDockCho.type === 'JAPCHO') {
              console.log(
                '약 잡 쎄게맞음!',
                this.yourDamage,
                this.yourDamage * 1.2
              )
              this.yourDamage *= 1.2
            } else if (this.currentYourDockCho.type === 'HIDDEN') {
              console.log(
                '약 히 쎄게맞음!',
                this.yourDamage,
                this.yourDamage * 1.2
              )
              this.yourDamage *= 1.2
            }
          } else {
            console.log('히 쎄게때림!', this.myDamage, this.myDamage * 1.2)
            this.myDamage *= 1.2
            if (this.currentYourDockCho.type === 'HIDDEN') {
              console.log(
                '히 히 쎄게맞음!',
                this.yourDamage,
                this.yourDamage * 1.2
              )
              this.yourDamage *= 1.2
            }
          }
          this.myDamage = Math.round(this.myDamage)
          this.yourDamage = Math.round(this.yourDamage)
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
      }
    },
    judged() {
      this.currentMyDockCho.currentHp -= this.yourDamage
      this.currentYourDockCho.currentHp -= this.myDamage
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
      var audio = new Audio(process.env.VUE_APP_S3_URL + '/card_select.mp3')
      audio.volume = 1
      audio.play()
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
      var audio = new Audio(
        process.env.VUE_APP_S3_URL + '/skill' + this.skill + '.mp3'
      )
      audio.play()
      if (!this.isUseSkill) {
        this.nowUseSkill = true
      }
    }
  },
  created() {
    this.fetchUserDeck()
    if (!this.enemyInfo.nickname) {
      this.$router.push({ path: '/game/arena' })
    }
    setTimeout(() => {
      this.isLoading = false
      this.myDockChoList = this.userDeck
      this.yourDockChoList = this.enemyInfo.enemyDeck
      if (this.enemyInfo.nickname) {
        this.gameStart()
      }
    }, 3000)
    this.skill = _.random(1, 3)
  },
  mounted() {
    this.audio.loop = true
    this.audio.volume = 0.5
    this.audio.play()
  },
  beforeUnmount() {
    this.audio.pause()
    this.fetchEnemyInfo('')
  },
  computed: {
    ...mapGetters(['userDeck', 'enemyInfo'])
  }
}
</script>

<style scoped>
.inGame {
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-image: url('@/assets/game_background.png');
  background-repeat: no-repeat;
  background-size: cover;
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
  border-radius: 10px;
  width: 12vh;
  height: 12vh;
  margin: 1vh;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}
.yourDockChoItemImage {
  width: 11vh;
  height: 11vh;
}
.inGame__middle {
  display: flex;
}
.currentMyDockCho {
  width: 50vw;
  height: 55vh;
}
.myDockChoMon {
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
  border: 5px solid #ffe140;
  border-radius: 10px;
  width: 17vh;
  height: 17vh;
  margin: 1vh;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}
.myDockChoItemImage {
  width: 16vh;
  height: 16vh;
}
.deadBlurBox {
  background-color: rgba(0, 0, 0, 0.4);
  border-radius: 10px;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 9999;
  position: absolute;
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
@media screen and (max-width: 850px) {
  .myDockChoItem {
    border-width: 2px;
  }
}
</style>
