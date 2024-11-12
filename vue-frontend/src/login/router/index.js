import { createRouter, createWebHistory} from 'vue-router'
import Register from "@/login/components/register.vue";
import Login from "@/login/components/login.vue";
//import 自定义组件名 from 路径
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Login',
            component: Login,
        },
        {
            path: '/register',
            name: 'Register',
            component: Register,
        },
    ]
})

export default router