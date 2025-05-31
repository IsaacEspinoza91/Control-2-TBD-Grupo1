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
              <button class="btn btn-outline-primary dropdown-toggle w-100" type="button" @click="toggleDropdown"
                aria-expanded="false" style="text-align: left;">
                Consultas
              </button>
              <ul v-if="showDropdown" class="dropdown-menu show w-100">
                <li>
                  <a class="dropdown-item" href="#" @click.prevent="selectQuery('por-sector')">
                    ¿Cuántas tareas ha hecho el usuario por sector?
                  </a>
                </li>
                <li>
                  <a class="dropdown-item" href="#" @click.prevent="selectQuery('sector-2km')">
                    ¿Cuál es el sector con más tareas completadas en un radio de 2 km del usuario?
                  </a>
                </li>
                <li>
                  <a class="dropdown-item" href="#" @click.prevent="selectQuery('sector-5km')">
                    ¿Cuál es el sector con más tareas completadas en un radio de 5 km del usuario?
                  </a>
                </li>
                <li>
                  <a class="dropdown-item" href="#" @click.prevent="selectQuery('tareas-pendientes-sector')">
                    ¿En qué sectores geográficos se concentran la mayoría de las tareas pendientes?
                  </a>
                </li>
                <li>
                  <a class="dropdown-item" href="#" @click.prevent="selectQuery('distancia-promedio-usuario')">
                    ¿Cuál es el promedio de distancia de las tareas completadas respecto a la ubicación del usuario (poner un punto en el mapa)?
                  </a>
                </li>
                <li>
                  <a class="dropdown-item" href="#" @click.prevent="selectQuery('distancia-promedio-registro')">
                    ¿Cuál es el promedio de distancia entre las tareas completadas y el punto registrado del usuario?
                  </a>
                </li>
                <li>
                  <a class="dropdown-item" href="#" @click.prevent="selectQuery('pendiente-mas-cercana')">
                    ¿Cuál es la tarea pendiente más cercana al usuario según la ubicación seleccionada en el mapa?
                  </a>
                </li>
                <li>
                  <a class="dropdown-item" href="#" @click.prevent="selectQuery('pendiente-mas-cercana-ubi-usuario')">
                    ¿Cuál es la tarea pendiente más cercana a la ubicación del usuario?
                  </a>
                </li>
              </ul>
            </div>

            <!-- Botón Ejecutar -->
            <button class="btn btn-success flex-shrink-0" @click="executeQuery" :disabled="!selectedQuery"
              style="min-width: 100px;">
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
                    <li v-for="item in resultado" :key="item.sector"
                      class="list-group-item d-flex justify-content-between align-items-center">
                      {{ item.sector }}
                      <span class="badge bg-primary rounded-pill">{{ item.totalTareas }}</span>
                    </li>
                  </ul>
                </div>
                <div v-else class="alert alert-info">
                  No hay tareas realizadas en ningún sector.
                </div>
              </div>

              <!-- Resultado para consulta de tareas pendientes por sector -->
              <div v-else-if="selectedQuery === 'tareas-pendientes-sector'">
                <div v-if="resultado.length > 0" class="alert alert-warning">
                  <h6 class="mb-3">Tareas pendientes por sector:</h6>
                  <ul class="list-group">
                    <li v-for="item in resultado" :key="item.sector"
                      class="list-group-item d-flex justify-content-between align-items-center">
                      {{ item.sector }}
                      <span class="badge bg-warning text-dark rounded-pill">{{ item.total_tareas_pendientes }}</span>
                    </li>
                  </ul>
                </div>
                <div v-else class="alert alert-info">
                  No hay tareas pendientes en ningún sector.
                </div>
              </div>

              <!-- Resultado para consultas de distancia promedio desde ubicación seleccionada -->
              <div v-else-if="selectedQuery === 'distancia-promedio-usuario'">
                <div v-if="resultado" class="alert alert-success">
                  <h6 class="mb-3">Distancia promedio desde la ubicación seleccionada:</h6>
                  <p><strong>{{ authStore.user.nombreUsuario }} {{ authStore.user.apellidoUsuario }}</strong></p>
                  <p>Promedio de distancia: 
                    <strong>{{ (resultado.promedioDistancia / 1000).toFixed(2) }} km</strong>
                  </p>
                </div>
                <div v-else class="alert alert-info">
                  No se encontraron resultados para esta consulta.
                </div>
              </div>

              <!-- Resultado para consultas de distancia promedio punto registrado -->
              <div v-else-if="selectedQuery === 'distancia-promedio-registro'">
                <div v-if="resultado && resultado.promedioDistancia !== undefined" class="alert alert-success">
                  <h6 class="mb-3">Distancia promedio desde la ubicación registrada:</h6>
                  <p><strong>{{ resultado.nombreUsuario }} {{ resultado.apellidoUsuario }}</strong></p>
                  <p>Promedio de distancia: <strong>{{ (resultado.promedioDistancia / 1000).toFixed(2) }} km</strong></p>
                </div>
                <div v-else class="alert alert-info">
                  No hay tareas realizadas en ningún sector.
                </div>
              </div>

              <!-- Resultado para consulta 'pendiente-mas-cercana-ubi-usuario' -->
              <div v-else-if="selectedQuery === 'pendiente-mas-cercana-ubi-usuario'">
                <div v-if="resultado && resultado.id" class="alert alert-success">
                  <h6 class="mb-3">Tarea pendiente más cercana:</h6>
                  <p><strong>ID Tarea:</strong> {{ resultado.id }}</p>
                  <p><strong>Título:</strong> {{ resultado.titulo }}</p>
                  <p><strong>Descripción:</strong> {{ resultado.descripcion }}</p>
                  <!-- Dirección no disponible, mostrar latitud y longitud en su lugar -->
                  <p><strong>Latitud:</strong> {{ resultado.latitud }}</p>
                  <p><strong>Longitud:</strong> {{ resultado.longitud }}</p>
                  <p class="card-text">
                    <strong>Distancia:  </strong> 
                    <span class="badge bg-primary rounded-pill">
                      {{ (resultado.distancia / 1000).toFixed(2) }} km
                    </span>
                  </p>

                  <!-- Mapa -->
                  <div class="map-container" style="height: 400px; margin-top: 20px;">
                    <l-map 
                      ref="map"
                      :zoom="15" 
                      :center="[resultado.latitud, resultado.longitud]"
                      :options="{scrollWheelZoom: false}"
                    >
                      <l-tile-layer
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        layer-type="base"
                        name="OpenStreetMap"
                      ></l-tile-layer>
                      <l-marker :lat-lng="[resultado.latitud, resultado.longitud]">
                        <l-popup>
                          <strong>{{ resultado.titulo }}</strong><br>
                          {{ resultado.descripcion }}
                        </l-popup>
                      </l-marker>
                    </l-map>
                  </div>

                </div>
                <div v-else class="alert alert-info">
                  No se encontró una tarea pendiente cercana.
                </div>
              </div>  
              <!-- Resultado para tarea pendiente más cercana -->
              <div v-else-if="selectedQuery === 'pendiente-mas-cercana'">
                <div v-if="resultado && resultado.id" class="alert alert-success">
                  <h6 class="mb-3">Tarea pendiente más cercana:</h6>
                  <div class="card">
                    <div class="card-body">
                      <h5 class="card-title">Tarea #{{ resultado.id }}</h5>
                      <p class="card-text"><strong>Título:</strong> {{ resultado.titulo }}</p>
                      <p class="card-text"><strong>Descripción:</strong> {{ resultado.descripcion }}</p>
                      <p class="card-text"><strong>Fecha vencimiento:</strong> {{ resultado.fechavencimiento }}</p>
                      <p class="card-text">
                        <strong>Distancia:  </strong> 
                        <span class="badge bg-primary rounded-pill">
                          {{ (resultado.distancia / 1000).toFixed(2) }} km
                        </span>
                      </p>

                      <div class="map-container" style="height: 400px; margin-top: 20px;">
                        <l-map 
                          ref="map"
                          :zoom="15" 
                          :center="[resultado.latitud, resultado.longitud]"
                          :options="{scrollWheelZoom: false}"
                        >
                          <l-tile-layer
                            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                            layer-type="base"
                            name="OpenStreetMap"
                          ></l-tile-layer>
                          <l-marker :lat-lng="[resultado.latitud, resultado.longitud]">
                            <l-popup>
                              <strong>{{ resultado.titulo }}</strong><br>
                              {{ resultado.descripcion }}
                            </l-popup>
                          </l-marker>
                        </l-map>
                      </div>


                    </div>
                  </div>
                </div>
                <div v-else class="alert alert-info">
                  No se encontraron tareas pendientes cercanas a tu ubicación.
                </div>
              </div>

              <!-- Resultado para otras consultas -->
              <div v-else>
                <div v-if="resultado.sectorId" class="alert alert-success">
                  <p class="mb-1"><strong>Sector:</strong> {{ resultado.sectorNombre }}</p>
                  <p class="mb-1"><strong>Tareas realizadas:</strong> {{ resultado.tareasRealizadas }}</p>
                  <p class="mb-0"><strong>Radio de búsqueda:</strong> {{ resultado.radioKm || (selectedQuery ===
                    'sector-2km' ? '2' : '5') }} km</p>
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
      </div>

      <div class="form-group">
          <label class="form-label">
              <i class="bi bi-geo-alt me-2"></i>
              Seleccione su ubicación actual
          </label>
          <l-map
              ref="mapRef"
              class="map-container"
              style="height: 550px" 
              :zoom="13"
              :center="mapCenter"
              @click="onMapClick"
          >
              <l-tile-layer
                  url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
              />
              <l-marker :lat-lng="ubicacion" v-if="ubicacion" />
          </l-map>
          <small v-if="ubicacion">Lat: {{ ubicacion.lat }}, Lng: {{ ubicacion.lng }}</small>
      </div>
    </div>
  </div>
</template>

<script setup>
import ClientNavBar from '../../components/NavBars/ClientNavBar.vue'
import { useAuthStore } from '../../stores/auth'
import { ref, onMounted } from 'vue'
import axios from '../../api' // Importamos la instancia de axios configurada

//inicio del mapa
import { LMap, LTileLayer, LMarker, LPopup } from '@vue-leaflet/vue-leaflet'

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

const mapCenter = ref([-33.456, -70.648])
const ubicacion = ref(null)

const onMapClick = (e) => {
  ubicacion.value = e.latlng
  authStore.user.longitud = e.latlng.lng
  authStore.user.latitud = e.latlng.lat
}

//fin del mapa

const queryDescriptions = {
  'por-sector': '¿Cuántas tareas ha hecho el usuario por sector?',
  'sector-2km': '¿Cuál es el sector con más tareas completadas en un radio de 2 km del usuario?',
  'sector-5km': '¿Cuál es el sector con más tareas completadas en un radio de 5 km del usuario?',
  'tareas-pendientes-sector': '¿En qué sectores geográficos se concentran la mayoría de las tareas pendientes?',
  'distancia-promedio-usuario': '¿Cuál es el promedio de distancia de las tareas completadas respecto a la ubicación del usuario?  (poner un punto en el mapa)',
  'distancia-promedio-registro': '¿Cuál es el promedio de distancia entre las tareas completadas y el punto registrado del usuario?',
  'pendiente-mas-cercana': '¿Cuál es la tarea pendiente más cercana al usuario según la ubicación seleccionada en el mapa?',
  'pendiente-mas-cercana-ubi-usuario': '¿Cuál es la tarea pendiente más cercana a la ubicación del usuario?'
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
  console.group('🛐DEBUG: Iniciando executeQuery🛐');

  // 1. Verificación inicial
  if (!selectedQuery.value) {
    console.error('❌DEBUG: No hay consulta seleccionada');
    error.value = 'Debes seleccionar una consulta primero';
    console.groupEnd();
    return;
  }

  if (!authStore.user?.idUsuario) {
    console.error('❌DEBUG: No hay ID de usuario', {
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
    let data = {};

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
      case 'tareas-pendientes-sector':
        endpoint = `/tarea/pendientes-por-sector`;
        break;
      case 'distancia-promedio-registro':
        endpoint = `/usuario/promedio-distancia/${authStore.user.idUsuario}`;
        break;
      case 'distancia-promedio-usuario':
        endpoint = `/usuario/promedio-de-distancia`;
        params = {
          usuarioId: authStore.user.idUsuario,
          longitud: ubicacion.value.lng,
          latitud: ubicacion.value.lat
        };
        break;
      case 'pendiente-mas-cercana':
        endpoint = `/tarea/tarea-cercana`;
        params = {
          usuarioId: authStore.user.idUsuario,
          longitud: authStore.user?.longitud,
          latitud: authStore.user?.latitud
        };
        break;

      case 'pendiente-mas-cercana-ubi-usuario':
        endpoint = `/tarea/usuario/${authStore.user.idUsuario}/tarea-cercana`;
        break;

    }

    console.log('❗DEBUG: Datos de la petición:', {
      endpoint,
      selectedQuery: selectedQuery.value,
      user: authStore.user,
      token: authStore.token,
      data
    });

    // 3. Headers detallados
    const headers = {
      'Authorization': `Bearer ${authStore.token}`,
      'Content-Type': 'application/json',
      'X-Debug-Request': 'true'
    };

    console.log('❗DEBUG: Headers enviados:', headers);

    // 4. Realizar petición
    const response = await axios.get(endpoint, {
      headers,
      params
    });

    console.log('✅DEBUG: Respuesta exitosa:', {
      status: response.status,
      data: response.data,
      headers: response.headers
    });

    resultado.value = response.data;

  } catch (err) {
    console.error('❌DEBUG: Error completo:', {
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

/* Estilo específico para tareas pendientes */
.badge.bg-warning {
  color: #212529;
}

/* Estilos para la tarjeta de tarea */
.card {
  margin-top: 10px;
  border: 1px solid rgba(0, 0, 0, 0.125);
  border-radius: 0.25rem;
}

.card-body {
  padding: 1.25rem;
}

.card-title {
  margin-bottom: 0.75rem;
  font-size: 1.25rem;
}

.card-text {
  margin-bottom: 0.5rem;
}
</style>