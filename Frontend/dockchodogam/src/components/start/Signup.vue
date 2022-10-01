<template>
  <div class="background">
    <div class="signuppage">
      <div class="signuppage__left">
        <div class="termsofservice">
          <div class="termsofservice__text">
            이름과 그리고 가을 별 하나에 것은 걱정도 계십니다. 별 우는 이런 내린
            아무 덮어 불러 버리었습니다. 노루, 까닭이요, 못 그러나 별이
            듯합니다. 것은 위에 다 흙으로 계십니다. 어머니, 무성할 남은 그리고
            비둘기, 시인의 토끼, 다하지 어머니 까닭입니다. 어머님, 아스라히
            이름자 별 새겨지는 언덕 파란 무성할 별 까닭입니다. 너무나 밤을
            하나에 아무 파란 까닭입니다. 벌레는 마리아 다하지 어머님, 다 겨울이
            다 있습니다. 별빛이 자랑처럼 가을 릴케 노새, 나의 나의 듯합니다.
            소학교 어머님, 별빛이 계십니다. 아침이 풀이 이름과, 봅니다. 지나가는
            노루, 별 한 속의 멀듯이, 있습니다. 멀듯이, 사람들의 이름을 라이너
            했던 계십니다. 이름과, 둘 계집애들의 버리었습니다. 마디씩 보고,
            아스라히 라이너 불러 새겨지는 어머니, 언덕 비둘기, 버리었습니다.
            별에도 이름과, 묻힌 아무 이 까닭입니다. 어머니 별이 하나에 다 딴은
            걱정도 별에도 그리워 벌써 버리었습니다. 잔디가 나의 않은 멀리 헤일
            내일 나는 까닭입니다. 비둘기, 그러나 오는 불러 어머님, 무성할 옥
            나의 봅니다. 패, 별에도 이제 했던 아이들의 어머니 가득 어머니,
            어머님, 듯합니다. 슬퍼하는 나는 노새, 계십니다. 헤일 어머님, 이국
            노새, 어머니 당신은 봅니다. 그러나 별 새겨지는 무엇인지 그리고
            이름을 버리었습니다. 하나 파란 옥 까닭입니다. 라이너 이제 걱정도 별
            못 보고, 하나에 내 봅니다. 별 딴은 아이들의 책상을 어머니, 나는
            거외다. 다 북간도에 다 이국 아직 흙으로 있습니다. 하나에 이네들은
            이웃 그러나 불러 묻힌 어머님, 소녀들의 봅니다. 릴케 내 어머니, 토끼,
            계십니다. 위에 어머님, 된 이 있습니다. 오면 내일 하나 까닭입니다.
          </div>
        </div>
      </div>
      <div class="signuppage__right">
        <div class="logo">
          <img class="logo__img" src="@/assets/dokcho_logo.png" alt="" />
        </div>

        <div>
          <input
            v-model="username"
            placeholder="아이디 (영문자나 숫자의 조합으로 5~20자리)"
          />
        </div>
        <div>
          <input v-model="email" placeholder="이메일" />
          <button
            class="duplicate__button"
            type="submit"
            @click="isEmailDuplicate()"
          >
            이메일중복확인
          </button>
        </div>
        <div>
          <input
            v-model="password"
            type="password"
            placeholder="비밀번호 (영문자+숫자+특수문자 조합으로 8~25자리)"
          />
        </div>
        <div>
          <input
            @keyup.enter="signup()"
            v-model="password2"
            type="password"
            placeholder="비밀번호 확인"
          />
        </div>
        <div class="signup">
          <button class="signup__btn" type="submit" @click="signup()">
            회원가입
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import { mapActions, mapGetters } from 'vuex'

var usernameCheck = /^[a-zA-Z0-9]{5,20}$/

var passwordCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/
var emailCheck =
  /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/

export default {
  data() {
    return {
      email: this.email,

      password: this.password,
      password2: this.password2,
      username: this.username,
      nicknameDuplicate: true,
      emailDuplicate: true
    }
  },
  methods: {
    ...mapActions(['doRefreshToken', 'fetchUserInfo']),
    ...mapGetters(['isAccessTokenExpired']),
    isEmailDuplicate() {
      if (!emailCheck.test(this.email)) {
        alert('정확한 이메일 주소인지 확인해주세요🙏')
      } else {
        axios
          .get(BASE_URL + '/api/v1/user/auth/check/email/' + this.email, {
            email: this.email
          })
          .then((res) => {
            console.log(res)
            if (res.data === false) {
              this.emailDuplicate = false
              alert('이 이메일은 사용하셔도 좋아용.')
            } else {
              this.emailDuplicate = true
              alert('이미 존재하는 이메일 주소입니다.')
            }
          })
          .catch((err) => {
            console.log(err)
          })
      }
    },
    login() {
      console.log(this.username)
      console.log(this.password)
      axios
        .post(
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
        .then((result) => {
          console.log(result)
          localStorage.setItem('accessToken', result.data.accessToken)
          localStorage.setItem('refreshToken', result.data.refreshToken)
          const option = {
            headers: {
              AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
            }
          }
          axios.get(BASE_URL + '/api/v1/user/myinfo', option).then((res) => {
            this.fetchUserInfo(res.data)
            console.log(res.data.newbie)
            if (res.data.newbie) {
              this.$router.push({ name: 'setnickname' })
            } else {
              this.$router.push({ name: 'main' })
            }
          })
          // console.log(localStorage.getItem('userInfo').newbie)
        })

      // .catch((errorr) => {
      //   alert('아이디나 비밀번호를 확인해주세요🙏')
      // })
    },

    signup() {
      if (this.emailDuplicate === true) {
        alert('이메일중복검사를 먼저 진행해주세요.')
      } else if (!passwordCheck.test(this.password)) {
        alert(
          '비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리를 사용해야 해요🙏'
        )
      } else if (!usernameCheck.test(this.username)) {
        alert('아이디는 영문자나 숫자의 조합으로 5~20자리를 사용해야 해요🙏')
      } else if (!emailCheck.test(this.email)) {
        alert('정확한 이메일 주소인지 확인해주세요🙏')
      } else if (this.password === this.password2) {
        axios
          .post(BASE_URL + '/api/v1/user/auth/signup', {
            email: this.email,
            nickname: this.nickname,
            password: this.password,
            region: this.region,
            username: this.username
          })
          .then((res) => {
            console.log(res)
            alert('회원가입을 축하드립니다!')
            this.login()
          })
          .catch((err) => {
            console.log(err)
          })
      } else {
        return alert('비밀번호가 일치하지 않습니다.')
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
  margin: 10px;
  transition: 0.3s;
}
.duplicate__button:hover {
  background-color: #467302;
  color: white;
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
.signuppage {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: white;
}
.signuppage__left {
  margin-left: 20px;
  margin-right: 30px;
}
.signuppage__right {
  margin-right: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.logo {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}
.logo__img {
  width: 15vw;
}
.signup {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}
.signup__btn {
  background-color: #a7c957;
  transition: 0.3s;
  height: 6vh;
}
.signup__btn:hover {
  background-color: #467302;
  color: white;
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
.termsofservice {
  width: 50vw;
  height: 30vw;
  margin: 20px;
  overflow: auto;
  background-image: url('@/assets/hanji.jpeg');
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  border-radius: 50px;
}
.termsofservice__text {
  margin: 4vh;
  height: 40vh;
  flex-direction: column;
  justify-content: center;
  justify-content: center;
  text-align: center;
}

@media screen and (max-width: 850px) {
  .background {
    /* overflow: auto; */
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .signuppage {
    overflow: auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 90vw;
    height: 95%;
    background-color: white;
    border-radius: 40px;
  }
  .signuppage__left {
    /* overflow: auto; */
    margin-left: 5vw;
    margin-right: 5vw;
    margin-top: 35vh;
  }
  .termsofservice {
    width: 75vw;
    height: 30vh;
    margin: 5vw;
    margin-top: 10vh;
    margin-bottom: 0;
    /* margin-top: 15vh; */
    background-image: url('@/assets/hanji.jpeg');
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
    display: flex;
    flex-direction: column;
    justify-content: center;
    border-radius: 50px;
  }
  .termsofservice__text {
    overflow: auto;
    height: 35vh;
    margin: 5vw;
    flex-direction: column;
    justify-content: center;
    text-align: center;
  }
  .signuppage__right {
    margin-left: 5vw;
    margin-right: 5vw;
    width: 80vw;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .logo__img {
    width: 60vw;
  }
  input {
    width: 70vw;
    height: 7vh;
    font-size: 1.5vh;
    background-size: 3vw 3vw;
    padding-left: 4vw;
  }
  button {
    height: 4vh;
    border-radius: 50px;
    border: none;
    width: 40vw;
    margin-left: 0;
    margin-right: 0;
    margin-top: 20px;
    margin-bottom: 20px;
  }
  .signup__btn {
    background-color: #a7c957;
    transition: 0.3s;
    height: 7vh;
  }
}
</style>
