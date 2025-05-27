<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-success fixed-top">
        <div class="container-fluid">
            <router-link class="navbar-brand" to="/client">Gestor Tareas</router-link>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#clientNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="clientNavbar">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <router-link class="nav-link" to="/client">Home</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" to="/client/tasks/manager">Tareas Manager</router-link>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item me-3">
                        <NotificationBell />
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="clientDropdown" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="../../assets/user-icon.png" alt="Cliente" class="rounded-circle me-2" width="30"
                                height="30">
                            {{ authStore.user?.nombreUsuario }}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="clientDropdown">
                            <li>
                                <button class="dropdown-item d-flex align-items-center" @click="logout">
                                    <i class="bi bi-box-arrow-right me-2"></i>
                                    Cerrar Sesión
                                </button>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script>
import { useAuthStore } from '../../stores/auth'
import { useRouter } from 'vue-router'
import NotificationBell from '../NotificationBell.vue'

export default {
    components: { NotificationBell },
    setup() {
        const authStore = useAuthStore()
        const router = useRouter()

        const logout = () => {
            authStore.logout()
            router.push('/home')
        }

        return { authStore, logout }
    }
}
</script>

<style scoped>
.navbar {
    height: 60px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.dropdown-item {
    cursor: pointer;
    transition: background-color 0.2s;
}

.dropdown-item:hover {
    background-color: #f8f9fa;
    color: #198754;
}
</style>