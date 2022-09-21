<template>
  <NavBar @overflow="overflow" />
  <div class="mypage">
    <div class="mypage__left">
      <div class="myDockcho">
        <img src="@/assets/loading/5.png" alt="representative" />
      </div>
      <button class="change__dockcho">대표 독초몬 변경</button>
    </div>
    <div class="mypage__right">
      <div class="myProfile">
        <div class="myProfile__medal">
          <img src="@/assets/medal.png" alt="" />
        </div>
        <div>
          <p class="TITLE myProfile__name">username님</p>
          <p class="myProfile__contents">
            현재 재화 : <span class="emphasize">{ }</span>냥
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
        <button class="quit__btn">회원 탈퇴</button>
      </div>
      <div class="changePw__form" :class="this.isNone ? '' : 'displayNone'">
        <div class="changePw__inputs">
          <input type="password" placeholder="현재 비밀번호" />
          <input type="password" placeholder="새 비밀번호" />
          <input type="password" placeholder="새 비밀번호 확인" />
        </div>
        <div class="changePw__btn">
          <button @click="this.displayNone()" class="cancel__btn">취소</button>
          <button class="complete__btn">수정 완료</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'

export default {
  components: {
    NavBar
  },
  data() {
    return {
      showMenu: false,
      isNone: false
    }
  },
  methods: {
    overflow(value) {
      this.showMenu = value
    },
    displayNone() {
      this.isNone = !this.isNone
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
    font-size: 2vw;
  }
  .quit__btn {
    width: 20vw;
    font-size: 2vw;
  }
  .changePw__inputs input {
    height: 5vh;
    padding: 2vh 3vw;
    font-size: 3vw;
  }
  .cancel__btn {
    width: 20vw;
    font-size: 1vw;
  }
  .complete__btn {
    width: 20vw;
    font-size: 1vw;
  }
}
</style>
