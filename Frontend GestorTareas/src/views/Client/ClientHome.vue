<template>
    <ClientNavBar />
    <div class="container mt-4">
        <h1>Bienvenido Cliente</h1>
        <p>Hola, {{ authStore.user?.emailOrNick }}</p>

        <div class="row">
            <div class="col-md-8">
                <div class="card mb-4">
                    <div class="card-body">
                        <h2>Mis Tareas</h2>
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
        </div>
    </div>
</template>

<script setup>
import ClientNavBar from '../../components/NavBars/ClientNavBar.vue'
import { useAuthStore } from '../../stores/auth'

const authStore = useAuthStore()
</script>