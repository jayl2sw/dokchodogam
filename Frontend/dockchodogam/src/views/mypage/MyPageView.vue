<template>
  <NavBar @overflow="overflow" />
  <div
    class="mypage"
    :class="
      this.showMenu ? 'open-menu' : this.showChangeDokchoMenu ? 'open-menu' : ''
    "
  >
    <div class="mypage__left">
      <div class="myDockcho">
        <img
          :src="this.imageBaseUrl + '/' + this.userInfo.profile_img + '.png'"
          alt=""
        />
      </div>
      <button class="change__dockcho" @click="this.onClickChangeDokcho()">
        대표 독초몬 변경
      </button>
    </div>
    <div class="mypage__right">
      <div class="myProfile">
        <div class="myProfile__medal">
          <img src="@/assets/medal.png" alt="" />
        </div>
        <div>
          <p class="TITLE myProfile__name">{{ this.userInfo.nickname }}님</p>
          <p class="myProfile__contents">
            현재 재화 : <span class="emphasize">{{ this.userInfo.money }}</span
            >냥
          </p>
          <p class="myProfile__contents">
            아레나 순위 : <span class="emphasize">{ }</span>위
          </p>
          <p class="myProfile__contents">
            독초보감과 함께한 지 <span class="emphasize">{ }</span>일 째
          </p>
        </div>
      </div>
      <div class="profile__btn" :class="this.isNone ? 'displayNone' : ''">
        <button class="change__password" @click="this.displayNone()">
          비밀번호 변경
        </button>
        <button class="quit__btn" @click="this.deleteUser()">회원 탈퇴</button>
      </div>
      <div class="changePw__form" :class="this.isNone ? '' : 'displayNone'">
        <div class="changePw__inputs">
          <input
            v-model="oldPassword"
            type="password"
            placeholder="현재 비밀번호"
          />
          <input
            v-model="newPassword"
            type="password"
            placeholder="새 비밀번호"
          />
          <input
            v-model="newPassword2"
            type="password"
            placeholder="새 비밀번호 확인"
          />
        </div>
        <div class="changePw__btn">
          <button @click="this.displayNone()" class="cancel__btn">취소</button>
          <button @click="this.changePassword()" class="complete__btn">
            수정 완료
          </button>
        </div>
      </div>
    </div>
  </div>
  <MyDokchoChange
    @closeChangeDokcho="closeChangeDokcho"
    :showChangeDokchoMenu="showChangeDokchoMenu"
  />
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'
import MyDokchoChange from '@/components/mypage/MyDokchoChange.vue'
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import swal from 'sweetalert'

var passwordCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/

export default {
  components: {
    NavBar,
    MyDokchoChange
  },
  data() {
    return {
      showMenu: false,
      isNone: false,
      userInfo: JSON.parse(localStorage.getItem('userInfo')),
      showChangeDokchoMenu: false,
      oldPassword: this.oldPassword,
      newPassword: this.newPassword,
      newPassword2: this.newPassword2,
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  methods: {
    overflow(value) {
      this.showMenu = value
    },
    displayNone() {
      this.isNone = !this.isNone
    },
    closeChangeDokcho(value) {
      this.showChangeDokchoMenu = value
    },
    onClickChangeDokcho() {
      this.showChangeDokchoMenu = true
    },
    changePassword() {
      if (!passwordCheck.test(this.newPassword)) {
        swal({
          title:
            '비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리를 사용해야 해요🙏',
          icon: 'error',
          buttons: false,
          timer: 2000
        })
      } else if (this.newPassword === this.newPassword2) {
        console.log(this.newPassword)
        console.log(this.newPassword2)
        axios
          .put(
            BASE_URL + '/api/v1/user/password',
            {
              newPW: this.newPassword
            },
            {
              headers: {
                'Content-type': 'application/json',
                AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
              }
            }
          )
          .then((res) => {
            console.log(res)
            swal({
              title: '비밀번호가 변경되었습니다!😘',
              icon: 'success',
              buttons: false,
              timer: 1500
            })
          })
          .catch((err) => {
            console.log(err)
          })
      } else {
        swal({
          title: '새 비밀번호를 한번 더 확인해 주세요😢',
          icon: 'error',
          buttons: false,
          timer: 1500
        })
      }
    },
    deleteUser() {
      if (confirm('정말 탈퇴하시겠어요? 독초도감을 완성하지 못했는데..😥')) {
        axios
          .delete(BASE_URL + '/api/v1/user/', {
            headers: {
              'Content-type': 'application/json',
              AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
            }
          })
          .then((res) => {
            console.log(res)
            swal({
              title: '탈퇴가 완료되었어요!😭',
              icon: 'success',
              buttons: false,
              timer: 1500
            })
            localStorage.clear()
            this.$router.push({
              path: '/'
            })
          })
          .catch((err) => {
            console.log(err)
          })
      }
    }
  },
  watch: {
    showChangeDokchoMenu() {
      this.userInfo = JSON.parse(localStorage.getItem('userInfo'))
    }
  }
}
</script>

<style scoped>
button {
  height: 4vh;
  border-radius: 50px;
  border: none;
  width: 10vw;
  margin: 0 auto;
}
.mypage {
  margin: 5vh 10vw 0 10vw;
  border-radius: 50px;
  display: flex;
  justify-content: space-between;
  width: 80vw;
  height: 80vh;
  background: url('@/assets/hanji.jpeg') no-repeat;
  background-size: cover;
}
.mypage__left {
  height: 100%;
  width: 20vw;
  margin-left: 10vw;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.myDockcho {
  height: 30vh;
  margin: 2vh 0;
  background-color: rgba(255, 255, 255, 0.3);
  border-radius: 50px;
}
.myDockcho > img {
  width: 20vw;
  height: 100%;
  object-fit: contain;
  vertical-align: middle;
}
.change__dockcho {
  background-color: #a7c957;
  transition: 0.3s;
}
.change__dockcho:hover {
  background-color: #467302;
  color: white;
}
.mypage__right {
  height: 100%;
  width: 30vw;
  margin-right: 10vw;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.myProfile {
  height: 30vh;
  width: 100%;
  background-color: rgba(255, 255, 255, 0.3);
  border-radius: 50px;
  margin: 2vh 0;
  padding: 3vh;
  display: flex;
  /* transition: 1s; */
}
.myProfile__medal {
  margin-top: 1vh;
  margin-right: 1vw;
}
.myProfile__medal > img {
  width: 5vw;
}
.myProfile__name {
  font-size: 2vw;
  margin-bottom: 3vh;
}
.myProfile__contents {
  font-size: 1vw;
  line-height: 4vh;
}
.emphasize {
  font-family: 'UhBeeSe_hyun';
  font-weight: bold;
  color: #467302;
}
.profile__btn {
  display: flex;
  justify-content: space-around;
}
.change__password {
  background-color: #a7c957;
  transition: 0.3s;
}
.change__password:hover {
  background-color: #467302;
  color: white;
}
.quit__btn {
  background-color: #ff3434;
  color: white;
  transition: 0.3s;
}
.quit__btn:hover {
  background-color: #a72121;
}
.changePw__form {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 25vh;
  transition: 0.6s;
}
.changePw__inputs {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 20vh;
  margin-bottom: 2vh;
}
.changePw__inputs input {
  height: 5vh;
  padding: 1vh 1vw;
  border: 3px solid white;
  border-radius: 20px;
}
.changePw__inputs input:focus {
  outline: none;
  border: 3px solid #467302;
}
.changePw__btn {
  display: flex;
  justify-content: flex-end;
}
.cancel__btn {
  background-color: #ddd;
  transition: 0.3s;
}
.cancel__btn:hover {
  background-color: #aaa;
  color: white;
}
.complete__btn {
  background-color: #a7c957;
  transition: 0.3s;
}
.complete__btn:hover {
  background-color: #467302;
  color: white;
}
.displayNone {
  visibility: hidden;
  opacity: 0;
  height: 0;
  transition: 0.6s;
}
@media screen and (max-width: 850px) {
  .mypage {
    flex-direction: column;
    align-items: center;
    height: auto;
  }
  .mypage__left {
    margin: 0;
    width: 80%;
  }
  .myDockcho {
    padding: 5vw;
  }
  .myDockcho > img {
    width: 100%;
    height: 100%;
    object-fit: contain;
    vertical-align: middle;
  }
  .myProfile__medal {
    margin-right: 3vw;
  }
  .myProfile__medal > img {
    width: 12vw;
  }
  .myProfile__name {
    font-size: 4vw;
    margin-bottom: 3vh;
  }
  .myProfile__contents {
    font-size: 2.5vw;
    line-height: 4vh;
  }
  .change__dockcho {
    width: 50vw;
    height: 8vw;
    font-size: 3vw;
  }
  .mypage__right {
    margin: 0;
    margin-bottom: 2vh;
    width: 80%;
  }
  .change__password {
    width: 20vw;
    font-size: 2.5vw;
  }
  .quit__btn {
    width: 20vw;
    font-size: 2.5vw;
  }
  .changePw__inputs input {
    height: 5vh;
    padding: 2vh 3vw;
    font-size: 3vw;
  }
  .cancel__btn {
    width: 20vw;
    font-size: 2.5vw;
  }
  .complete__btn {
    width: 20vw;
    font-size: 2.5vw;
  }
  .open-menu {
    overflow: hidden;
    position: fixed;
  }
}
</style>
