<template>
  <div class="dropdown">
    <button class="btn btn-light position-relative" type="button" id="notificationDropdown"
      data-bs-toggle="dropdown" aria-expanded="false">
      <i class="bi bi-bell-fill fs-5"></i>
      <span v-if="unreadCount > 0"
        class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
        {{ unreadCount }}
      </span>
    </button>
    <ul class="dropdown-menu dropdown-menu-end shadow" aria-labelledby="notificationDropdown" style="width: 500px;">
      <li v-if="notifications.length === 0">
        <span class="dropdown-item text-muted">No hay notificaciones</span>
      </li>
      <li v-for="notification in notifications" :key="notification.id" class="d-flex align-items-start">
        <input type="checkbox" class="form-check-input ms-2 me-2 mt-2"
               :disabled="notification.visto"
               v-model="notification.visto"
               @change="marcarComoVisto(notification)" />
        <div class="dropdown-item small w-100">
          <div class="fw-bold">{{ notification.mensaje }}</div>
          <div class="text-muted">
            Fecha: {{ notification.fechaenvio }}<br />
            Tarea ID: {{ notification.tarea_id }}
          </div>
        </div>
      </li>
      <li><hr class="dropdown-divider my-1" /></li>
      <li>
        <router-link class="dropdown-item text-center fw-semibold" to="/client/notification">
            Ver todas
        </router-link>
    </li>
    </ul>
  </div>
</template>

<script setup>
import { useAuthStore } from '../stores/auth'
import { ref, onMounted } from 'vue'
import axios from '../api'

const authStore = useAuthStore()
const notifications = ref([])
const unreadCount = ref(0)

const cargarNotificaciones = async () => {
  try {
    const res = await axios.get(`/notificacion/usuario-no-visto/${authStore.user.idUsuario}`, {
      headers: {
        'Authorization': `Bearer ${authStore.token}`
      }
    })

    notifications.value = res.data
    unreadCount.value = notifications.value.filter(n => !n.visto).length

  } catch (error) {
    console.error('Error al cargar notificaciones:', error)
  }
}

const marcarComoVisto = async (notif) => {
  try {
    await axios.patch(`/notificacion/marcar/${notif.id}/visto`, {}, {
      headers: {
        'Authorization': `Bearer ${authStore.token}`
      }
    })

    notif.visto = true
  } catch (error) {
    console.error(`Error al marcar notificación ${notif.id} como vista:`, error)
  }
}


onMounted(() => {
  cargarNotificaciones()
})
</script>
