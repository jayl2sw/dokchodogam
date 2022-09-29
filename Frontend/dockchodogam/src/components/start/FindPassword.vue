<template>
  <div class="background">
    <div class="findpasswordpage">
      <img
        class="findpasswordpage__img"
        src="@/assets/dokcho_logo.png"
        alt=""
      />
      <div class="findpasswordpage__text">
        회원가입할 때 입력하신 이메일로 비밀번호 변경 메일을 보내드릴게요 🐯
      </div>

      <input v-model="username" placeholder="아이디를 입력하세요" />
      <input v-model="email" placeholder="이메일을 입력하세요" />

      <div class="findpasswordpage__button">
        <button
          class="findpassword__button"
          type="submit"
          @click="findpassword()"
        >
          비밀번호찾기
        </button>
        <button class="login__button" type="submit" @click="to_login()">
          로그인
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'

var emailCheck =
  /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/

export default {
  data() {
    return {
      email: this.email
    }
  },
  methods: {
    async findpassword() {
      if (!emailCheck.test(this.email)) {
        alert('정확한 이메일 주소인지 확인해주세요🙏')
      } else {
        await axios
          .put(BASE_URL + '/api/v1/user/auth/findpw?email=' + this.email)
          .then((res) => {
            console.log(res)
            alert('비밀번호 변경 메일을 보내드렸어요!')
          })
          .catch((err) => {
            console.log(err)
          })
      }
    },
    to_login() {
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
button {
  height: 7vh;
  border-radius: 50px;
  border: none;
  width: 15vw;
  margin: 10px;
}
.findpassword__button:hover {
  background-color: #467302;
  color: white;
}

.login__button:hover {
  background-color: #467302;
  color: white;
}

.findpasswordpage__img {
  width: 30vw;
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

.findpasswordpage {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 90vw;
  background-color: white;
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

.findpasswordpage__text {
  font-size: 2vw;
  margin-bottom: 3vh;
}

.findpasswordpage__button {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

@media screen and (max-width: 850px) {
  button {
    height: 6vh;
    border-radius: 50px;
    border: none;
    width: 35vw;
    margin: 10px;
  }

  .findpasswordpage {
    height: 95%;
    border-radius: 40px;
  }

  .findpasswordpage__img {
    width: 80vw;
  }

  .findpasswordpage__text {
    font-size: 5vw;
    text-align: center;
    margin: 3vw;
  }
  input {
    width: 70vw;
    height: 8vh;
    font-size: 2vh;
    background-size: 3vw 3vw;
    padding-left: 4vw;
  }
}
</style>
