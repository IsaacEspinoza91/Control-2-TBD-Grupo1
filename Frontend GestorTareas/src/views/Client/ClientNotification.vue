<template>
  <div class="client-layout">
    <ClientNavBar />

    <div class="container mt-4">
      <h3 class="mb-3">📬 Todas tus notificaciones</h3>
      <ul class="list-group">
        <li
          v-for="notification in notifications"
          :key="notification.id"
          class="list-group-item d-flex align-items-start"
        >
          <input
            type="checkbox"
            class="form-check-input me-3 mt-1"
            :disabled="notification.visto"
            v-model="notification.visto"
            @change="marcarComoVisto(notification)"
          />
          <div>
            <div :class="notification.visto ? 'text-secondary' : 'fw-bold'">
              {{ notification.mensaje }}
            </div>
            <div class="text-muted small">
              Fecha: {{ notification.fechaenvio }} — Tarea ID: {{ notification.tarea_id }}
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import ClientNavBar from '../../components/NavBars/ClientNavBar.vue'
import { useAuthStore } from '../../stores/auth'
import { ref, onMounted } from 'vue'
import api from '../../api'

const authStore = useAuthStore()
const notifications = ref([])

const cargarNotificaciones = async () => {
  try {
    const res = await api.get(`/notificacion/usuario/${authStore.user.idUsuario}`, {
      headers: {
        Authorization: `Bearer ${authStore.token}`,
      },
    })
    notifications.value = res.data
  } catch (error) {
    console.error('Error al cargar notificaciones:', error)
  }
}

const marcarComoVisto = async (notif) => {
  if (notif.visto === true) {
    try {
      await api.patch(`/notificacion/marcar/${notif.id}/visto`, null, {
        headers: {
          Authorization: `Bearer ${authStore.token}`,
        },
      })
    } catch (error) {
      console.error('Error al marcar como visto:', error)
    }
  }
}


onMounted(() => {
  cargarNotificaciones()
})
</script>

<style scoped>
.container {
  max-width: 700px;
}
</style>
