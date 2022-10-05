<template>
  <NavBar @overflow="overflow" />
  <div
    class="mypage"
    :class="
      this.showMenu ? 'open-menu' : this.showChangeDokchoMenu ? 'open-menu' : ''
    "
  >
    <div class="mypage__left">
      <div class="goToGalleryBox">
        <button class="goToGallery" @click="this.goToGallery()">
          내 갤러리로 이동
        </button>
      </div>
      <div class="myDockcho">
        <img
          :src="this.imageBaseUrl + '/' + this.userInfo.profile_img + '.png'"
          alt=""
        />
      </div>
      <button class="change__dockcho" @click="this.onClickChangeDokcho()">
        대표 풀깨비 변경
      </button>
    </div>
    <div class="mypage__right">
      <div class="block"></div>

      <div class="myProfile">
        <div class="myProfile__medal">
          <img
            :src="
              this.imageBaseUrl +
              '/tier' +
              (Math.floor(this.userInfo.rank_point / 100) + 1) +
              '.png'
            "
            alt=""
          />
        </div>
        <div>
          <div class="nickname">
            <p class="TITLE myProfile__name">{{ this.userInfo.nickname }}님</p>
          </div>
          <p class="myProfile__contents">
            지금 내 주머니에는
            <span class="emphasize">{{ this.userInfo.money }}</span
            >냥이 있어요!
          </p>
          <p class="myProfile__contents">
            오늘 아레나 <span class="emphasize">{{ this.ranking }}</span
            >위를 달성했습니다!
          </p>
          <p class="myProfile__contents">
            어느덧 독초도감과 함께한 지
            <span class="emphasize">{{
              Math.ceil((this.today - this.cdate) / (1000 * 60 * 60 * 24))
            }}</span
            >일 째
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
          <span>영문자+숫자+특수문자 조합으로 8~25자리</span>
          <input
            @keyup="checkPassword()"
            v-model="newPassword"
            type="password"
            placeholder="새 비밀번호"
          />
          <span class="allowedtext" v-if="this.isPasswordChecked"
            >이 비밀번호는 사용하셔도 좋아요👌</span
          >
          <span class="warningtext" v-else
            >비밀번호 생성 조건을 확인해주세요🙏</span
          >
          <input
            v-model="newPassword2"
            type="password"
            placeholder="새 비밀번호 확인"
          />
          <span
            class="warningtext"
            v-if="this.newPassword !== this.newPassword2"
            >비밀번호를 확인해주세요🙏</span
          >
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
  <footer>
    <p>
      <!-- 쾌락과 독초 <br />
      서상균 김성빈 박지현 오하민 이재준 최지원 <br /> -->
      © 2022. 쾌락과 독초 All rights reserved
    </p>
  </footer>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'
import MyDokchoChange from '@/components/mypage/MyDokchoChange.vue'
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import { mapActions } from 'vuex'
import swal from 'sweetalert'
import Swal from 'sweetalert2'

const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
    title: 'custom-title-class',
    confirmButton: 'btn btn-success',
    cancelButton: 'btn btn-danger'
  }
  // buttonsStyling: false
})

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
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      ranking: 0,
      isPasswordChecked: false,
      today: '',
      cdate: ''
    }
  },
  methods: {
    ...mapActions(['fetchnowUserInfo']),
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
    checkPassword() {
      if (passwordCheck.test(this.newPassword)) {
        this.isPasswordChecked = true
      } else {
        this.isPasswordChecked = false
      }
    },
    goToGallery() {
      this.$router.push({ path: '/gallery' })
    },
    changePassword() {
      if (!passwordCheck.test(this.newPassword)) {
        swal({
          title:
            '비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리를 사용해야 해요🙏',
          icon: 'error',
          text: '🤔',
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
              newPW: this.newPassword,
              nowPW: this.oldPassword
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
              text: '바뀐 비밀번호로 로그인 해주세요😉',
              buttons: false,
              timer: 1500
            })
          })
          .catch((err) => {
            console.log(err)
            swal({
              title: '현재 비밀번호를 확인해주세요😥',
              icon: 'warning',
              text: '입력해주신 비밀번호와 현재 비밀번호가 다른 것 같아요😅',
              buttons: false,
              timer: 1500
            })
          })
      } else {
        swal({
          title: '새 비밀번호를 한번 더 확인해 주세요😢',
          icon: 'error',
          text: '두 비밀번호가 다른 것 같아요..😅',
          buttons: false,
          timer: 1500
        })
      }
    },
    deleteUser() {
      swalWithBootstrapButtons
        .fire({
          title: '허준의 제자를 그만두시겠어요?',
          text: `풀깨비들이 ${this.userInfo.nickname}님을 많이 좋아하는데...😥`,
          icon: 'question',
          showCancelButton: true,
          confirmButtonText: '예',
          cancelButtonText: '아니오',
          reverseButtons: true
        })
        .then((res) => {
          if (res.value) {
            this.fetchDeleteUser()
          }
        })
    },
    fetchDeleteUser() {
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
            title: '탈퇴가 완료되었어요😭',
            icon: 'success',
            text: '언제든지 돌아오세요!',
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
  },
  watch: {
    showChangeDokchoMenu() {
      this.userInfo = JSON.parse(localStorage.getItem('userInfo'))
    }
  },
  created() {
    this.fetchnowUserInfo()
    const option = {
      headers: {
        'Content-type': 'application/json',
        AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
      }
    }
    axios
      .get(BASE_URL + '/api/v1/game/myranking', option)
      .then((res) => (this.ranking = res.data))
      .catch((err) => console.log(err))
    this.today = new Date()
    this.cdate = new Date(this.userInfo.createDate)
  }
}
</script>

<style scoped>
button {
  height: 4vh;
  border-radius: 50px;
  border: none;
  width: 12vw;
  margin: 0 auto;
}
.mypage {
  margin: 5vh 10vw 0 10vw;
  border-radius: 50px;
  display: flex;
  justify-content: space-between;
  width: 80vw;
  height: 75vh;
  /* height: 80vh; */
  background: url('@/assets/hanji.jpeg') no-repeat;
  background-size: cover;
  /* padding-bottom: 10%; */
}

footer {
  height: 10%;
  padding-top: 2%;
  position: relative;
  transform: translateY(-10%);
  text-align: center;
}

footer p {
  color: #989797;
}
.mypage__left {
  height: 100%;
  width: 20vw;
  margin-left: 10vw;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.block {
  height: 6vh;
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
  width: 14vw;
}
.change__dockcho:hover {
  background-color: #467302;
  color: white;
}
.mypage__right {
  height: 100%;
  width: 35vw;
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
.nickname {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: auto;
}
.myProfile__name {
  font-size: 2.5vw;
  margin-bottom: 1vh;
}
.goToGalleryBox {
  height: 6vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.goToGallery {
  border-radius: 50px;
  height: 4vh;
  width: 14vw;
  background-color: #d0d0d0;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 0.3s;
}
.goToGallery:hover {
  background-color: #aaa;
  transition: 0.3s;
}
.myProfile__contents {
  word-break: keep-all;
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
  height: 35vh;
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
.changePw__inputs > span {
  margin: 1vh 3vw;
  font-size: 1vw;
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
.allowedtext {
  color: #29cd2e;
}
.warningtext {
  color: #be0000;
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
    margin-bottom: 1vh;
  }
  .goToGallery {
    margin-top: 3vh;
    width: 50vw;
    height: 8vw;
    font-size: 4vw;
  }
  .myProfile__contents {
    font-size: 2.5vw;
    line-height: 4vh;
  }
  .change__dockcho {
    width: 50vw;
    height: 8vw;
    font-size: 4vw;
  }
  .mypage__right {
    margin: 0;
    margin-bottom: 2vh;
    width: 80%;
  }
  .change__password {
    width: 20vw;
    font-size: 3vw;
  }
  .quit__btn {
    width: 20vw;
    font-size: 3vw;
  }
  .changePw__form {
    height: 30vh;
  }
  .changePw__inputs > span {
    margin: 1vh 3vw;
    font-size: 2.5vw;
  }
  .displayNone {
    height: 0;
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
