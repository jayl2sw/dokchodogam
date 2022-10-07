<template>
  <router-view />
</template>

<script></script>

<style>
@font-face {
  font-family: 'UhBeeSe_hyun';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_five@.2.0/UhBeeSe_hyun.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'Pretendard-Regular';
  src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff')
    format('woff');
  font-weight: 400;
  font-style: normal;
}
* {
  font-family: 'Pretendard-Regular';
}
.TITLE {
  font-family: 'UhBeeSe_hyun';
}
/* 스크롤바 전체 */
*::-webkit-scrollbar {
  width: 15px;
}
/* 스크롤 막대 */
*::-webkit-scrollbar-thumb {
  height: 30vh; /* 스크롤바의 길이 */
  background: #467302;
  border-radius: 15px;
}
@media screen and (max-width: 850px) {
  *::-webkit-scrollbar {
    display: none;
  }
}
</style>
