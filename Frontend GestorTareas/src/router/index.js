import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/Auth/LoginView.vue'
import RegisterView from '../views/Auth/RegisterView.vue'
import AdminHome from '../views/Admin/AdminHome.vue'
import ClientHome from '../views/Client/ClientHome.vue'
import ClienteTareas from '../views/Client/ClienteTareas.vue'
import { useAuthStore } from '../stores/auth'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            redirect: '/home'
        },
        {
            path: '/home',
            name: 'home',
            component: HomeView,
            meta: { requiresAuth: false }
        },
        {
            path: '/login',
            name: 'login',
            component: LoginView,
            meta: { requiresAuth: false }
        },
        {
            path: '/register',
            name: 'register',
            component: RegisterView,
            meta: { requiresAuth: false }
        },
        {
            path: '/admin',
            name: 'admin',
            component: AdminHome,
            meta: { requiresAuth: true, role: 'admin' }
        },
        {
            path: '/client',
            name: 'client',
            component: ClientHome,
            meta: { requiresAuth: true, role: 'cliente' }
        },
        {
            path: '/client/tasks/manager',
            name: 'client-task',
            component: ClienteTareas,
            meta: { requiresAuth: true, role: 'cliente' }
        }
    ]
})

router.beforeEach((to, from, next) => {
    const authStore = useAuthStore()

    if (to.meta.requiresAuth && !authStore.isAuthenticated) {
        next('/login')
    } else if (to.meta.requiresAuth && to.meta.role && authStore.user?.tipoUsuario !== to.meta.role) {
        next(authStore.user?.tipoUsuario === 'admin' ? '/admin' : '/client')
    } else {
        next()
    }
})

export default router