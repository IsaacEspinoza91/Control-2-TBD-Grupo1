<template>
    <div class="client-layout">
        <ClientNavBar />
        <div class="content">
            <div class="container mt-4">
                <h1>Hola, {{ authStore.user?.nombreUsuario }}</h1>
                <p>Bienvenido</p>

                <div class="row">
                    <div class="col-md-8">
                        <div class="card mb-4">
                            <div class="card-body">
                                <h2>Tareas del día</h2>
                                <p>Aquí irá el listado de tus tareas.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-body">
                                <h2>Notificaciones</h2>
                                <p>Aquí irán tus notificaciones.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="card mb-4">
                            <div class="card-body">
                                <h2>Tareas de la semana</h2>
                                <p>Aquí tareas dentro de la semana.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="card mb-4">
                            <div class="card-body">
                                <h2>Tareas según sector</h2>
                                <p>Aquí tareas dentro de un sector específico.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import ClientNavBar from '../../components/NavBars/ClientNavBar.vue'
import { useAuthStore } from '../../stores/auth'

const authStore = useAuthStore()
</script>


<style scoped>
.client-layout {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
}

.content {
    flex: 1;
    padding: 20px;
}
</style>