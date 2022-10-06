<template>
  <LoadingPage v-if="this.isLoading" />
  <div class="containerBox" v-show="!this.isLoading">
    <div id="warning-message">
      <p class="TITLE">
        아레나는<br /><br /><span class="emphasize">"가로 화면 전용"</span>
        게임입니다.
      </p>
    </div>
    <div class="inGame" v-if="!this.isGameEndFlag">
      <div class="inGame__top">
        <div class="yourDockChoList">
          <div
            v-for="(yourDockCho, i) in this.yourDockChoList"
            :key="i"
            class="yourDockChoItem"
          >
            <img
              :src="
                require('@/assets/monster/' + yourDockCho.monsterId + '.png')
              "
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
            <div class="myDockchoBox">
              <img
                :src="this.imageBaseUrl + '/skill' + this.skill + '.png'"
                alt=""
                class="skillEffect"
                :class="this.skillEffect ? 'onEffect' : ''"
              />
              <DockChoMon
                :data="this.currentMyDockCho"
                :damage="this.yourDamage"
                :sangseong="this.sangseong"
                :who="'me'"
              />
            </div>
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
              :sangseong="this.sangseong"
              :who="'you'"
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
              :src="require('@/assets/monster/' + myDockCho.monsterId + '.png')"
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
          <img
            :src="this.imageBaseUrl + '/skill' + this.skill + '.png'"
            alt=""
            :class="this.isUseSkill ? 'skillImg usedSkill' : 'skillImg'"
          />
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
import swal from 'sweetalert'
import { BASE_URL } from '@/constant/BASE_URL'
import axios from 'axios'

export default {
  components: {
    DockChoMon,
    ArenaGameResult,
    LoadingPage
  },
  data() {
    return {
      battleId: 0,
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
      myType: '',
      yourType: '',
      sangseong: '',
      isMyDockchoDead: false,
      isGameEndFlag: false,
      resultInfo: [],
      skill: '',
      nowUseSkill: false,
      isUseSkill: false,
      audio: new Audio(process.env.VUE_APP_S3_URL + '/game.mp3'),
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      isPortrait: true,
      skillEffect: false
    }
  },
  methods: {
    ...mapActions(['fetchUserDeck', 'fetchEnemyInfo']),
    gameStart() {
      setTimeout(() => {
        this.currentMyDockCho = this.myDockChoList[0]
        this.currentYourDockCho = this.yourDockChoList[0]
        this.myType = this.setType(this.currentMyDockCho)
        this.yourType = this.setType(this.currentYourDockCho)
        this.sangseong = this.calSangSeong()
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
              this.yourDamage *= 1.2
            } else if (this.currentYourDockCho.type === 'YAKCHO') {
              this.myDamage *= 1.2
            } else if (this.currentYourDockCho.type === 'HIDDEN') {
              this.yourDamage *= 1.2
            }
          } else if (this.currentMyDockCho.type === 'DOKCHO') {
            if (this.currentYourDockCho.type === 'JAPCHO') {
              this.myDamage *= 1.2
            } else if (this.currentYourDockCho.type === 'YAKCHO') {
              this.yourDamage *= 1.2
            } else if (this.currentYourDockCho.type === 'HIDDEN') {
              this.yourDamage *= 1.2
            }
          } else if (this.currentMyDockCho.type === 'YAKCHO') {
            if (this.currentYourDockCho.type === 'DOKCHO') {
              this.myDamage *= 1.2
            } else if (this.currentYourDockCho.type === 'JAPCHO') {
              this.yourDamage *= 1.2
            } else if (this.currentYourDockCho.type === 'HIDDEN') {
              this.yourDamage *= 1.2
            }
          } else {
            this.myDamage *= 1.2
            if (this.currentYourDockCho.type === 'HIDDEN') {
              this.yourDamage *= 1.2
            }
          }
          this.myDamage = Math.round(this.myDamage)
          this.yourDamage = Math.round(this.yourDamage)
          if (this.nowUseSkill) {
            if (this.skill === 1) {
              this.myDamage *= 2
              this.skillEffect = true
            } else if (this.skill === 2) {
              this.yourDamage = 0
              this.skillEffect = true
            } else {
              this.myDamage = this.yourDamage
              this.yourDamage = 0
              this.skillEffect = true
            }
          }
          this.judged()
        }, 300)
      }
    },
    judged() {
      this.currentMyDockCho.currentHp -= this.yourDamage
      this.currentYourDockCho.currentHp -= this.myDamage
      this.postGameLog()
      if (this.nowUseSkill) {
        this.isUseSkill = true
        this.nowUseSkill = false
      }
      setTimeout(() => {
        this.skillEffect = false
        this.round += 1
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
          this.myType = ''
          this.yourType = ''
          this.sangseong = ''
          this.isMyDockchoDead = true
          setTimeout(() => {
            this.isGameEnd()
          }, 2000)
        } else if (this.currentMyDockCho.currentHp <= 0) {
          this.isDead_myDockCho[this.currentMyIdx] = true
          this.currentMyIdx = this.isDead_myDockCho.indexOf(false)
          this.currentMyDockCho = ''
          this.myType = ''
          this.sangseong = ''
          this.isMyDockchoDead = true
          setTimeout(() => {
            this.isGameEnd()
          }, 2000)
        } else if (this.currentYourDockCho.currentHp <= 0) {
          this.isDead_yourDockCho[this.currentYourIdx] = true
          this.currentYourIdx = this.isDead_yourDockCho.indexOf(false)
          this.currentYourDockCho = ''
          this.yourType = ''
          this.sangseong = ''
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
      if (this.currentMyIdx === -1 && this.currentYourIdx === -1) {
        this.postGameEnd(false)
        setTimeout(() => {
          this.isGameEndFlag = true
          this.resultInfo = [this.enemyInfo.isChinsun, '패배']
        }, 1000)
      } else if (this.currentMyIdx === -1) {
        this.postGameEnd(false)
        setTimeout(() => {
          this.isGameEndFlag = true
          this.resultInfo = [this.enemyInfo.isChinsun, '패배']
        }, 1000)
      } else if (this.currentYourIdx === -1) {
        this.postGameEnd(true)
        setTimeout(() => {
          this.isGameEndFlag = true
          this.resultInfo = [this.enemyInfo.isChinsun, '승리!']
        }, 1000)
      } else if (this.currentMyDockCho && this.currentYourDockCho === '') {
        this.currentYourDockCho = this.yourDockChoList[this.currentYourIdx]
        this.yourType = this.setType(this.currentYourDockCho)
        this.sangseong = this.calSangSeong()
        setTimeout(() => {
          this.attack()
        }, 2000)
      }
    },
    selectNextDockcho(idx) {
      var audio = new Audio(process.env.VUE_APP_S3_URL + '/card_select.mp3')
      audio.volume = 1
      audio.play()
      if (
        this.isMyDockchoDead &&
        !this.isDead_myDockCho[idx] &&
        this.currentMyDockCho === ''
      ) {
        this.currentMyIdx = idx
        this.currentMyDockCho = this.myDockChoList[this.currentMyIdx]
        this.currentYourDockCho = this.yourDockChoList[this.currentYourIdx]
        this.myType = this.setType(this.currentMyDockCho)
        this.yourType = this.setType(this.currentYourDockCho)
        this.sangseong = this.calSangSeong()
        this.isMyDockchoDead = false
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
    },
    postGameStart() {
      const data = {
        attacker: JSON.parse(localStorage.getItem('userInfo')).nickname,
        defender: this.enemyInfo.nickname,
        monster0: this.myDockChoList[0].monsterId,
        monster1: this.myDockChoList[1].monsterId,
        monster2: this.myDockChoList[2].monsterId,
        monster3: this.myDockChoList[3].monsterId,
        monster4: this.myDockChoList[4].monsterId,
        monster5: this.yourDockChoList[0].monsterId,
        monster6: this.yourDockChoList[1].monsterId,
        monster7: this.yourDockChoList[2].monsterId,
        monster8: this.yourDockChoList[3].monsterId,
        monster9: this.yourDockChoList[4].monsterId,
        rank: true
      }
      axios
        .post(BASE_URL + '/api/v1/battle/new', data, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          this.battleId = res.data.battle_id
        })
        .catch((err) => console.log(err))
    },
    postGameLog() {
      const data = {
        attackMonsterDamage: this.myDamage,
        attackMonsterHp: this.currentMyDockCho.currentHp,
        attackMonsterId: this.currentMyDockCho.monsterId,
        battleId: this.battleId,
        defendMonsterDamage: this.yourDamage,
        defendMonsterHp: this.currentYourDockCho.currentHp,
        defendMonsterId: this.currentYourDockCho.monsterId,
        round: this.round,
        skill: this.skill,
        skillUsage: this.nowUseSkill
      }
      axios
        .post(BASE_URL + '/api/v1/battle/logs/new', data, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken'),
            'Content-type': 'application/json'
          }
        })
        .catch((err) => console.log(err))
    },
    postGameEnd(win) {
      const data = {
        battle_id: this.battleId,
        success: win
      }
      axios
        .post(BASE_URL + '/api/v1/battle/finish', data, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken'),
            'Content-type': 'application/json'
          }
        })
        .catch((err) => console.log(err))
    },
    calSangSeong() {
      if (this.myType === '잡초') {
        if (this.yourType === '잡초') {
          return '상성 보통'
        } else if (this.yourType === '독초') {
          return '상성 나쁨'
        } else if (this.yourType === '약초') {
          return '상성 좋음'
        } else if (this.yourType === '히든') {
          return '상성 나쁨'
        } else {
          return ''
        }
      } else if (this.myType === '독초') {
        if (this.yourType === '잡초') {
          return '상성 좋음'
        } else if (this.yourType === '독초') {
          return '상성 보통'
        } else if (this.yourType === '약초') {
          return '상성 나쁨'
        } else if (this.yourType === '히든') {
          return '상성 나쁨'
        } else {
          return ''
        }
      } else if (this.myType === '약초') {
        if (this.yourType === '잡초') {
          return '상성 나쁨'
        } else if (this.yourType === '독초') {
          return '상성 좋음'
        } else if (this.yourType === '약초') {
          return '상성 보통'
        } else if (this.yourType === '히든') {
          return '상성 나쁨'
        } else {
          return ''
        }
      } else if (this.myType === '히든') {
        if (this.yourType === '잡초') {
          return '상성 좋음'
        } else if (this.yourType === '독초') {
          return '상성 좋음'
        } else if (this.yourType === '약초') {
          return '상성 좋음'
        } else if (this.yourType === '히든') {
          return '상성 보통'
        } else {
          return ''
        }
      } else {
        return this.sangseong === ''
      }
    },
    setType(dokchoInfo) {
      if (dokchoInfo.type === 'JAPCHO') {
        return '잡초'
      } else if (dokchoInfo.type === 'DOKCHO') {
        return '독초'
      } else if (dokchoInfo.type === 'YAKCHO') {
        return '약초'
      } else {
        return '히든'
      }
    }
  },
  created() {
    this.fetchUserDeck()
    this.isChinsun = this.enemyInfo.isChinsun
    if (!this.enemyInfo.nickname) {
      this.$router.push({ path: '/main' })
    }
    setTimeout(() => {
      this.isLoading = false
      this.myDockChoList = this.userDeck
      this.yourDockChoList = this.enemyInfo.enemyDeck
      this.postGameStart()
      if (this.enemyInfo.nickname) {
        this.gameStart()
      }
    }, 3000)
    this.skill = _.random(1, 3)
  },
  mounted() {
    this.audio.loop = true
    this.audio.volume = 0.2
    this.audio.play()
  },
  beforeUnmount() {
    this.audio.pause()
    if (!this.isGameEndFlag) {
      swal({
        title: '정상적이지 않은 게임 진행입니다 😡',
        icon: 'error',
        timer: 1500
      })
    }
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
  top: 25vh;
  left: -20vw;
  transition: all 1s;
}
.myDockchoBox {
  width: 100%;
  height: 100%;
  position: absolute;
}
.skillEffect {
  width: 15vw;
  height: 15vw;
  transition: 1s;
  opacity: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  z-index: 9999;
  transform: translate(-50%, -50%);
  display: none;
}
.onEffect {
  opacity: 0.5;
  transition: 1s;
  display: block;
}
.currentYourDockCho {
  width: 50vw;
  height: 55vh;
}
.yourDockChoMon {
  width: 20vw;
  height: 25vw;
  position: fixed;
  top: 25vh;
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
  background-color: rgba(255, 255, 255, 0.4);
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
  width: 17vh;
  height: 17vh;
  margin: 1vh;
  cursor: pointer;
  position: relative;
}
.skillImg {
  position: absolute;
  width: 17vh;
  height: 17vh;
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
  opacity: 0.3;
  cursor: default;
}

@media screen and (max-width: 850px) {
  .myDockChoItem {
    border-width: 2px;
  }
}
@media only screen and (orientation: portrait) {
  .containerBox {
    background-image: none;
    background-color: white;
    height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  .inGame {
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
