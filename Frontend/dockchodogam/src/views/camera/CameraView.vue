<template>
  <NavBar @overflow="overflow" />
  <div class="camera">
    <div class="camera__body">
      <camera
        ref="camera"
        @loading="logEvent('loading')"
        @started="logEvent('started')"
        @error="(error) => logEvent('error: ' + error)"
      >
      </camera>
      <button type="button" @click="snapshot" class="photo__btn">
        <font-awesome-icon icon="fa-solid fa-camera" class="photo__icon" />
      </button>
      <!-- <button @click="goToMain">메인으로 돌아가기</button> -->
    </div>
  </div>
</template>

<script lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { BASE_URL } from '@/constant/BASE_URL'
import NavBar from '@/components/main/NavBar.vue'
import axios from 'axios'
import { defineComponent, onMounted, Ref, ref } from 'vue'
// import Camera from 'simple-vue-camera'
import Camera from '@/components/camera/Camera.vue'

export default defineComponent({
  name: 'App',
  components: {
    Camera,
    NavBar
  },
  setup() {
    const camera = ref<InstanceType<typeof Camera>>()

    const cameras: Ref<MediaDeviceInfo[]> = ref([])

    onMounted(async () => {
      if (!camera.value) return
      try {
        cameras.value = await camera.value.devices(['videoinput'])
      } catch (e) {
        console.error(e)
      }
    })

    const route = useRoute()
    const router = useRouter()
    const start = () => camera.value?.start()
    const stop = () => camera.value?.stop()
    const pause = () => camera.value?.pause()
    const resume = () => camera.value?.resume()
    const snapshot = async () => {
      const blob = await camera.value?.snapshot({
        width: 1280,
        height: 720
      })

      const formdata = new FormData()
      formdata.append('img', blob)

      const url = URL.createObjectURL(blob!)
      console.log(formdata)
      console.log(blob)
      const result = ref()
      currentSnapshot.value = URL.createObjectURL(blob!)
      // console.log('사진찍힘')
      // console.log(currentSnapshot.value)

      axios({
        url: BASE_URL + '/api/v1/dokcho/judge',
        method: 'POST',
        headers: {
          AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken'),
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: formdata
      })
        .then((res) => {
          console.log(res)
          result.value = res
          // router push 하면서 result.value 담아서 보내기
          router.push({
            path: '/camera/result',
            query: { query: result.value }
          })
        })
        .catch((err) => {
          console.log(err)
        })
    }

    const logEvent = (name: string) => console.log(name)

    const currentSnapshot = ref()

    const changeCamera = (event: Event) => {
      const target = event.target as HTMLSelectElement
      camera.value?.changeCamera(target.value)
    }

    const goToMain = () => {
      router.push({
        name: 'main'
      })
    }

    return {
      camera,
      start,
      stop,
      pause,
      resume,
      logEvent,
      snapshot,
      currentSnapshot,
      cameras,
      changeCamera,
      goToMain
    }
  }
})
</script>

<style scoped>
.camera {
  height: 100vh;
  width: 100vw;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  overflow: hidden;
}
.camera__body {
  width: 100%;
  height: 100%;
}
.photo__btn {
  position: absolute;
  bottom: 5vh;
  left: 50vw;
  display: block;
  z-index: 999;
  transform: translate(-50%, 0%);
  width: 7vw;
  height: 7vw;
  border-radius: 50%;
  border: 10px double #467302;
  background: #eee;
  transition: 0.3s;
}
.photo__btn:hover {
  border: 13px double #467302;
  background: white;
}
.photo__btn:hover > .photo__icon {
  width: 2.5vw;
  height: 2.5vw;
}
.photo__icon {
  width: 2vw;
  height: 2vw;
  color: #467302;
  transition: 0.3s;
}
@media screen and (max-width: 850px) {
  .photo__btn {
    width: 10vh;
    height: 10vh;
    bottom: 12vh;
  }
  .photo__icon {
    width: 4vh;
    height: 4vh;
  }
}
</style>

<style>
#video {
  object-fit: cover;
}
</style>
