<template>
  <div class="lists">
    <div class="left">
      <img
        :src="this.imageBaseUrl + '/' + this.friend.profile_img + '.png'"
        alt=""
      />
      <p class="TITLE name">{{ this.friend.nickname }}</p>
    </div>
    <div class="right">
      <font-awesome-icon
        icon="fa-solid fa-trash"
        @click="this.deleteFriend()"
        class="icon"
      />
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL } from '@/constant/BASE_URL'
import Swal from 'sweetalert2'

const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
    title: 'custom-title-class',
    confirmButton: 'btn btn-success',
    cancelButton: 'btn btn-danger'
  }
  // buttonsStyling: false
})

export default {
  props: ['friend'],
  data() {
    return {
      imageBaseUrl: process.env.VUE_APP_S3_URL,
      btn_audio: new Audio(process.env.VUE_APP_S3_URL + '/button.mp3')
    }
  },
  methods: {
    deleteFriend() {
      swalWithBootstrapButtons
        .fire({
          title: '친구를 삭제하시겠어요?',
          text: '되돌릴 수 없어요 🚨',
          icon: 'question',
          showCancelButton: true,
          confirmButtonText: '예',
          cancelButtonText: '아니오',
          reverseButtons: true
        })
        .then((res) => {
          if (res.value) {
            // console.log(result)
            this.fetchDelete()

            swalWithBootstrapButtons.fire(
              '친구가 삭제되었습니다!',
              '안녕 친구야 ...',
              'success'
            )
          } else if (
            /* Read more about handling dismissals below */
            res.dismiss === 'cancel'
          ) {
            // console.log(res)
            swalWithBootstrapButtons.fire(
              '삭제가 취소되었습니다!',
              '친구를 유지하게 되었어요 :)',
              'error'
            )
          }
        })
    },
    fetchDelete() {
      this.btn_audio.play()
      axios
        .delete(BASE_URL + '/api/v1/user/friend/' + this.friend.user_id, {
          headers: {
            AUTHORIZATION: 'Bearer ' + localStorage.getItem('accessToken')
          }
        })
        .then((res) => {
          console.log(res.data)
          this.$emit('getFriendList')
        })
        .catch((err) => console.log(err))
    }
  }
}
</script>

<style scoped>
.lists {
  border: none;
  border-radius: 20px;
  width: 100%;
  height: 10vh;
  margin: 2vh 0;
  background-color: white;
  display: flex;
  justify-content: space-between;
}
.left {
  display: flex;
}
.left > img {
  width: 6vh;
  height: 6vh;
  object-fit: contain;
  margin: 2vh;
  background-color: #eee;
  border-radius: 50%;
}
.name {
  font-size: 2.5vh;
  font-weight: bold;
  line-height: 10vh;
  margin-left: 0.5vw;
}
svg {
  color: #467302;
  width: 2vw;
  height: 10vh;
  margin-right: 3vw;
  transition: 0.3s;
}
svg:hover {
  color: #a7c957;
}
.icon {
  cursor: pointer;
}

.custom-title-class {
  /* font-size: 1.6rem !important; */
  font-family: 'UhBeeSe_hyun' !important;
}
</style>
