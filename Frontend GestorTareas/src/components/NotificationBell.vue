<template>
    <div class="dropdown">
        <button class="btn btn-light position-relative" type="button" id="notificationDropdown"
            data-bs-toggle="dropdown">
            <i class="bi bi-bell-fill"></i>
            <span v-if="unreadCount > 0"
                class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                {{ unreadCount }}
            </span>
        </button>
        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="notificationDropdown">
            <li v-if="notifications.length === 0">
                <span class="dropdown-item">No hay notificaciones</span>
            </li>
            <li v-for="notification in notifications" :key="notification.id">
                <a class="dropdown-item" href="#">
                    <div class="d-flex justify-content-between">
                        <span>{{ notification.message }}</span>
                        <small class="text-muted">{{ notification.time }}</small>
                    </div>
                </a>
            </li>
            <li>
                <hr class="dropdown-divider">
            </li>
            <li><a class="dropdown-item" href="#">Ver todas</a></li>
        </ul>
    </div>
</template>

<script setup>
import { ref } from 'vue'

// Esto es temporal, luego se conectará con el backend
const notifications = ref([
    { id: 1, message: 'Nueva tarea asignada', time: '2h ago' },
    { id: 2, message: 'Recordatorio: Reunión a las 15:00', time: '1d ago' }
])

const unreadCount = ref(2)
</script>