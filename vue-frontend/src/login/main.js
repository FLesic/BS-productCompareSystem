// import './assets/main.css'
import axios from 'axios'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import App from './App.vue'
import router from './router/index.js'
import 'element-plus/dist/index.css'
import store from '../store/store.js'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
// axios.defaults.baseURL = 'http://localhost:8080';
// 部署使用：
// 使用环境变量设置 axios 的 baseURL
axios.defaults.baseURL = import.meta.env.VITE_BASEURL;
// console.log('Base URL:', import.meta.env.VITE_BASEURL);
app.use(createPinia())
app.use(store)
app.use(router)
app.use(ElementPlus)
app.mount('#app')