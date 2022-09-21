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
      <button type="button" @click="snapshot">Button</button>
    </div>
  </div>
</template>

<script lang="ts">
import { useRoute, useRouter } from 'vue-router'
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

    const start = () => camera.value?.start()
    const stop = () => camera.value?.stop()
    const pause = () => camera.value?.pause()
    const resume = () => camera.value?.resume()
    const snapshot = async () => {
      const blob = await camera.value?.snapshot({
        width: 1280,
        height: 720
      })

      const url = URL.createObjectURL(blob!)
      console.log(blob)
      const result = ref()
      currentSnapshot.value = URL.createObjectURL(blob!)
      // console.log('사진찍힘')
      // console.log(currentSnapshot.value)

      const route = useRoute()
      const router = useRouter()

      axios({
        url: '/dokcho/judge',
        method: 'post',
        data: {
          data: url
        }
      })
        .then((res) => {
          console.log(res)
          result.value = res
          // router push 하면서 result.value 담아서 보내기
          router.push({
            path: '/search',
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
      changeCamera
    }
  }
})
</script>

<style scoped>
.camera {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.camera__body {
  align-items: center;
  flex-direction: column;
  height: 70vh;
  width: auto;
}
</style>
