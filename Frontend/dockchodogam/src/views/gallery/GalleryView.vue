<template>
  <NavBar @overflow="overflow" />
  <div class="gallery" :class="this.showMenu ? 'open-menu' : ''">
    <div class="gallery__top TITLE">{{ this.userInfo.nickname }}의 갤러리</div>
    <div class="gallery__main">
      <div
        class="galleryItem"
        :class="item.plant ? 'plantTrue' : ''"
        v-for="(item, i) in this.userGallery"
        :key="i"
      >
        {{ item.picturedTime }}
        <img :src="item.imgURL" alt="" class="galleryImage front" />
        <img :src="item.suggestionImg" alt="" class="galleryImage back" />
        <div v-if="item.plant">
          {{ item.suggestionPlantNm }}
        </div>
        <div v-else>null</div>
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
  padding-top: 13vh;
}
.gallery__top {
  height: 10vh;
}
.gallery__main {
  width: 80vw;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
.galleryItem {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
  border-radius: 5px;
  border: 1px solid black;
  width: 20vw;
  height: 26vw;
  margin: 3vw;
  perspective: 80vw;
  background-color: #ececec;
}
.galleryImage {
  max-height: 19vw;
  max-width: 19vw;
  border-radius: 5px;
  object-fit: contain;
  backface-visibility: hidden;
  transition: 1s;
}
.plantTrue {
  cursor: pointer;
}
.front {
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
  .galleryItem {
    width: 30vw;
    height: 38vw;
  }
  .galleryImage {
    max-width: 28vw;
    max-height: 28vw;
  }
}
@media screen and (max-width: 450px) {
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
}
@media screen and (max-height: 450px) {
  .gallery {
    padding-top: 23vh;
  }
}
</style>
