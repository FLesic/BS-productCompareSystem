import { createRouter, createWebHistory} from 'vue-router'
//import 自定义组件名 from 路径
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        /*
        {
            path: '/',
            redirect: ''
        },
        {
            path: '/...'
            component: 自定组件名（和import一致）
        }
        ...
        */
    ]
})

export default router