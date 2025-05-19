<template>
    <div class="admin-layout">
        <AdminNavBar />
        <div class="content">
            <div class="container mt-4">
                <h1>Panel de Administración</h1>
                <p>Bienvenido, {{ authStore.user?.nombreUsuario }}</p>
                <div class="card">
                    <div class="card-body">
                        <h2>Funciones de administrador</h2>
                        <p>Funcionalidades.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
import AdminNavBar from '../../components/NavBars/AdminNavBar.vue'
import { useAuthStore } from '../../stores/auth'

export default {
    components: { AdminNavBar },
    setup() {
        const authStore = useAuthStore()
        return { authStore }
    }
}
</script>

<style scoped>
.admin-layout {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
}

.content {
    flex: 1;
    padding: 20px;
}
</style>