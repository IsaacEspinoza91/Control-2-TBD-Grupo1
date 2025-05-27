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
                      @click.prevent="selectQuery(2)"
                  >
                    ¿Cuál es el sector con más tareas completadas en un radio de 2 kilómetros del usuario?
                  </a>
                </li>
                <li>
                  <a
                      class="dropdown-item"
                      href="#"
                      @click.prevent="selectQuery(5)"
                  >
                    ¿Cuál es el sector con más tareas completadas dentro de un radio de 5 km desde la ubicación del usuario?
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
            {{ 
              selectedQuery === 'por-sector' ? 
              '¿Cuántas tareas ha hecho el usuario por sector?' :
              selectedQuery === 2 ?
              '¿Cuál es el sector con más tareas completadas en un radio de 2 kilómetros del usuario?' :
              '¿Cuál es el sector con más tareas completadas dentro de un radio de 5 km desde la ubicación del usuario?'
            }}
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

              <!-- Resultado para otras consultas -->
              <div v-else>
                <div v-if="resultado.sectorId" class="alert alert-success">
                  <p class="mb-1"><strong>Sector:</strong> {{ resultado.sectorNombre }}</p>
                  <p class="mb-1"><strong>Tareas realizadas:</strong> {{ resultado.tareasRealizadas }}</p>
                  <p class="mb-0"><strong>Radio de búsqueda:</strong> {{ resultado.radioKm }} km</p>
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
import { ref } from 'vue'
import axios from 'axios'

const authStore = useAuthStore()

// Estado del componente
const showDropdown = ref(false)
const selectedQuery = ref(null) // 'por-sector', 2 o 5
const resultado = ref(null)
const error = ref(null)
const loading = ref(false)

const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value
}

const selectQuery = (query) => {
  selectedQuery.value = query
  showDropdown.value = false
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

  if (!authStore.user?.nombreUsuario) {
    console.error('❌ No hay nombre de usuario', {
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
    const userIdentifier = authStore.user.nombreUsuario;
    const endpoint = selectedQuery.value === 'por-sector'
      ? `http://localhost:8080/api/tarea/por-sector/${userIdentifier}`
      : `http://localhost:8080/api/usuario/${authStore.user.id}/sector-mas-tareas-realizadas-${selectedQuery.value}km`;

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
    const response = await axios.get(endpoint, { headers });

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
</style>