// import './assets/main.css'
import axios from 'axios'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import App from './App.vue'
import router from './router/index.js'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import * as echarts from 'echarts';
import store from '../store/store.js'
import ECharts from 'vue-echarts';

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
axios.defaults.baseURL = 'http://localhost:8080';
app.use(createPinia())
app.use(router)
app.use(ElementPlus)
app.use(store)
app.component('v-chart', ECharts);
app.config.globalProperties.$echarts = echarts;
app.mount('#app')