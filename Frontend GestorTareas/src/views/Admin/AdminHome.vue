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
            <p>¿Cuántas tareas ha realizado cada usuario por sector?</p>

            <div v-if="loading" class="text-center mt-3">
              <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">Cargando...</span>
              </div>
            </div>

            <div v-if="error" class="alert alert-danger mt-3">
              {{ error }}
            </div>

            <table v-if="tareasPorSector.length > 0" class="table table-striped mt-3">
              <thead>
              <tr>
                <th>RUT</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Sector</th>
                <th>Tareas Realizadas</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(item, index) in tareasPorSector" :key="index">
                <td>{{ item.rut }}</td>
                <td>{{ item.nombre }}</td>
                <td>{{ item.apellido }}</td>
                <td>{{ getSectorName(item.sector_id) }}</td>
                <td>{{ item.total_tareas_hechas }}</td>
              </tr>
              </tbody>
            </table>

            <div v-if="!loading && tareasPorSector.length === 0" class="alert alert-info mt-3">
              No hay datos de tareas realizadas por sector.
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AdminNavBar from '../../components/NavBars/AdminNavBar.vue'
import { useAuthStore } from '../../stores/auth'
import { ref, onMounted } from 'vue'

export default {
  components: { AdminNavBar },
  setup() {
    const authStore = useAuthStore()
    const tareasPorSector = ref([])
    const loading = ref(false)
    const error = ref(null)

    const fetchTareasPorSector = async () => {
      try {
        loading.value = true
        error.value = null

        const response = await fetch('http://localhost:8080/api/tarea/tareas-realizadas-sector', {
          headers: {
            'Authorization': `Bearer ${authStore.token}`,
            'Content-Type': 'application/json'
          }
        })

        if (!response.ok) {
          throw new Error('Error al obtener los datos')
        }

        tareasPorSector.value = await response.json()
      } catch (err) {
        error.value = err.message || 'Ocurrió un error al cargar los datos'
      } finally {
        loading.value = false
      }
    }

    const getSectorName = (sectorId) => {
      const sectores = {
        1: 'Administración',
        2: 'Limpieza',
        3: 'Mantenimiento'
      }
      return sectores[sectorId] || `Sector ${sectorId}`
    }

    onMounted(() => {
      fetchTareasPorSector()
    })

    return {
      authStore,
      tareasPorSector,
      loading,
      error,
      getSectorName
    }
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

.table {
  margin-top: 20px;
}
</style>