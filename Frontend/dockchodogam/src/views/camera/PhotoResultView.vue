<template>
  <LoadingPage v-if="this.isLoading" />
  <NavBar v-show="!this.isLoading" @overflow="overflow" />
  <div
    v-show="!this.isLoading"
    v-if="photoResult !== null && monsterDetail !== null"
    class="result"
  >
    <div class="result__body">
      <div class="result__left">
        <!-- 도감에 없을 때  -->
        <div v-if="this.photoResult.onDogam === false" class="dockchoMonster">
          <undefined-find />
        </div>
        <!-- 도감 O 새로 찾음 -->
        <div
          v-else-if="
            this.photoResult.onDogam === true &&
            this.photoResult.isOverlapped === false
          "
          class="dockchoMonster"
        >
          <new-find :monsterDetail="monsterDetail" />
        </div>
        <!-- 도감 O 중복 찾음 -->
        <div
          v-else-if="
            this.photoResult.onDogam === true &&
            this.photoResult.isOverlapped === true
          "
          class="dockchoMonster"
        >
          <duplicate-find :monsterDetail="monsterDetail" />
        </div>
      </div>
      <div class="result__right">
        <div class="dockchoExplanation__container">
          <div v-if="this.photoResult.plant" class="dockchoExplanation">
            <img
              v-if="this.photoResult.plant.imgUrl !== 'NONE'"
              :src="
                this.imageBaseUrl +
                '/' +
                this.photoResult.plant.plantId +
                '.png'
              "
              alt="flower"
            />
            <h3 v-show="this.photoResult.isDokcho == true" class="notice TITLE">
              독초입니다! 채집 및 섭취에 주의하세요.
            </h3>
            <h3 class="TITLE">{{ this.photoResult.plant.name }}</h3>
            <p
              v-if="
                this.photoResult.plant.familyKorNm &&
                this.photoResult.plant.genusKorNm
              "
            >
              {{ this.photoResult.plant.familyKorNm }}
              {{ this.photoResult.plant.genusKorNm }}
            </p>
            <p v-if="this.photoResult.plant.dstrb">
              <span class="subheading">원산지 :</span>
              {{ this.photoResult.plant.dstrb }}
            </p>
            <p v-if="this.photoResult.plant.flwrDesc">
              <span class="subheading">꽃 🌺 :</span>
              {{ this.photoResult.plant.flwrDesc }}
            </p>
            <p v-if="this.photoResult.plant.fritDesc">
              <span class="subheading">열매 🍈 :</span>
              {{ this.photoResult.plant.fritDesc }}
            </p>
            <p v-if="this.photoResult.plant.grwEvrntDesc">
              <span class="subheading"> 어디서 자라나요? 🌿 </span>:
              {{ this.photoResult.plant.grwEvrntDesc }}
            </p>
            <br />
            <p style="color: gray; font-size: small; text-align: center">
              {{ this.photoResult.plant.cprtCtnt }}
            </p>
          </div>
          <div class="tree_container">
            <img class="tree1" src="@/assets/tree.png" alt="tree" />
            <img class="tree2" src="@/assets/tree.png" alt="tree" />
            <img class="tree3" src="@/assets/tree2.png" alt="tree" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'
import NewFind from '@/components/camera/NewFind.vue'
import DuplicateFind from '@/components/camera/DuplicateFind.vue'
import UndefinedFind from '@/components/camera/UndefinedFind.vue'
import { mapGetters } from 'vuex'
import axios from 'axios'
import LoadingPage from '@/components/main/LoadingPage.vue'

export default {
  components: {
    NavBar,
    LoadingPage,
    NewFind,
    DuplicateFind,
    UndefinedFind
  },
  data() {
    return {
      isLoading: true,
      monsterDetail: {},
      imageBaseUrl: process.env.VUE_APP_PLANTS_S3_URL
    }
  },

  computed: {
    ...mapGetters(['photoResult'])
  },

  methods: {
    fetchMonsterDetail() {
      axios({
        url: `https://j7e201.p.ssafy.io/api/v1/game/monster/detail/${this.photoResult.plant.monsterId}`,
        method: 'GET',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      })
        .then((res) => {
          this.monsterDetail = res.data
        })
        .catch((err) => {
          console.log(err)
        })
    },
    goToCamera() {
      this.$router.push({
        path: '/camera'
      })
    }
  },
  created() {
    this.fetchMonsterDetail()
    setTimeout(() => {
      this.isLoading = false
    }, 2000)
  }
}
</script>

<style scoped>
.result {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-top: 7vh;
}
.result__body {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  align-items: stretch;
}
.result__left {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  text-align: center;
  margin-right: 2%;
  height: 100%;
}
.result__right {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  text-align: center;
}
.result__right > img {
  position: absolute;
  width: 120px;
  top: 10vh;
  right: 10%;
  z-index: 999;
}
.result__footer {
  display: flex;
  justify-content: center;
}
.dockchoMonster {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: auto;
}
.dockchoExplanation__container {
  width: 40vw;
  height: 70vh;
  align-items: center;
  background: url('@/assets/hanji.jpeg') no-repeat;
  background-size: cover;
  border-radius: 50px;
  padding: 5vh;
  text-align: left;
  position: relative;
}

.dockchoExplanation {
  height: 60vh;
  overflow: auto;
}

.dockchoExplanation h3 {
  margin-top: 3vmin;
  margin-bottom: 3vmin;
}

.notice {
  font-size: medium;
  text-align: center;
  color: red;
}
.dockchoExplanation p {
  color: #000000;
}

.subheading {
  color: #000000;
  font-weight: bold;
}

.copyright {
  color: #d0d0d0;
}

.dockchoExplanation > img {
  display: block;
  width: 100%;
  height: 40%;
  margin: auto;
  object-fit: cover;
}

.tree_container img {
  height: 8vh;
  position: absolute;
  bottom: 1vh;
}
.tree_container .tree1 {
  left: -1vw;
}
.tree_container .tree2 {
  height: 5vh;
  left: 1vw;
}
.tree_container .tree3 {
  right: -1vw;
}

@media screen and (max-width: 850px) {
  .result {
    align-items: center;
    flex-direction: column;
  }
  .result__body {
    align-content: center;
    flex-direction: column;
    width: 80vw;
  }
  .result__right {
    height: auto;
    position: relative;
  }
  .result__right > img {
    position: absolute;
    top: -15vw;
    width: 20vw;
  }
  .dockchoExplanation__container {
    width: 100%;
    border-radius: 20px;
  }
  .dockchoExplanation {
    overflow-x: hidden;
    font-size: 0.8rem;
  }
  .dockchoExplanation > img {
    width: 100%;
    height: 20vh;
    margin-bottom: 3vh;
  }
  .tree_container .tree1 {
    left: -5vw;
  }
  .tree_container .tree2 {
    height: 5vh;
    left: 1vw;
  }
  .tree_container .tree3 {
    right: -3vw;
  }
}
</style>
