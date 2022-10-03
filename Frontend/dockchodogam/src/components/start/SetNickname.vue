<template>
  <div class="background">
    <div class="setnicknamepage">
      <img class="setnicknamepage__img" src="@/assets/dokcho_logo.png" alt="" />
      <div class="setnicknamepage__text">
        독초도감에 새겨질 당신의 이름을 알려주세요 🐯
      </div>

      <div>한글이나 영문자, 숫자의 조합으로 1~4자리</div>
      <input v-model="nickname" placeholder="닉네임" />

      <div class="setnicknamepage__button">
        <button
          class="setnickname__button"
          type="submit"
          @click="isNicknameDuplicate()"
        >
          중복확인
        </button>

        <button class="login__button" type="submit" @click="setnickname()">
          완료
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import { mapActions } from 'vuex'
import swal from 'sweetalert'

var nicknameCheck = /^[가-힣a-zA-Z0-9]{1,4}$/

export default {
  data() {
    return {
      nickname: this.nickname,
      nicknameDuplicate: true
    }
  },
  methods: {
    ...mapActions(['fetchnowUserInfo']),
    isNicknameDuplicate() {
      if (!nicknameCheck.test(this.nickname)) {
        swal({
          title:
            '닉네임은 한글이나 영문자, 숫자의 조합으로 1~4자리를 사용해야 해요🙏',
          text: '🐯',
          icon: 'warning',
          buttons: false,
          timer: 1500
        })
      } else {
        axios
          .get(BASE_URL + '/api/v1/user/auth/check/nickname/' + this.nickname, {
            nickname: this.nickname
          })
          .then((res) => {
            console.log(res)
            if (res.data === false) {
              this.nicknameDuplicate = false

              swal({
                title: '이 닉네임은 사용하셔도 좋아용😎',
                text: '🐯',
                icon: 'success',
                buttons: false,
                timer: 1500
              })
            } else {
              this.nicknameDuplicate = true
              swal({
                title: '이미 존재하는 닉네임입니다😥',
                text: '🐯',
                icon: 'warning',
                buttons: false,
                timer: 1500
              })
            }
          })
          .catch((err) => {
            console.log(err)
          })
      }
    },
    setnickname() {
      if (this.nicknameDuplicate === true) {
        alert('닉네임중복검사를 먼저 진행해주세요.')
      } else if (!nicknameCheck.test(this.nickname)) {
        swal({
          title:
            '닉네임은 한글이나 영문자, 숫자의 조합으로 1~4자리를 사용해야 해요🙏',
          text: '🐯',
          icon: 'warning',
          buttons: false,
          timer: 1500
        })
      } else {
        const option = {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        }
        axios
          .put(
            BASE_URL + '/api/v1/user/set/nickname?nickname=' + this.nickname,
            null,
            option
          )
          .then((res) => {
            this.fetchnowUserInfo()
            console.log(res)
            swal({
              title: this.nickname + '님, 환영합니다!🥰',
              text: '부디 허준을 도와 독초도감을 채워주세요!',
              icon: 'success',
              buttons: false,
              timer: 1500
            })
            this.$router.push({ name: 'intro' })
          })
      }
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
.setnickname__button:hover {
  background-color: #467302;
  color: white;
}

.login__button:hover {
  background-color: #467302;
  color: white;
}

.setnicknamepage__img {
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

.setnicknamepage {
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

.setnicknamepage__text {
  font-size: 2vw;
  margin-bottom: 3vh;
}

.setnicknamepage__button {
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

  .setnicknamepage {
    height: 95%;
    border-radius: 40px;
  }

  .setnicknamepage__img {
    width: 80vw;
  }

  .setnicknamepage__text {
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
