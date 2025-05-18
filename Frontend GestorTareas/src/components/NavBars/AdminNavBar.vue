<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
        <div class="container-fluid">
            <router-link class="navbar-brand" to="/admin">Gestor Tareas - Admin</router-link>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#adminNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="adminNavbar">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <router-link class="nav-link" to="/admin">Home</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" to="/admin/tasks">Tareas</router-link>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="adminDropdown" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="../../assets/user-icon.png" alt="Admin" class="rounded-circle me-2" width="30"
                                height="30">
                            {{ authStore.user?.emailOrNick }}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="adminDropdown">
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

export default {
    setup() {
        const authStore = useAuthStore()
        const router = useRouter()

        const logout = () => {
            authStore.logout()
            router.push('/login')
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
    color: #0d6efd;
}
</style>