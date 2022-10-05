<template>
  <NavBar @overflow="overflow" />
  <div class="gallery" :class="this.showMenu ? 'open-menu' : ''">
    <div class="gallery__top TITLE">{{ this.userInfo.nickname }}의 갤러리</div>
    <div class="container">
      <div class="row">
        <div
          class="col"
          :class="item.plant ? 'plantTrue' : ''"
          v-for="(item, i) in this.userGallery"
          :key="i"
        >
          <div class="galleryItem">
            <img :src="item.imgURL" alt="" class="galleryImage front" />
            <img :src="item.suggestionImg" alt="" class="galleryImage back" />
            <div class="galleryItemText TITLE">
              {{ item.picturedTime }}
              <div v-if="item.plant">
                {{ item.suggestionPlantNm }}
              </div>
              <div v-else>null</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <InfiniteLoading
      @infinite="load"
      :slots="{
        complete: '더 이상 사진이 없어요..😉',
        error: '무언가 잘못되었어요!'
      }"
    />
    <div class="block"></div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'
import { BASE_URL } from '@/constant/BASE_URL'
import axios from 'axios'
import InfiniteLoading from 'v3-infinite-loading'
import 'v3-infinite-loading/lib/style.css'

export default {
  components: {
    NavBar,
    InfiniteLoading
  },
  data() {
    return {
      showMenu: false,
      userInfo: JSON.parse(localStorage.getItem('userInfo')),
      userGallery: [],
      galleryPage: 0
    }
  },
  methods: {
    overflow(value) {
      this.showMenu = value
    },
    load($state) {
      const option = {
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
        }
      }
      axios
        .get(
          BASE_URL + '/api/v1/dokcho/archive?page=' + this.galleryPage,
          option
        )
        .then((res) => {
          console.log(res.data.data)
          if (res.data.data.length) {
            this.userGallery = this.userGallery.concat(res.data.data)
            this.galleryPage += 1
            $state.loaded()
          } else {
            $state.complete()
          }
        })
        .catch((err) => {
          console.log(err)
          $state.error()
        })
    }
  }
}
</script>

<style scoped>
.gallery {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: absolute;
  width: 100vw;
  height: 100vh;
  z-index: -9999;
  top: 0;
  left: 0;
  overflow: auto;
  background-image: url('@/assets/hanji.jpeg');
  background-repeat: no-repeat;
  background-size: cover;
}
.gallery__top {
  margin-top: 100px;
  min-height: 70px;
  font-size: 30px;
}
.gallery__main {
  display: grid;
}
.col {
  display: flex;
  justify-content: center;
}
.galleryItem {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
  box-shadow: 0 0 8px black;
  width: 200px;
  height: 270px;
  margin-bottom: 30px;
  perspective: 1000px;
  background-color: white;
}
.galleryImage {
  height: 180px;
  width: 180px;
  margin: 15px 10px;
  object-fit: contain;
  backface-visibility: hidden;
  transition: 1s;
}
.galleryItemText {
  height: 60px;
}
.plantTrue {
  cursor: pointer;
}
.front {
  top: 0;
  position: absolute;
  transform: rotateY(0deg);
}
.plantTrue:hover .front {
  transform: rotateY(180deg);
}
.back {
  transform: rotateY(-180deg);
}
.plantTrue:hover .back {
  transform: rotateY(0deg);
}
.block {
  margin: 1vh;
}
@media screen and (max-width: 850px) {
  .open-menu {
    overflow: hidden;
    width: 100%;
    height: 100%;
    position: fixed;
  }
  /* .galleryItem {
    width: 30vw;
    height: 38vw;
  }
  .galleryImage {
    max-width: 28vw;
    max-height: 28vw;
  } */
}
/* @media screen and (max-width: 450px) {
  .open-menu {
    overflow: hidden;
    width: 100%;
    height: 100%;
    position: fixed;
  }
  .galleryItem {
    width: 60vw;
    height: 75vw;
  }
  .galleryImage {
    max-width: 55vw;
    max-height: 55vw;
  }
} */
/* @media screen and (max-height: 450px) {
  .gallery {
    padding-top: 23vh;
  }
} */
</style>
