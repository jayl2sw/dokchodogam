import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import { BASE_URL } from '@/constant/BASE_URL'
import LoginView from '../views/start/LoginView.vue'
import axios from 'axios'

// webpackPrefetch:true 가 추가되어있으면 제일 처음에 데이터 받아옴
// 빈도가 많거나 사이즈가 크면 추가

const isAccessTokenExpired = function isAccessTokenExpired() {
  let expire = false
  // accessToken에서 .(도트)로 분리하여 payload를 가져옵니다.
  if (localStorage.getItem('accessToken') !== null) {
    let base64Payload = localStorage.getItem('accessToken').split('.')[1]
    // URL과 호환되지 않는 문자를 base64 표준 문자로 교체합니다.
    base64Payload = base64Payload.replace(/-/g, '+').replace(/_/g, '/')
    // atob() 메소드로 복호화합니다.
    base64Payload = atob(base64Payload)
    // JSON 객체로 변환합니다.
    const payloadObject = JSON.parse(base64Payload)
    // accessToken의 만료 시간을 확인합니다.
    const currentDate = new Date().getTime() / 1000
    if (payloadObject.exp <= currentDate) {
      console.log('token expired')
      expire = true
    } else {
      console.log('token valid')
    }
  }
  return expire
}

// const requireAuth = () => (from, to, next) => {
//   if (isAccessTokenExpired) return next()
//   next('/')
// }

const doRefreshToken = async function doRefreshToken() {
  if (localStorage.getItem('accessToken') !== '') {
    const token = {
      accessToken: localStorage.getItem('accessToken'),
      refreshToken: localStorage.getItem('refreshToken')
    }
    try {
      const result = await axios.post(
        BASE_URL + '/api/v1/user/auth/refresh',
        token
      )
      if (result.status === 200) {
        console.log('Access-Token이 갱신되었습니다.')
        localStorage.setItem('accessToken', result.data.accessToken)
        localStorage.setItem('refreshToken', result.data.refreshToken)
        console.log('accessToken : ', result.data.accessToken)
        console.log('refreshToken : ', result.data.refreshToken)
        axios.defaults.headers.common.AUTHORIZATION = result.data.accessToken
      } else {
        console.log('다시 로그인 하셈')
        // let err = new Error("Request failed with status code 401");
        // err.status = 401;
        // err.response = {data:{"success":false, "errormessage":"Access-Token이 갱신되었습니다."}};
        // resultErr = err;
      }
    } catch (err) {
      console.log('다시 로그인 하셈')
      // if (!err.response) {
      // err.response = {data:{"success":false, "errormessage":err.message}};
      // }
      // resultErr = err;
    }
  } else {
    console.log('다시 로그인 하셈')
    // let err = new Error("Access-Token does not exist");
    // err.status = 401;
    // err.response = {data:{"success":false, "errormessage":"Access-Token이 없습니다."}};
    // resultErr = err;
  }
}

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'start',
    component: LoginView
  },
  // 비밀번호 찾기
  {
    path: '/findpassword',
    name: 'findpassword',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(
        /* webpackChunkName: "findpassword" */ '../views/start/FindPasswordView.vue'
      )
  },
  // 회원가입
  {
    path: '/signup',
    name: 'signup',
    component: () =>
      import(
        /* webpackChunkName: "signup", webpackPrefetch:true */ '../views/start/SignUpView.vue'
      )
  },
  // 인트로
  {
    path: '/intro',
    name: 'intro',
    component: () =>
      import(
        /* webpackChunkName: "intro", webpackPrefetch:true */ '../views/start/IntroView.vue'
      )
  },
  // 닉네임지정
  {
    path: '/set/nickname',
    name: 'setnickname',
    component: () =>
      import(
        /* webpackChunkName: "intro", webpackPrefetch:true */ '../views/start/SetNicknameView.vue'
      )
  },
  // 메인페이지
  {
    path: '/main',
    name: 'main',
    component: () =>
      import(
        /* webpackChunkName: "main", webpackPrefetch:true */ '../views/main/MainView.vue'
      )
  },
  // 카메라
  {
    path: '/camera',
    name: 'camera',
    component: () =>
      import(
        /* webpackChunkName: "camera", webpackPrefetch:true */ '../views/camera/CameraView.vue'
      )
  },
  // 사진 분석 결과
  {
    path: '/camera/result',
    name: 'result',
    component: () =>
      import(
        /* webpackChunkName: "search" */ '../views/camera/PhotoResultView.vue'
      )
  },
  // 검색 결과
  {
    path: '/search',
    name: 'search',
    component: () =>
      import(
        /* webpackChunkName: "search" */ '../views/search/SearchResultView.vue'
      )
  },
  // 검색 디테일
  {
    path: '/search/detail',
    name: 'searchDetail',
    component: () =>
      import(
        /* webpackChunkName: "search" */ '../views/search/SearchDetailView.vue'
      )
  },
  // 도감
  {
    path: '/encyclopedia',
    name: 'encyclopedia',
    component: () =>
      import(
        /* webpackChunkName: "encyclopedia" */ '../views/encyclopedia/EncyclopediaView.vue'
      )
  },
  // 마이페이지
  {
    path: '/mypage',
    name: 'mypage',
    component: () =>
      import(/* webpackChunkName: "mypage" */ '../views/mypage/MyPageView.vue')
  },
  // 게임 메인 페이지
  {
    path: '/game',
    name: 'game',
    component: () =>
      import(
        /* webpackChunkName: "game", webpackPrefetch:true */ '../views/game/GameMainView.vue'
      )
  },
  // 상점 페이지
  {
    path: '/game/shop',
    name: 'shop',
    component: () =>
      import(/* webpackChunkName: "shop" */ '../views/game/ShopView.vue')
  },
  // 뽑기애니메이션 페이지
  {
    path: '/game/shop/gacha',
    name: 'gacha',
    component: () =>
      import(
        /* webpackChunkName: "shop" */ '../views/game/GachaAnimationView.vue'
      )
  },
  // 패키지 애니메이션 페이지
  {
    path: '/game/shop/package',
    name: 'package',
    component: () =>
      import(
        /* webpackChunkName: "shop" */ '../views/game/PackageAnimationView.vue'
      )
  },
  // 아레나 메인 페이지
  {
    path: '/game/arena',
    name: 'arena',
    component: () =>
      import(
        /* webpackChunkName: "arena", webpackPrefetch:true */ '../views/game/ArenaMainView.vue'
      )
  },
  {
    path: '/game/arena/ingame',
    name: 'arenaInGame',
    component: () =>
      import(
        /* webpackChunkName: "ingame" */ '../views/game/ArenaInGameView.vue'
      )
  },
  {
    path: '/game/arena/chinsunGame',
    name: 'arenaChinsunGame',
    component: () =>
      import(
        /* webpackChunkName: "ingame" */ '../views/game/ArenaChinsunInGameView.vue'
      )
  },
  // 덱 수정
  {
    path: '/game/deck',
    name: 'deck',
    component: () =>
      import(/* webpackChunkName: "deck" */ '../views/game/DeckView.vue')
  },
  // 친구 관리
  {
    path: '/game/friend',
    name: 'friend',
    component: () =>
      import(/* webpackChunkName: "friend" */ '../views/game/FriendView.vue')
  },
  // 독초지지
  {
    path: '/game/gg',
    name: 'gg',
    component: () =>
      import(/* webpackChunkName: "gg" */ '../views/game/DokchoGG.vue')
  },
  // 독초지지
  {
    path: '/dokcho/gg/',
    name: 'dcgg',
    component: () =>
      import(/* webpackChunkName: "gg" */ '../views/game/DokchoData.vue')
  },
  // 어드민
  {
    path: '/admin',
    name: 'admin',
    component: () =>
      import(/* webpackChunkName: "deck" */ '../views/admin/AdminView.vue')
  },
  // 404 에러
  {
    path: '/404',
    name: 'notFound',
    component: () =>
      import(/* webpackChunkName: "404" */ '../views/error/NotFound404.vue')
  },
  // 404로 보냄
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404'
  },
  {
    path: '/oauth',
    name: 'oauth',
    component: () =>
      import(
        /* webpackChunkName: "oauth" */ '../components/start/KakaoLogin.vue'
      )
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach(async (to, from, next) => {
  console.log(window.location.href)
  let token = ''
  if (localStorage.getItem('accessToken')) {
    token = localStorage.getItem('accessToken')
  }
  if (
    to.path === '/' ||
    to.path === '/signup' ||
    to.path === '/findpassword' ||
    to.path === '/oauth' ||
    to.path === '/oauth2/authorization/kakao'
  ) {
    next()
  } else if (token) {
    console.log(isAccessTokenExpired())

    if (!isAccessTokenExpired()) {
      return next()
    } else {
      doRefreshToken()
    }
  } else {
    console.log('로그인 해주세용~💋')
    alert('로그인 해주세용~💋')
    return next({ path: '/' })
  }

  // if (window.location.href === 'http://localhost:8080/') {
  //   console.log(window.location.href)
  //   console.log(localStorage.getItem('accessToken'))
  //   return next()
  // } else {
  //   if (isAccessTokenExpired()) {
  //     await doRefreshToken()
  //     return next()
  //   } else {
  //     alert('로그인 해주세용~💋')
  //     router.push({ path: '/' })
  //     return next()
  //   }
  // }
})

// router.beforeEach(async (to, from) => {
//   console.log(window.location.href)
//   if (window.location.href === 'http://localhost:8080/') {
//     console.log(window.location.href)
//     console.log(localStorage.getItem('accessToken'))
//     return '/'
//   } else {
//     if (isAccessTokenExpired()) {
//       await doRefreshToken()
//       return '/'
//     } else {
//       alert('로그인 해주세용~💋')
//       return '/'
//     }
//   }
// })

export default router
