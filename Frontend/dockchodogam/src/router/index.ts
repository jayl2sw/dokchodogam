import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import LoginView from '../views/start/LoginView.vue'

// webpackPrefetch:true 가 추가되어있으면 제일 처음에 데이터 받아옴
// 빈도가 많거나 사이즈가 크면 추가

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
    path: '/result',
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
        /* webpackChunkName: "arena" */ '../views/game/ArenaInGameView.vue'
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
      import(/* webpackChunkName: "deck" */ '../views/game/FriendView.vue')
  },
  // 친구 관리
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
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
