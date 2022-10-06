<template>
  <div class="card__container">
    <div
      v-if="monster.got == true && monster.monsterId !== 0"
      class="card"
      :class="{
        card__dokcho: monster.type == 'DOKCHO',
        card__yakcho: monster.type == 'YAKCHO',
        card__japcho: monster.type == 'JAPCHO',
        card__hidden: monster.type == 'HIDDEN'
      }"
    >
      <div class="imgBx">
        <img
          :src="require('@/assets/monster/' + monster.monsterId + '.png')"
          class="card__img monster__img"
          style="-webkit-user-drag: none"
          alt="풀깨비 그림"
          @click="openDetail()"
        />
      </div>
      <div
        :class="{
          card__common: monster.grade == 'COMMON',
          card__rare: monster.grade == 'RARE',
          card__epic: monster.grade == 'EPIC',
          card__legendary: monster.grade == 'LEGENDARY',
          card__special: monster.grade == 'SPECIAL'
        }"
        class="contentBx"
      >
        <div class="contentBx__name">
          <p class="TITLE">00{{ monster.monsterId }}</p>
          <p class="TITLE title">
            {{ monster.name }}몬<span class="moveGG">
              |
              <font-awesome-icon
                @click="goToGG"
                icon="fa-solid fa-magnifying-glass-chart"
              /><span>&nbsp;</span>
              <font-awesome-icon
                @click="shareKakao()"
                icon="fa-solid fa-arrow-up-from-bracket"
              />
            </span>
          </p>
        </div>

        <div class="size">
          <p>
            타입 : {{ this.monsterType }} <br />등급 : {{ this.monterGrade }}
            <br />
            체력 : {{ this.monster.hp }} <br />
            공격력 : {{ this.monster.minAttack }} ~ {{ this.monster.maxAttack }}
          </p>
        </div>
      </div>
    </div>

    <div
      v-else-if="monster.got == false && monster.monsterId !== 0"
      class="card"
    >
      <div class="dontHaveimgBx">
        <img
          :src="require('@/assets/monster/' + monster.monsterId + '.png')"
          class="card__dontHaveimg"
          style="-webkit-user-drag: none"
          alt="획득 못한 풀깨비"
        />
      </div>
      <div class="dontHavecontentBx">
        <div class="dontHavecontentBx__name">
          <p>00{{ monster.monsterId }}</p>
          <h3>?</h3>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import Swal from 'sweetalert2'

export default {
  props: {
    monster: Object
  },
  data() {
    return {
      modal: false,
      monsterDetail: {},
      monsterType: '',
      monterGrade: '',
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      userInfo: JSON.parse(localStorage.getItem('userInfo'))
    }
  },
  methods: {
    async storeMonster(a) {
      this.monsterDetail = a
    },
    openDetail() {
      this.fetchMonsterDetail()
    },
    goToGG() {
      this.$router.push({
        path: '/dokcho/gg',
        query: { query: this.monster.monsterId }
      })
    },
    checkType() {
      if (this.monster.type === 'DOKCHO') {
        this.monsterType = '독초😈'
      } else if (this.monster.type === 'YAKCHO') {
        this.monsterType = '약초🌿'
      } else if (this.monster.type === 'JAPCHO') {
        this.monsterType = '잡초🌻'
      } else {
        this.monsterType = '히든💜'
      }
    },
    checkGrade() {
      if (this.monster.grade === 'COMMOM') {
        this.monterGrade = '일반'
      } else if (this.monster.grade === 'RARE') {
        this.monterGrade = '희귀'
      } else if (this.monster.grade === 'EPIC') {
        this.monterGrade = '영웅'
      } else if (this.monster.grade === 'LEGENDARY') {
        this.monterGrade = '전설'
      } else {
        this.monterGrade = '스페셜'
      }
    },
    fetchMonsterDetail() {
      axios({
        url: `https://j7e201.p.ssafy.io/api/v1/dokcho/detail/${this.monster.plantId}`,
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      })
        .then((res) => {
          this.monsterDetail = res.data.data
          // 히든 몬스터
          if (this.monster.type === 'HIDDEN') {
            return setTimeout(() => {
              Swal.fire({
                title:
                  '<div style="font-family:UhBeeSe_hyun">$name몬</div>'.replace(
                    '$name',
                    this.monster.name
                  ),
                html: `<a style="font-family:UhBeeSe_hyun;text-decoration:none;" href="${this.monsterDetail.engNm}">${this.monster.name}몬의 Github</a>`,
                imageUrl: `${this.monsterDetail.imgUrl}`,
                imageWidth: '80%',
                imageHeight: 200,
                imageAlt: 'Custom image',
                showCloseButton: false,
                showConfirmButton: false,
                position: 'center',
                scrollbarPadding: false
              })
            }, 100)
          } else if (
            // 독초 X 학명 X 설명 X
            !this.monsterDetail.familyKorNm &&
            !this.monsterDetail.flwrDesc &&
            !this.monster.type === 'DOKCHO'
          ) {
            return setTimeout(() => {
              Swal.fire({
                title:
                  '<div style="font-family:UhBeeSe_hyun">$name몬</div>'.replace(
                    '$name',
                    this.monster.name
                  ),
                html: `<a style="color:blue; text-decoration:none;" href="https://j7e201.p.ssafy.io/search/detail?query=${this.monster.plantId}">🔍더 알아보기</a>`,
                imageUrl: `${this.monsterDetail.imgUrl}`,
                imageWidth: '80%',
                imageHeight: 200,
                imageAlt: 'Custom image',
                showCloseButton: false,
                showConfirmButton: false,
                position: 'center',
                scrollbarPadding: false
              })
            }, 100)
          } else if (
            // 독초 O 학명 X 설명 X
            !this.monsterDetail.familyKorNm &&
            !this.monsterDetail.flwrDesc &&
            this.monster.type === 'DOKCHO'
          ) {
            return setTimeout(() => {
              Swal.fire({
                title:
                  '<div style="font-family:UhBeeSe_hyun">$name몬</div>'.replace(
                    '$name',
                    this.monster.name
                  ),
                html: `<b>독초입니다! 채집 및 섭취에 주의하세요!</b><br /><br /><a style="color:blue; text-decoration:none;" href="https://j7e201.p.ssafy.io/search/detail?query=${this.monster.plantId}">🔍더 알아보기</a>`,
                imageUrl: `${this.monsterDetail.imgUrl}`,
                imageWidth: '80%',
                imageHeight: 200,
                imageAlt: 'Custom image',
                showCloseButton: false,
                showConfirmButton: false,
                position: 'center',
                scrollbarPadding: false
              })
            }, 100)
          } else if (
            // 독초 O 학명 O 설명 X
            this.monsterDetail.familyKorNm &&
            !this.monsterDetail.flwrDesc &&
            this.monster.type === 'DOKCHO'
          ) {
            return setTimeout(() => {
              Swal.fire({
                title:
                  '<div style="font-family:UhBeeSe_hyun">$name몬</div>'.replace(
                    '$name',
                    this.monster.name
                  ),
                html: `<b>독초입니다! 채집 및 섭취에 주의하세요!</b><br /><br /><b>${this.monsterDetail.familyKorNm} ${this.monsterDetail.genusKorNm}</b><br /><br /><a style="color:blue; text-decoration:none;" href="https://j7e201.p.ssafy.io/search/detail?query=${this.monster.plantId}">🔍더 알아보기</a>`,
                imageUrl: `${this.monsterDetail.imgUrl}`,
                imageWidth: '80%',
                imageHeight: 200,
                imageAlt: 'Custom image',
                showCloseButton: false,
                showConfirmButton: false,
                position: 'center',
                scrollbarPadding: false
              })
            }, 100)
          } else if (
            // 독초 X 학명 O 설명 X
            this.monsterDetail.familyKorNm &&
            !this.monsterDetail.flwrDesc &&
            !this.monster.type === 'DOKCHO'
          ) {
            return setTimeout(() => {
              Swal.fire({
                title:
                  '<div style="font-family:UhBeeSe_hyun">$name몬</div>'.replace(
                    '$name',
                    this.monster.name
                  ),
                html: `<b>${this.monsterDetail.familyKorNm} ${this.monsterDetail.genusKorNm}</b><br /><br /><a style="color:blue; text-decoration:none;" href="https://j7e201.p.ssafy.io/search/detail?query=${this.monster.plantId}">🔍더 알아보기</a>`,
                imageUrl: `${this.monsterDetail.imgUrl}`,
                imageWidth: '80%',
                imageHeight: 200,
                imageAlt: 'Custom image',
                showCloseButton: false,
                showConfirmButton: false,
                position: 'center',
                scrollbarPadding: false
              })
            }, 100)
          } else if (
            // 독초 O 학명 O 설명 O
            this.monsterDetail.familyKorNm &&
            this.monsterDetail.flwrDesc &&
            this.monster.type === 'DOKCHO'
          ) {
            return setTimeout(() => {
              Swal.fire({
                title:
                  '<div style="font-family:UhBeeSe_hyun">$name몬</div>'.replace(
                    '$name',
                    this.monster.name
                  ),
                html: `<b>독초입니다! 채집 및 섭취에 주의하세요!</b><br /><br /><b>${this.monsterDetail.familyKorNm} ${this.monsterDetail.genusKorNm}</b><p style="font-size:1rem">${this.monsterDetail.flwrDesc}</p><a style="color:blue; text-decoration:none;" href="https://j7e201.p.ssafy.io/search/detail?query=${this.monster.plantId}">🔍더 알아보기</a>`,
                imageUrl: `${this.monsterDetail.imgUrl}`,
                imageWidth: '80%',
                imageHeight: 200,
                imageAlt: 'Custom image',
                showCloseButton: false,
                showConfirmButton: false,
                position: 'center',
                scrollbarPadding: false
              })
            }, 100)
          } else if (
            // 독초 x 학명 x 설명 o
            !this.monsterDetail.familyKorNm &&
            this.monsterDetail.flwrDesc &&
            !this.monster.type === 'DOKCHO'
          ) {
            return setTimeout(() => {
              Swal.fire({
                title:
                  '<div style="font-family:UhBeeSe_hyun">$name몬</div>'.replace(
                    '$name',
                    this.monster.name
                  ),
                html: `<p style="font-size:1rem">${this.monsterDetail.flwrDesc}</p><a style="color:blue; text-decoration:none;" href="https://j7e201.p.ssafy.io/search/detail?query=${this.monster.plantId}">🔍더 알아보기</a>`,
                imageUrl: `${this.monsterDetail.imgUrl}`,
                imageWidth: '80%',
                imageHeight: 200,
                imageAlt: 'Custom image',
                showCloseButton: false,
                showConfirmButton: false,
                position: 'center',
                scrollbarPadding: false
              })
            }, 100)
          } else {
            return setTimeout(() => {
              Swal.fire({
                title:
                  '<div style="font-family:UhBeeSe_hyun">$name몬</div>'.replace(
                    '$name',
                    this.monster.name
                  ),
                html: `<b style="font-family:UhBeeSe_hyun;color:red">독초입니다! 채집 및 섭취에 주의하세요!</b>
                <br /><br /><p style="font-size:1rem">${this.monsterDetail.flwrDesc}</p>
                <a style="color:blue; text-decoration:none;" href="https://j7e201.p.ssafy.io/search/detail?query=${this.monster.plantId}">🔍더 알아보기</a>`,
                imageUrl: `${this.monsterDetail.imgUrl}`,
                imageWidth: '80%',
                imageHeight: 200,
                imageAlt: 'Custom image',
                showCloseButton: false,
                showConfirmButton: false,
                position: 'center',
                scrollbarPadding: false
              })
            }, 100)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    shareKakao() {
      const img = this.imageBaseUrl + '/' + this.monster.monsterId + '.png'
      const name = this.monster.name
      const user = this.userInfo.nickname
      window.Kakao.Link.sendDefault({
        objectType: 'feed',
        content: {
          title: `${name}몬을 잡았어요!`,
          description: `${user}님이 ${name}몬을 획득하셨습니다 ✨`,
          imageUrl: img,
          link: {
            webUrl: 'https://j7e201.p.ssafy.io'
          }
        },
        buttons: [
          {
            title: '도감 모으러 이동!',
            link: {
              webUrl: 'https://j7e201.p.ssafy.io'
            }
          }
        ]
      })
    }
  },
  created() {
    this.checkType()
    this.checkGrade()
  }
}
</script>

<style scoped>
.card__container {
  display: flex;
  justify-content: center;
  flex-direction: row;
}

.speech {
  position: absolute;
  width: 200px;
  height: auto;
}

.card {
  position: relative;
  width: 200px;
  height: 300px;
  background: white;
  border-radius: 20px;
  overflow: hidden;
  margin-bottom: 2vh;
}

.container .card .imgBx {
  transform: translate(10%, 20%);
  width: 100%;
  height: 120px;
  transition: 0.5s;
  display: flex;
  flex-direction: column;
}

.container .card .imgBx p {
  visibility: hidden;
}

.container .card:hover .imgBx p {
  visibility: visible;
  display: inline-block;
}

.contentBx__name .moveGG {
  display: none;
}

.card:hover .moveGG {
  display: inline;
}

.container .card:hover .imgBx {
  transform: translate(20%, 20%);
  width: 80%;
}

.container .card .imgBx .monster__img {
  position: absolute;
  display: block;
  margin: auto;
  width: 80%;
}

.kakao__img {
  margin: 2vmin;
  width: 20px;
  position: absolute;
  align-items: flex-end;

  top: 1%;
}

.container .card .card__dontHaveimg {
  position: absolute;

  transform: translate(30%, 30%);

  width: 60%;

  filter: brightness(0%);
}

.container .card .card__dontHaveimg img {
  position: absolute;
  top: 60%;
  left: 50%;
  width: 80%;
  filter: brightness(0%);
}

.container .card .contentBx {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 35%;
  text-align: center;
  transition: 1s;
  z-index: 10;
  padding: 1vh;
}

.container .card .dontHavecontentBx {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 45%;
  text-align: center;
  transition: 1s;
  z-index: 10;
}

.container .card:hover .contentBx {
  height: 25vh;
}

.container .card .contentBx .title {
  position: relative;
  font-weight: 500;
  font-size: 1.2em;
  color: #000000;
  margin-bottom: 1vw;
}

.container .card .contentBx p {
  font-weight: 500;
  font-size: 0.9em;
  color: #000000;
  margin: 0;
}

.contentBx__name {
  margin-top: 1.5vh;
}

.dontHavecontentBx__name {
  margin-top: 6vh;
}

.container .card .contentBx .size,
.container .card .contentBx .color {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 8px 20px;
  transition: 0.5s;
  opacity: 0;
  visibility: hidden;
  padding-top: 0;
  padding-bottom: 0;
  color: white;
}

.container .card .dontHavecontentBx h3 {
  position: relative;
  font-size: 15;
  color: #000000;
  font-family: 'UhBeeSe_hyun', sans-serif;
  margin-bottom: 1vw;
}

.container .card .dontHavecontentBx p {
  position: relative;
  font-weight: 500;
  font-size: 0.9em;
  font-family: 'UhBeeSe_hyun', sans-serif;
  color: #000000;
  margin: 0;
}

.container .card:hover .contentBx .size {
  opacity: 1;
  visibility: visible;
  transition-delay: 0.5s;
}

.card__common {
  background-color: rgb(166, 166, 166);
}
.card__rare {
  background-color: skyblue;
}
.card__epic {
  background-color: violet;
}
.card__legendary {
  background-color: yellow;
}
.card__special {
  background-image: url(https://img.freepik.com/premium-vector/glitters-rainbow-sky-shiny-rainbows-pastel-color-magic-fairy-starry-skies-and-glitter-sparkles-background-illustration_102902-1299.jpg?w=2000);
}

.card__dokcho {
  box-shadow: 0 0 8px #ff5555;
}

.card__yakcho {
  box-shadow: 0 0 8px #467302;
}

.card__japcho {
  box-shadow: 0 0 8px #ffe140;
}

.card__hidden {
  box-shadow: 0 0 8px #c493ff;
}
svg {
  cursor: pointer;
  transition: 0.3s;
}
svg:hover {
  color: #467302;
}
</style>
