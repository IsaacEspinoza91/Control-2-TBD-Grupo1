<template>
  <div class="client-layout">
    <ClientNavBar />
    <div class="content">
      <div class="container mt-4">
        <h1>Hola, {{ authStore.user?.nombreUsuario }}</h1>
        <p>Bienvenido</p>

        <!-- Componente de consultas estadísticas -->
        <div class="query-container mb-4" style="width: 80%;">
          <div class="d-flex align-items-center mb-2">
            <!-- Botón desplegable más ancho -->
            <div class="dropdown me-2 flex-grow-1">
              <button
                class="btn btn-outline-primary dropdown-toggle w-100"
                type="button"
                @click="toggleDropdown"
                aria-expanded="false"
                style="text-align: left;"
              >
                Consultas
              </button>
              <ul v-if="showDropdown" class="dropdown-menu show w-100">
                <li>
                  <a
                    class="dropdown-item"
                    href="#"
                    @click.prevent="selectQuery('por-sector')"
                  >
                    ¿Cuántas tareas ha hecho el usuario por sector?
                  </a>
                </li>
                <li>
                  <a
                    class="dropdown-item"
                    href="#"
                    @click.prevent="selectQuery('sector-2km')"
                  >
                    ¿Cuál es el sector con más tareas completadas en un radio de 2 km del usuario?
                  </a>
                </li>
                <li>
                  <a
                    class="dropdown-item"
                    href="#"
                    @click.prevent="selectQuery('sector-5km')"
                  >
                    ¿Cuál es el sector con más tareas completadas en un radio de 5 km del usuario?
                  </a>
                </li>
                <li>
                  <a
                    class="dropdown-item"
                    href="#"
                    @click.prevent="selectQuery('distancia-promedio-usuario')"
                  >
                    ¿Cuál es el promedio de distancia de las tareas completadas respecto a la ubicación del usuario?
                  </a>
                </li>
                <li>
                  <a
                    class="dropdown-item"
                    href="#"
                    @click.prevent="selectQuery('distancia-promedio-registro')"
                  >
                    ¿Cuál es el promedio de distancia entre las tareas completadas y el punto registrado del usuario?
                  </a>
                </li>
              </ul>
            </div>

            <!-- Botón Ejecutar -->
            <button
              class="btn btn-success flex-shrink-0"
              @click="executeQuery"
              :disabled="!selectedQuery"
              style="min-width: 100px;"
            >
              Ejecutar!
            </button>
          </div>

          <!-- Consulta seleccionada -->
          <div v-if="selectedQuery" class="mt-2 p-3 bg-light rounded">
            <strong>Consulta seleccionada:</strong>
            {{ queryDescriptions[selectedQuery] }}
          </div>

          <!-- Resultados -->
          <div class="results mt-3 p-3 border rounded bg-white">
            <h5 class="mb-3">Resultado:</h5>
            <div v-if="loading" class="text-center py-4">
              <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">Cargando...</span>
              </div>
              <p class="mt-2">Procesando consulta...</p>
            </div>
            <div v-else-if="resultado">
              <!-- Resultado para consulta por sector -->
              <div v-if="selectedQuery === 'por-sector'">
                <div v-if="resultado.length > 0" class="alert alert-success">
                  <h6 class="mb-3">Tareas realizadas por sector:</h6>
                  <ul class="list-group">
                    <li v-for="item in resultado" :key="item.sector" class="list-group-item d-flex justify-content-between align-items-center">
                      {{ item.sector }}
                      <span class="badge bg-primary rounded-pill">{{ item.totalTareas }}</span>
                    </li>
                  </ul>
                </div>
                <div v-else class="alert alert-info">
                  No hay tareas realizadas en ningún sector.
                </div>
              </div>

              <!-- Resultado para consultas de distancia promedio -->
              <div v-else-if="selectedQuery.includes('distancia-promedio')">
                <div v-if="resultado.promedioDistancia !== undefined" class="alert alert-success">
                  <p><strong>Usuario:</strong> {{ resultado.nombreUsuario }} {{ resultado.apellidoUsuario }}</p>
                  <p><strong>Promedio de distancia:</strong> {{ (resultado.promedioDistancia / 1000).toFixed(2) }} km</p>
                </div>
                <div v-else class="alert alert-info">
                  No se encontraron resultados para esta consulta.
                </div>
              </div>

              <!-- Resultado para otras consultas -->
              <div v-else>
                <div v-if="resultado.sectorId" class="alert alert-success">
                  <p class="mb-1"><strong>Sector:</strong> {{ resultado.sectorNombre }}</p>
                  <p class="mb-1"><strong>Tareas realizadas:</strong> {{ resultado.tareasRealizadas }}</p>
                  <p class="mb-0"><strong>Radio de búsqueda:</strong> {{ resultado.radioKm || (selectedQuery === 'sector-2km' ? '2' : '5') }} km</p>
                </div>
                <div v-else class="alert alert-info">
                  No se encontraron resultados para esta consulta.
                </div>
              </div>
            </div>
            <div v-else class="text-muted p-2">
              Selecciona una consulta y haz clic en "Ejecutar!" para ver los resultados.
            </div>
            <div v-if="error" class="alert alert-danger mt-3">
              <strong>Error:</strong> {{ error }}
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-8">
            <div class="card mb-4">
              <div class="card-body">
                <h2>Tareas del día</h2>
                <div v-if="loadingTareas" class="text-center py-2">
                  <div class="spinner-border text-primary spinner-border-sm" role="status">
                    <span class="visually-hidden">Cargando tareas...</span>
                  </div>
                  <small class="ms-2">Cargando tareas...</small>
                </div>
                <ul v-else-if="tareasUsuario.length > 0">
                  <li v-for="tarea in tareasUsuario" :key="tarea.id">
                    <strong>{{ tarea.titulo }}</strong>: {{ tarea.descripcion }} (Estado: {{ tarea.estado }})
                  </li>
                </ul>
                <div v-else-if="!loadingTareas">
                  <p>No tienes tareas asignadas.</p>
                </div>
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
import { ref, onMounted } from 'vue'
import axios from '../../api' // Importamos la instancia de axios configurada

const authStore = useAuthStore()

// Estado del componente
const showDropdown = ref(false)
const selectedQuery = ref(null)
const resultado = ref(null)
const error = ref(null)
const loading = ref(false)

const tareasUsuario = ref([])
const loadingTareas = ref(false)
const errorTareas = ref(null)

const queryDescriptions = {
  'por-sector': '¿Cuántas tareas ha hecho el usuario por sector?',
  'sector-2km': '¿Cuál es el sector con más tareas completadas en un radio de 2 km del usuario?',
  'sector-5km': '¿Cuál es el sector con más tareas completadas en un radio de 5 km del usuario?',
  'distancia-promedio-usuario': '¿Cuál es el promedio de distancia de las tareas completadas respecto a la ubicación del usuario?',
  'distancia-promedio-registro': '¿Cuál es el promedio de distancia entre las tareas completadas y el punto registrado del usuario?'
}

const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value
}

const selectQuery = (queryKey) => {
  selectedQuery.value = queryKey
  showDropdown.value = false
}

const fetchTareasUsuario = async () => {
  if (!authStore.user?.idUsuario) {
    console.log('Usuario no definido, no se pueden cargar las tareas.')
    return
  }
  loadingTareas.value = true
  errorTareas.value = null
  tareasUsuario.value = []
  try {
    const response = await axios.get(`/usuario/${authStore.user.idUsuario}`, {
      headers: {
        Authorization: `Bearer ${authStore.token}`
      }
    })
    tareasUsuario.value = response.data
  } catch (err) {
    console.error('Error al cargar las tareas del usuario:', err)
    errorTareas.value = err.response?.data?.message || 'Error al cargar las tareas.'
  } finally {
    loadingTareas.value = false
  }
}

const executeQuery = async () => {
  console.group('Iniciando executeQuery');
  
  // 1. Verificación inicial
  if (!selectedQuery.value) {
    console.error('❌ No hay consulta seleccionada');
    error.value = 'Debes seleccionar una consulta primero';
    console.groupEnd();
    return;
  }

  if (!authStore.user?.idUsuario) {
    console.error('❌ No hay ID de usuario', {
      storeUser: authStore.user,
      localStorage: localStorage.getItem('auth')
    });
    error.value = 'No se encontró información del usuario';
    console.groupEnd();
    return;
  }

  loading.value = true;
  error.value = null;
  resultado.value = null;

  try {
    // 2. Construcción del endpoint
    let endpoint = '';
    let params = {};

    switch (selectedQuery.value) {
      case 'por-sector':
        endpoint = `/tarea/por-sector/${authStore.user.nombreUsuario}`;
        break;
      case 'sector-2km':
        endpoint = `/usuario/${authStore.user.idUsuario}/sector-mas-tareas-realizadas-2km`;
        break;
      case 'sector-5km':
        endpoint = `/usuario/${authStore.user.idUsuario}/sector-mas-tareas-realizadas-5km`;
        break;
      case 'distancia-promedio-registro':
        endpoint = `/usuario/promedio-distancia/${authStore.user.idUsuario}`;
        break;
      case 'distancia-promedio-usuario':
        endpoint = `/usuario/promedio-de-distancia`;
        params = {
          longitud: authStore.user?.longitud,
          latitud: authStore.user?.latitud
        };
        break;
    }

    console.log('🔍 Datos de la petición:', {
      endpoint,
      selectedQuery: selectedQuery.value,
      user: authStore.user,
      token: authStore.token
    });

    // 3. Headers detallados
    const headers = {
      'Authorization': `Bearer ${authStore.token}`,
      'Content-Type': 'application/json',
      'X-Debug-Request': 'true'
    };

    console.log('📤 Headers enviados:', headers);

    // 4. Realizar petición
    const response = await axios.get(endpoint, { 
      headers,
      params
    });

    console.log('📥 Respuesta exitosa:', {
      status: response.status,
      data: response.data,
      headers: response.headers
    });

    resultado.value = response.data;

  } catch (err) {
    console.error('🔥 Error completo:', {
      message: err.message,
      code: err.code,
      config: {
        url: err.config?.url,
        headers: err.config?.headers,
        method: err.config?.method
      },
      response: {
        status: err.response?.status,
        data: err.response?.data,
        headers: err.response?.headers
      },
      stack: err.stack
    });

    error.value = err.response?.data?.message || 
                 (err.response?.status === 403 ? 'Acceso no autorizado (403)' : err.message) || 
                 'Error desconocido al ejecutar la consulta';

    // Mostrar más detalles del 403
    if (err.response?.status === 403) {
      console.warn('⚠️ Posibles causas del 403:');
      console.warn('- Token inválido o expirado');
      console.warn('- Falta de permisos para el recurso');
      console.warn('- Configuración incorrecta de CORS');
      console.warn('- Políticas de seguridad en el backend');
    }
  } finally {
    loading.value = false;
    console.groupEnd();
  }
};

onMounted(() => {
  fetchTareasUsuario()
})
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

.query-container {
  margin-bottom: 2rem;
}

.dropdown-menu {
  width: 100%;
  padding: 0.5rem 0;
}

.dropdown-item {
  white-space: normal;
  padding: 0.75rem 1.25rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.dropdown-item:hover {
  background-color: #f8f9fa;
}

.results {
  min-height: 180px;
  border: 1px solid #dee2e6;
  border-radius: 0.375rem;
  transition: all 0.3s ease;
}

.btn-success:disabled {
  opacity: 0.65;
  cursor: not-allowed;
}

.alert {
  margin-bottom: 0;
}

/* Estilo específico para el botón desplegable */
.dropdown-toggle::after {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
}

.dropdown-toggle {
  position: relative;
  padding-right: 30px;
}

.list-group-item {
  border-left: none;
  border-right: none;
}

.badge {
  font-size: 0.9em;
}

.spinner-border-sm {
  width: 1rem;
  height: 1rem;
  border-width: 0.2em;
}
</style>