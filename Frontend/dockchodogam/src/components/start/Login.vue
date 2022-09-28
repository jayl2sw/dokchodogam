<template>
  <div class="background">
    <!-- <img class="background" src="@/assets/game_background.png" alt="" /> -->
    <div class="loginpage">
      <div class="loginpage__left"></div>
      <div class="loginpage__right">
        <input v-model="userId" placeholder="아이디를 입력하세요" />
        <input
          v-model="userPassword"
          type="password"
          placeholder="비밀번호를 입력하세요"
        />
        <div class="loginpage__btn">
          <button class="login__btn" @click="login()">로그인</button>
          <button class="kakaologin__btn" @click="kakaoLogin()">
            카카오로그인
          </button>
          <button class="signup__btn" @click="signUp()">회원가입</button>
          <!-- <a href="/oauth2/authorization/kakao">카카오 로그인</a> -->
        </div>
        <!-- <button class="findpassword__btn" @click="findpassword()">
          비밀번호찾기
        </button> -->
        <div>
          <router-link class="findpassword__btn" to="/findpassword"
            >비밀번호를 잊으셨나요?</router-link
          >
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
      userId: '',
      userPassword: ''
    }
  },
  methods: {
    ...mapActions(['doRefreshToken', 'fetchUserInfo', 'fetchUserDeck']),
    ...mapGetters(['isAccessTokenExpired']),
    kakaoLogin() {
      this.$router.push({ name: 'oauth' })
    },
    findpassword() {
      this.$router.push({ name: 'findpassword' })
    },
    signUp() {
      this.$router.push({ name: 'signup' })
    },
    async login() {
      console.log(this.userId)
      console.log(this.userPassword)
      try {
        const result = await axios.post(
          BASE_URL + '/api/v1/user/auth/login',
          {
            username: this.userId,
            password: this.userPassword
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
        throw new Error(err)
      }
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

.background {
  height: 100vh;
  overflow: hidden;
  margin: 0;
  background-image: url('@/assets/game_background.png');
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}

.loginpage__right {
  height: 100%;
  width: 60vw;
  margin-left: 10vw;
  display: flex;
  flex-direction: column;
  justify-content: center;
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
</style>
