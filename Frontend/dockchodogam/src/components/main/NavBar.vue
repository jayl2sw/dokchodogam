<template>
  <div>
    <nav class="navbar">
      <div class="navbar__logo">
        <a href="/main">
          <img src="@/assets/dokcho_logo.png" alt="" class="navbar__logoImg" />
        </a>
        <div class="navbar__toggleBtn" @click="menuOpen()">
          <font-awesome-icon icon="fa-solid fa-bars" size="xl" />
        </div>
      </div>
      <div class="navbar__centerMenu">
        <div>
          <ul class="navbar__centerIcons">
            <li>
              <a @click="popon('/camera')">
                <p class="TITLE">사 진</p>
              </a>
            </li>
            <li>
              <a @click="popon('/encyclopedia')">
                <p class="TITLE">도 감</p>
              </a>
            </li>
            <li>
              <a @click="popon('/game')">
                <p class="TITLE">게 임</p>
              </a>
            </li>
          </ul>
        </div>
      </div>
      <div class="navbar__sideMenu">
        <div>
          <ul class="navbar__sideIcons">
            <li>
              <a href="/mypage">
                <font-awesome-icon icon="fa-solid fa-user" size="xl" />
              </a>
            </li>
            <li>
              <a href="#" @click="logout()">
                <font-awesome-icon icon="fa-solid fa-door-open" size="xl" />
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <div class="sideBar" :class="this.showMenu ? 'open-menu' : 'closed-menu'">
      <div>
        &nbsp;<font-awesome-icon
          class="x__icon"
          icon="fa-solid fa-x"
          size="sm"
          @click="menuOpen()"
        />
      </div>
      <div class="sideBar__menu">
        <div class="sideBar__profile">
          <img
            :src="this.imageBaseUrl + '/' + this.userInfo.profile_img + '.png'"
            alt="profile"
          />
          <p class="TITLE">{{ this.userInfo.nickname }}</p>
        </div>
        <div class="sideBar__items">
          <ul class="sideBar__icons">
            <li>
              <a href="/camera">
                <font-awesome-icon icon="fa-solid fa-camera" size="xl" />
                <p class="TITLE">사진</p>
              </a>
            </li>
            <li>
              <a href="/encyclopedia">
                <font-awesome-icon icon="fa-solid fa-book" size="xl" />
                <p class="TITLE">도감</p>
              </a>
            </li>
          </ul>
          <ul class="sideBar__icons">
            <li>
              <a href="/game">
                <font-awesome-icon icon="fa-solid fa-gamepad" size="xl" />
                <p class="TITLE">게임</p>
              </a>
            </li>
            <li>
              <a href="/mypage">
                <font-awesome-icon icon="fa-solid fa-user" size="xl" />
                <p class="TITLE">내설정</p>
              </a>
            </li>
          </ul>
        </div>
        <div class="sideBar__logout" @click="logout()">
          <a href="#">
            <font-awesome-icon
              icon="fa-solid fa-door-open"
              class="sideBar__logoutIcon"
            />
            <span class="out__message TITLE">나가기</span>
          </a>
        </div>
      </div>
    </div>
    <div
      class="blur"
      :class="this.showMenu ? 'open-menu__blur' : ''"
      @click="menuOpen()"
    ></div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import swal from 'sweetalert'
import Swal from 'sweetalert2'

const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
    title: 'custom-title-class',
    confirmButton: 'btn btn-success',
    cancelButton: 'btn btn-danger'
  }
})
export default {
  data() {
    return {
      showMenu: false,
      userInfo: JSON.parse(localStorage.getItem('userInfo')),
      imageBaseUrl: process.env.VUE_APP_S3_URL
    }
  },
  methods: {
    menuOpen() {
      this.showMenu = !this.showMenu
      this.$emit('overflow', this.showMenu)
    },
    logout() {
      swalWithBootstrapButtons
        .fire({
          title: '도감을 덮으시겠어요?',
          text: '풀깨비들이 도감에서 웅성거리고 있어요 😥',
          icon: 'question',
          showCancelButton: true,
          confirmButtonText: '예',
          cancelButtonText: '아니오',
          reverseButtons: true
        })
        .then((res) => {
          if (res.value) {
            // console.log(result)
            this.fetchLogout()
          }
        })
    },
    fetchLogout() {
      axios
        .put(BASE_URL + '/api/v1/user/logout', null, {
          headers: {
            // 'Content-type': 'application/json',
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          console.log(res)
          swal({
            title: '로그아웃이 완료되었습니다!',
            text: ' 다시 도감을 펼치는 날을 기다릴게요 🌻 ',
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
    },
    popon(path) {
      console.log(process.env)
      var audio = new Audio(process.env.VUE_APP_S3_URL + '/button.mp3')
      audio.play()
      this.$router.push(path)
    }
  }
}
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  padding: 0 3vw 0 3vw;
  background-color: #a7c957;
  margin: 0;
}
.navbar__centerMenu {
  display: flex;
}
.navbar__logo {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 150px;
}
.navbar__logoImg {
  height: 70px;
}
.navbar__toggleBtn {
  display: none;
}
.navbar__sideMenu {
  width: 150px;
}
.navbar__centerMenu > div {
  margin: 0 1vw 0 1vw;
  align-items: center;
}

.navbar__centerIcons {
  list-style: none;
  display: flex;
  margin: 1.5vh 0 1.5vh 0;
  padding: 0;
}
.navbar__centerIcons > li {
  margin: 0 2vw 0 2vw;
  width: 8vw;
  height: 3rem;
  text-align: center;
}
.navbar__centerIcons a {
  cursor: pointer;
}
.navbar__centerIcons p {
  line-height: 3rem;
  font-weight: bold;
  color: #467302;
  transition: 0.3s;
}
.navbar__centerIcons p:hover {
  color: #2e4c00;
  font-size: 1.3rem;
}
.navbar__sideIcons {
  list-style: none;
  display: flex;
  justify-content: flex-end;
  margin: 1.5vh 0 1.5vh 0;
}
.navbar__sideIcons > li {
  margin: 0 1vw 0 1vw;
  width: 48px;
  text-align: center;
}
a {
  text-decoration: none;
  color: black;
}

.sideBar {
  width: 60vw;
  height: 100vh;
  position: fixed;
  top: 0px;
  right: -60vw;
  background: #cde29d;
  padding: 1vh 2vw;
  z-index: 2;
  overflow: auto;
}
.sideBar__menu {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

.blur {
  background-color: rgba(0, 0, 0, 0.4);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
  display: none;
}
.sideBar__items {
  width: 50vw;
  border-top: 2px solid #a7c957;
  border-bottom: 2px solid #a7c957;
  display: flex;
  flex-direction: column;
  margin: 5vh 0 3vh;
}
.sideBar__profile {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.sideBar__profile > img {
  width: 25vw;
}
.x__icon {
  color: #467302;
}
.sideBar__icons {
  list-style: none;
  padding: 0;
  margin: 40px 0;
  display: flex;
  justify-content: space-around;
}
.sideBar__icons > li {
  margin: 0 5vw 0 5vw;
  width: 48px;
  text-align: center;
}
.sideBar__icons p {
  margin: 0;
}
.sideBar__logout {
  display: flex;
  justify-content: flex-end;
  width: 50vw;
  margin-top: 1vh;
}
.sideBar__logoutIcon {
  vertical-align: middle;
}
.out__message {
  margin-left: 1vw;
  font-size: 0.7rem;
}
@media screen and (max-width: 850px) {
  .navbar {
    display: flex;
    flex-direction: column;
  }
  .navbar__logo {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    width: 90vw;
  }
  .navbar__toggleBtn {
    display: block;
    cursor: pointer;
  }
  .navbar__centerMenu {
    display: none;
  }
  .navbar__sideMenu {
    display: none;
  }
  .navbar__icons > li {
    margin: 0 3vw 0 3vw;
  }

  .open-menu {
    right: 0;
    transition: all 1s;
    position: fixed;
  }
  .closed-menu {
    transition: all 1s;
  }
  .open-menu__blur {
    display: block;
  }
  svg {
    color: #467302;
  }
  svg ~ p.TITLE {
    margin-top: 2vh;
    font-size: 0.8rem;
  }
}
</style>
