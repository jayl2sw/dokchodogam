<template>
  <div class="background">
    <!-- <img class="background" src="@/assets/game_background.png" alt="" /> -->
    <div class="loginpage">
      <div class="loginpage__left">
        <img class="loginpage__img" src="@/assets/login/book.gif" alt="" />
        <div class="dokcho1Box">
          <img class="dokchoImage" src="@/assets/login/1.png" alt="" />
        </div>
        <div class="dokcho2Box">
          <img class="dokchoImage" src="@/assets/login/2.png" alt="" />
        </div>
        <div class="dokcho3Box">
          <img class="dokchoImage" src="@/assets/login/3.png" alt="" />
        </div>
      </div>
      <div class="loginpage__right">
        <img class="logo__img" src="@/assets/dokcho_logo.png" alt="" />
        <input v-model="username" placeholder="아이디를 입력하세요" />
        <input
          @keyup.enter="login()"
          v-model="password"
          type="password"
          placeholder="비밀번호를 입력하세요"
        />
        <div class="loginpage__btn">
          <button class="login__btn" @click="login()">로그인</button>

          <!-- <a href="/oauth2/authorization/kakao">
            <button class="kakaologin__btn">카카오로그인</button>
          </a> -->

          <button class="kakaologin__btn" @click="kakaoLogin()">
            카카오로그인
          </button>
        </div>
        <div class="loginpage__btn">
          <button class="signup__btn" @click="signUp()">회원가입</button>
          <!-- <a href="/oauth2/authorization/kakao">카카오 로그인</a> -->
          <!-- <button class="findpassword__btn" @click="findpassword()">
            비밀번호찾기
          </button> -->
        </div>
        <div>
          <a class="findpassword__btn" @click="findpassword()"
            >비밀번호를 잊으셨나요?
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import { mapActions, mapGetters } from 'vuex'

export default {
  data() {
    return {
      username: '',
      password: '',
      kakaoLoginURL: BASE_URL + '/oauth2/authorization/kakao'
    }
  },
  methods: {
    ...mapActions(['doRefreshToken', 'fetchUserInfo', 'fetchUserDeck']),
    ...mapGetters(['isAccessTokenExpired']),
    kakaoLogin() {
      // window.open(BASE_URL + '/oauth2/authorization/kakao')
      window.location.href = this.kakaoLoginURL
    },
    findpassword() {
      this.$router.push({ name: 'findpassword' })
    },
    signUp() {
      this.$router.push({ name: 'signup' })
    },
    async login() {
      console.log(this.username)
      console.log(this.password)
      try {
        const result = await axios.post(
          BASE_URL + '/api/v1/user/auth/login',
          {
            username: this.username,
            password: this.password
          },
          {
            headers: {
              'Content-type': 'application/json'
            }
          }
        )
        if (result.status === 200) {
          console.log(result)
          localStorage.setItem('accessToken', result.data.accessToken)
          localStorage.setItem('refreshToken', result.data.refreshToken)
          const option = {
            headers: {
              AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
            }
          }
          await axios
            .get(BASE_URL + '/api/v1/user/myinfo', option)
            .then((res) => {
              this.fetchUserInfo(res.data)
            })
          await this.$router.push({ name: 'main' })
        }
      } catch (err) {
        alert('아이디나 비밀번호를 확인해주세요🙏')
      }
    }
  }
}
</script>

<style scoped>
button {
  height: 5vh;
  border-radius: 50px;
  border: none;
  width: 10vw;
  margin: 10px;
}

.background {
  height: 100vh;
  overflow: hidden;
  margin: 0;
  background-image: url('@/assets/game_background.png');
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

.loginpage {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

.loginpage__img {
  width: 35vw;
  height: 50vh;
  margin-top: 10vh;
}

@keyframes motion {
  from {
    transform: translate3d(0, 0, 0);
  }
  to {
    transform: translate3d(0, -15px, 0);
  }
}

@keyframes dokcho1 {
  0% {
    bottom: 10vh;
  }
  100% {
    left: 20vw;
    bottom: 38vh;
  }
}
@keyframes dokcho2 {
  0% {
    bottom: 10vh;
  }
  100% {
    left: 0vw;
    bottom: 33vh;
  }
}

@keyframes dokcho3 {
  0% {
    bottom: 10vh;
  }
  100% {
    left: 6vw;
    bottom: 48vh;
  }
}
.dokcho1Box {
  width: 10vw;
  height: 10vw;
  position: absolute;
  bottom: 10vh;
  left: 10vw;
  animation: dokcho1 1s linear 0s 1 forwards;
}
.dokcho2Box {
  width: 10vw;
  height: 10vw;
  position: absolute;
  bottom: 10vh;
  left: 10vw;
  animation: dokcho2 1s linear 0s 1 forwards;
}
.dokcho3Box {
  width: 10vw;
  height: 10vw;
  position: absolute;
  bottom: 10vh;
  left: 10vw;
  animation: dokcho3 1s linear 0s 1 forwards;
}
.dokchoImage {
  width: 10vw;
  height: 10vw;
  animation: motion 0.5s linear 1s infinite alternate;
}

.loginpage__left {
  /* height: 100%; */
  width: 30vw;
  margin-left: 5vw;
  margin-right: 5vw;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-content: center;
  position: relative;
}

.loginpage__right {
  /* height: 100%; */
  width: 30vw;
  margin-left: 5vw;
  margin-right: 5vw;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
}

input {
  display: block;
  width: 30vw;
  height: 7vh;
  margin: 8px 0 8px 0;
  padding: 10px 15px 10px 25px;
  /* background: #ececec url('@/assets/search-icon.png') no-repeat 20px center;
  background-size: 20px 20px; */
  font-size: 16px;
  border: #ececec solid 2px;
  border-radius: 20px;
  /* box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px,
    rgba(0, 0, 0, 0.3) 0px 1px 3px -1px; */
}

input::placeholder {
  color: #467302;
}

input:focus {
  outline: none;
  border: #467302 solid 2px;
}

.loginpage__btn {
  display: flex;
  justify-content: space-around;
}

.login__btn {
  background-color: #a7c957;
  transition: 0.3s;
}

.login__btn:hover {
  background-color: #467302;
  color: white;
}

.kakaologin__btn {
  background-color: #ffe62d;
  color: rgb(0, 0, 0);
  transition: 0.3s;
}
.kakaologin__btn:hover {
  background-color: #ffd104;
}

.signup__btn {
  background-color: rgb(113, 142, 255);
  transition: 0.3s;
}
.signup__btn:hover {
  background-color: rgb(46, 60, 255);
  color: white;
}

.findpassword__btn {
  display: flex;
  justify-content: space-around;
}

@media screen and (max-width: 850px) {
  .background {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .loginpage {
    display: flex;
    flex-direction: column;
  }
  .loginpage__left {
    width: 100%;
  }
  .loginpage__img {
    height: 30vh;
    width: 40vh;
  }
  @keyframes dokcho1 {
    0% {
      bottom: 10vh;
    }
    100% {
      left: 60vw;
      bottom: 24vh;
    }
  }
  @keyframes dokcho2 {
    0% {
      bottom: 10vh;
    }
    100% {
      left: 18vw;
      bottom: 20vh;
    }
  }

  @keyframes dokcho3 {
    0% {
      bottom: 10vh;
    }
    100% {
      left: 35vw;
      bottom: 28vh;
    }
  }
  .dokcho1Box {
    width: 10vw;
    height: 10vw;
    position: absolute;
    bottom: 10vh;
    left: 40vw;
    animation: dokcho1 1s linear 0s 1 forwards;
  }
  .dokcho2Box {
    width: 10vh;
    height: 10vh;
    position: absolute;
    bottom: 10vh;
    left: 40vw;
    animation: dokcho2 1s linear 0s 1 forwards;
  }
  .dokcho3Box {
    width: 10vh;
    height: 10vh;
    position: absolute;
    bottom: 10vh;
    left: 40vw;
    animation: dokcho3 1s linear 0s 1 forwards;
  }
  .dokchoImage {
    width: 10vh;
    height: 10vh;
    animation: motion 0.5s linear 1s infinite alternate;
  }
  .loginpage__right {
    width: 80vw;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .logo__img {
    height: 20vh;
  }
  input {
    width: 70vw;
    height: 5vh;
    font-size: 1.5vh;
    background-size: 3vw 3vw;
    padding-left: 7vw;
  }
  button {
    height: 6vh;
    border-radius: 50px;
    border: none;
    width: 35vw;
    margin-top: 20px;
    margin-bottom: 20px;
  }
}
</style>
