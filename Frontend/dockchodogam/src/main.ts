import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { fas } from '@fortawesome/free-solid-svg-icons'
import BootstrapVue3 from 'bootstrap-vue-3'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'
// import Camera from 'simple-vue-camera'

library.add(fas)

createApp(App)
  .component('font-awesome-icon', FontAwesomeIcon)
  // .component('camera', Camera)
  .use(BootstrapVue3)
  .use(store)
  .use(router)
  .mount('#app')
