<template>
    <div>
        <MainNavBar />
        <div class="container mt-5 main-content">
            <div class="row justify-content-center">
                <div class="col-md-8 text-center">
                    <h1>Bienvenido al Gestor de Tareas</h1>
                    <p class="lead">Una solución completa para administrar tus tareas diarias</p>

                    <div class="mt-4" v-if="!authStore.isAuthenticated">
                        <router-link to="/login" class="btn btn-primary me-2">Iniciar Sesión</router-link>
                        <router-link to="/register" class="btn btn-outline-primary">Registrarse</router-link>
                    </div>

                    <div class="mt-4" v-else>
                        <div class="d-flex justify-content-center gap-3">
                            <router-link :to="authStore.userRole === 'admin' ? '/admin' : '/client'"
                                class="btn btn-primary">
                                Ir a mi panel
                            </router-link>

                            <button @click="showLogoutModal = true" class="btn btn-outline-danger">
                                Cerrar Sesión
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal de confirmación de cierre de sesión -->
        <div v-if="showLogoutModal" class="modal-backdrop fade show"></div>
        <div class="modal fade" :class="{ 'show d-block': showLogoutModal }">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Confirmar cierre de sesión</h5>
                        <button type="button" class="btn-close" @click="showLogoutModal = false"></button>
                    </div>
                    <div class="modal-body">
                        <p>¿Estás seguro que deseas cerrar tu sesión?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" @click="showLogoutModal = false">
                            Cancelar
                        </button>
                        <button type="button" class="btn btn-danger" @click="handleLogout">
                            Cerrar Sesión
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import MainNavBar from '../components/NavBars/MainNavBar.vue'
import { useAuthStore } from '../stores/auth'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export default {
    components: { MainNavBar },
    setup() {
        const authStore = useAuthStore()
        const router = useRouter()
        const showLogoutModal = ref(false)

        const handleLogout = () => {
            authStore.logout()
            showLogoutModal.value = false
            router.push('/login')
        }

        return { authStore, showLogoutModal, handleLogout }
    }
}
</script>

<style scoped>
.main-content {
    margin-top: 80px;
    /* Ajuste para el espacio de la navbar */
}

.modal-backdrop {
    z-index: 1040;
}

.modal {
    z-index: 1050;
}

/* Estilo para el modal cuando está visible */
.modal.show {
    background-color: rgba(0, 0, 0, 0.5);
}
</style>