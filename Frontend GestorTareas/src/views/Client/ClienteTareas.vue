<template>
    <div class="client-layout">
        <ClientNavBar />

        <div class="task-manager-container">
            <div class="task-manager-header">
                <h1 class="task-manager-title">Gestor de Tareas</h1>
                <div class="header-actions">
                    <div class="search-container">
                        <input
                        v-model="searchKeyword"
                        type="text"
                        placeholder="Buscar tareas..."
                        class="search-input"
                        @keyup.enter="searchTasks"
                        />
                        <button @click="searchTasks" class="btn btn-search">
                        <i class="bi bi-search"></i> Buscar
                        </button>
                    </div>
                

                    <button @click="showCreateModal = true" class="btn btn-primary">
                        Nueva Tarea
                    </button>
                </div>
            </div>

            <!-- Filtros y pestañas -->
            <div class="task-filters">
                <div class="tab-buttons">
                    <button v-for="tab in tabs" :key="tab.id" @click="changeTab(tab.id)"
                        :class="['tab-button', { 'tab-button-active': activeTab === tab.id }]">
                        {{ tab.label }}
                    </button>
                </div>
            </div>
            <!-- Lista de tareas -->
            <div class="task-grid">
                <div v-for="task in getTasksToShow" :key="task.id" class="task-card" :class="{
                    'task-completed': task.estado === 'realizada',
                    'task-pending': task.estado === 'pendiente',
                    'task-deleted': task.eliminado
                }">
                    <!-- Contenido de la tarjeta de tarea -->
                    <div class="task-card-header">
                        <h3 class="task-title">{{ task.titulo }}</h3>
                        <span class="task-status" :class="{
                            'status-completed': task.estado === 'realizada',
                            'status-pending': task.estado === 'pendiente',
                            'status-deleted': task.eliminado
                        }">
                            {{ task.eliminado ? 'Eliminada' : task.estado }}
                        </span>
                    </div>

                    <p class="task-description">{{ task.descripcion }}</p>

                    <div class="task-meta">
                        <div class="task-date">
                            <span class="icon">📅</span>
                            <span>Vence: {{ formatDate(task.fechavencimiento) }}</span>
                        </div>

                        <div class="task-location">
                            <span class="icon">📍</span>
                            <span>Ubicación: {{ task.latitud }}, {{ task.longitud }}</span>
                        </div>
                    </div>

                    <div class="task-actions">
                        <button v-if="!task.eliminado" @click="toggleTaskStatus(task)" class="btn btn-action" :class="{
                            'btn-warning': task.estado === 'realizada',
                            'btn-success': task.estado === 'pendiente'
                        }">
                            {{ task.estado === 'realizada' ? 'Marcar Pendiente' : 'Marcar Realizada' }}
                        </button>

                        <button v-if="!task.eliminado" @click="openEditModal(task)" class="btn btn-action btn-info">
                            Editar
                        </button>

                        <button v-if="!task.eliminado" @click="deleteTask(task.id)" class="btn btn-action btn-danger">
                            Eliminar
                        </button>

                        <button v-if="task.eliminado" @click="restoreTask(task.id)" class="btn btn-action btn-restore">
                            Restaurar
                        </button>
                        <button @click="openMapModal(task)" class="btn btn-action btn-map">
                            <i class="bi bi-geo-alt"></i> Ver Mapa
                        </button>
                    </div>
                </div>
            </div>
            <!-- Modal para crear/editar tarea -->
            <div v-if="showCreateModal || showEditModal" class="modal-overlay">
                <div class="modal-container">
                    <div class="modal-content">
                        <h2 class="modal-title">
                            {{ showEditModal ? 'Editar Tarea' : 'Nueva Tarea' }}
                        </h2>
                        <button class="modal-close" @click="closeModal">×</button>

                        <div class="modal-body-scrollable">
                            <form @submit.prevent="showEditModal ? updateTask() : createTask()" class="task-form">
                                <div class="form-group">
                                    <label class="form-label">Título</label>
                                    <input v-model="currentTask.titulo" type="text" class="form-input" required>
                                </div>

                                <div class="form-group">
                                    <label class="form-label">Descripción</label>
                                    <textarea v-model="currentTask.descripcion" class="form-textarea"
                                        rows="3"></textarea>
                                </div>

                                <div class="form-group">
                                    <label class="form-label">Fecha de Vencimiento</label>
                                    <input v-model="currentTask.fechavencimiento" type="date" class="form-input"
                                        required>
                                </div>

                                <!-- Mapa para seleccionar ubicación -->
                                <div class="form-group">
                                    <label class="form-label">
                                        <i class="bi bi-geo-alt"></i> Seleccione la ubicación
                                    </label>
                                    <div class="map-container">
                                        <l-map ref="locationMap" :zoom="13" :center="defaultCenter" @click="setLocation"
                                            style="height: 300px; width: 100%;">
                                            <l-tile-layer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                                                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors' />
                                            <l-marker v-if="currentTask.latitud && currentTask.longitud"
                                                :lat-lng="[parseFloat(currentTask.latitud), parseFloat(currentTask.longitud)]" />
                                        </l-map>
                                    </div>
                                    <div class="coordinates-display">
                                        <span>Latitud: {{ currentTask.latitud || 'No seleccionada' }}</span>
                                        <span>Longitud: {{ currentTask.longitud || 'No seleccionada' }}</span>
                                    </div>
                                </div>



                                <div class="form-actions">
                                    <button type="button" @click="closeModal" class="btn btn-secondary">
                                        Cancelar
                                    </button>

                                    <button type="submit" class="btn btn-primary"
                                        :disabled="!currentTask.latitud || !currentTask.longitud">
                                        {{ showEditModal ? 'Actualizar' : 'Crear' }}
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal del Mapa -->
            <div v-if="showMapModal" class="modal-overlay">
                <div class="modal-container modal-map">
                    <div class="modal-content">
                        <h2 class="modal-title">Ubicación de la Tarea</h2>
                        <button class="modal-close" @click="showMapModal = false">×</button>

                        <div class="map-container">
                            <l-map ref="map" :zoom="15" :center="mapCenter" style="height: 400px; width: 100%">
                                <l-tile-layer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors' />
                                <l-marker :lat-lng="mapCenter" />
                            </l-map>
                        </div>

                        <div class="map-coordinates">
                            <span>Latitud: {{ currentLocation.latitud }}</span>
                            <span>Longitud: {{ currentLocation.longitud }}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>



<script setup>
import { ref, computed, onMounted, nextTick } from 'vue';
import { useAuthStore } from '../../stores/auth';
import api from '../../api';
import ClientNavBar from '../../components/NavBars/ClientNavBar.vue'

// Importaciones para Leaflet
import 'leaflet/dist/leaflet.css';
import { LMap, LTileLayer, LMarker } from '@vue-leaflet/vue-leaflet';

// Estados para el mapa
const showMapModal = ref(false);
const currentLocation = ref({ latitud: 0, longitud: 0 });
const mapCenter = computed(() => [currentLocation.value.latitud, currentLocation.value.longitud]);
const map = ref(null);
const defaultCenter = [-33.45694, -70.64827]; // Coordenadas por defecto (Santiago, Chile)


// Establecer ubicación desde el mapa
const setLocation = (e) => {
    currentTask.value.latitud = e.latlng.lat.toFixed(6);
    currentTask.value.longitud = e.latlng.lng.toFixed(6);

    // Centrar el mapa en la nueva ubicación
    nextTick(() => {
        if (locationMap.value && locationMap.value.leafletObject) {
            locationMap.value.leafletObject.setView([e.latlng.lat, e.latlng.lng]);
        }
    });
};

const authStore = useAuthStore();

// Estados
const searchKeyword = ref('');  // Para busqueda
const isSearching = ref(false);

// Función para buscar tareas por palabra clave
const searchTasks = async () => {
  if (!searchKeyword.value.trim()) {
    isSearching.value = false;
    await loadTasks(); // Recargar todas las tareas si el campo está vacío
    return;
  }

  try {
    isSearching.value = true;
    const userId = authStore.user?.idUsuario;
    const response = await api.get(`/tarea/usuario/${userId}/segun-palabra/${encodeURIComponent(searchKeyword.value)}`);
    tasks.value = response.data;
  } catch (error) {
    console.error('Error al buscar tareas:', error);
    isSearching.value = false;
  }
};



const tasks = ref([]);
const activeTab = ref('todas');
const showCreateModal = ref(false);
const showEditModal = ref(false);
const currentTask = ref({
    titulo: '',
    descripcion: '',
    fechavencimiento: '',
    estado: 'pendiente',
    latitud: '',
    longitud: '',
    usuario_id: authStore.user?.idUsuario,
    sector_id: null
});

// Pestañas disponibles (modificado)
const tabs = [
    { id: 'todas', label: 'Todas' },
    { id: 'pendientes', label: 'Pendientes' },
    { id: 'realizadas', label: 'Realizadas' },
    { id: 'semana', label: 'Esta Semana' },
    { id: 'proxima', label: 'Próxima Semana' }
];

// Cambiar pestaña (nueva función)
const changeTab = async (tabId) => {
    activeTab.value = tabId;

    try {
        const userId = authStore.user?.idUsuario;
        if (!userId) return;

        let endpoint = '';

        switch (tabId) {
            case 'pendientes':
                endpoint = `/tarea/usuario/pendientes/${userId}`;
                break;
            case 'realizadas':
                endpoint = `/tarea/usuario/completadas/${userId}`;
                break;
            case 'semana':
                endpoint = `/tarea/usuario/semana-actual/${userId}`;
                break;
            case 'proxima':
                endpoint = `/tarea/usuario/proximas-incompletas/${userId}`;
                break;
            case 'todas':
            default:
                endpoint = `/tarea/usuario/${userId}`;
        }

        if (endpoint) {
            const response = await api.get(endpoint);
            tasks.value = response.data;
        }
    } catch (error) {
        console.error('Error al cambiar pestaña:', error);
    }
};

// Filtrar tareas según la pestaña activa (simplificado)
const getTasksToShow = computed(() => {
    return tasks.value;
});

// Formatear fecha (igual que antes)
const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    const fecha = new Date(dateString + "T00:00:00").toLocaleDateString('es-ES', options);
    return fecha ; // Formatea la fecha para considerar zona horaria 0
};

// Cargar tareas (modificado)
const loadTasks = async () => {
    try {
        isSearching.value = false;
        const userId = authStore.user?.idUsuario;
        if (!userId) return;

        const response = await api.get(`/tarea/usuario/${userId}`);
        tasks.value = response.data;
    } catch (error) {
        console.error('Error al cargar tareas:', error);
    }
};

// Resto de funciones (igual que antes)
const createTask = async () => {
    try {
        currentTask.value.usuario_id = authStore.user?.idUsuario;
        await api.post('/tarea/', currentTask.value);
        await loadTasks();
        closeModal();
    } catch (error) {
        console.error('Error al crear tarea:', error);
    }
};

const openEditModal = (task) => {
    currentTask.value = { ...task };
    showEditModal.value = true;
    // Esperar a que el mapa se renderice y luego centrar
    nextTick(() => {
        if (locationMap.value && locationMap.value.leafletObject && task.latitud && task.longitud) {
            locationMap.value.leafletObject.setView(
                [parseFloat(task.latitud), parseFloat(task.longitud)],
                15
            );
        }
    });
};
const updateTask = async () => {
    try {
        await api.put(`/tarea/${currentTask.value.id}`, currentTask.value);
        await loadTasks();
        closeModal();
    } catch (error) {
        console.error('Error al actualizar tarea:', error);
    }
};

const toggleTaskStatus = async (task) => {
    try {
        if (task.estado === 'pendiente') {
            await api.patch(`/tarea/marcar-realizada/${task.id}`);
        } else {
            await api.patch(`/tarea/marcar-pendiente/${task.id}`);
        }
        await loadTasks();
    } catch (error) {
        console.error('Error al cambiar estado de la tarea:', error);
    }
};

const deleteTask = async (taskId) => {
    if (!confirm('¿Estás seguro de que deseas eliminar esta tarea?')) return;

    try {
        await api.patch(`/tarea/eliminar/${taskId}`);
        await loadTasks();
    } catch (error) {
        console.error('Error al eliminar tarea:', error);
    }
};

const restoreTask = async (taskId) => {
    try {
        await api.put(`/tarea/${taskId}`, { eliminado: false });
        await loadTasks();
    } catch (error) {
        console.error('Error al restaurar tarea:', error);
    }
};

const closeModal = () => {
    showCreateModal.value = false;
    showEditModal.value = false;
    currentTask.value = {
        titulo: '',
        descripcion: '',
        fechavencimiento: '',
        estado: 'pendiente',
        latitud: '',
        longitud: '',
        usuario_id: authStore.user?.idUsuario,
        sector_id: null
    };
};

// Cargar tareas al montar el componente
onMounted(() => {
    loadTasks();
});



// Función para abrir el modal del mapa
const openMapModal = (task) => {
    currentLocation.value = {
        latitud: parseFloat(task.latitud),
        longitud: parseFloat(task.longitud)
    };
    showMapModal.value = true;

    // Esperar a que el mapa se renderice y luego ajustar la vista
    setTimeout(() => {
        if (map.value && map.value.leafletObject) {
            map.value.leafletObject.setView(mapCenter.value, 15);
        }
    }, 100);
};


</script>

<style scoped>
/* Estilos base */
.client-layout {
    min-height: 100vh;
    background-color: #f5f7fa;
}

.task-manager-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

.task-manager-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
}

.task-manager-title {
    font-size: 28px;
    color: #2c3e50;
    margin: 0;
}

/* Botones */
.btn {
    padding: 10px 20px;
    border-radius: 6px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
    border: none;
}

.btn-primary {
    background-color: #3498db;
    color: white;
}

.btn-primary:hover {
    background-color: #2980b9;
}

.btn-secondary {
    background-color: #ecf0f1;
    color: #2c3e50;
}

.btn-secondary:hover {
    background-color: #bdc3c7;
}

.btn-success {
    background-color: #2ecc71;
    color: white;
}

.btn-success:hover {
    background-color: #27ae60;
}

.btn-warning {
    background-color: #f39c12;
    color: white;
}

.btn-warning:hover {
    background-color: #d35400;
}

.btn-danger {
    background-color: #e74c3c;
    color: white;
}

.btn-danger:hover {
    background-color: #c0392b;
}

.btn-info {
    background-color: #3498db;
    color: white;
}

.btn-info:hover {
    background-color: #2980b9;
}

.btn-restore {
    background-color: #9b59b6;
    color: white;
}

.btn-restore:hover {
    background-color: #8e44ad;
}

.btn-action {
    padding: 6px 12px;
    font-size: 14px;
    margin-right: 8px;
}

/* Pestañas */
.tab-buttons {
    display: flex;
    gap: 10px;
    margin-bottom: 20px;
    flex-wrap: wrap;
}

.tab-button {
    padding: 8px 16px;
    border-radius: 6px;
    background-color: #ecf0f1;
    color: #2c3e50;
    border: none;
    cursor: pointer;
    transition: all 0.3s ease;
}

.tab-button:hover {
    background-color: #bdc3c7;
}

.tab-button-active {
    background-color: #3498db;
    color: white;
}

.tab-button-active:hover {
    background-color: #2980b9;
}

/* Grid de tareas */
.task-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
}

.task-card {
    background-color: white;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    border-left: 4px solid #3498db;
}

.task-completed {
    border-left-color: #2ecc71;
}

.task-pending {
    border-left-color: #f39c12;
}

.task-deleted {
    border-left-color: #e74c3c;
}

.task-card-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 12px;
}

.task-title {
    font-size: 18px;
    font-weight: 600;
    color: #2c3e50;
    margin: 0;
}

.task-status {
    padding: 4px 8px;
    border-radius: 12px;
    font-size: 12px;
    font-weight: 500;
}

.status-completed {
    background-color: #d5f5e3;
    color: #27ae60;
}

.status-pending {
    background-color: #fef9e7;
    color: #f39c12;
}

.status-deleted {
    background-color: #fadbd8;
    color: #c0392b;
}

.task-description {
    color: #7f8c8d;
    margin-bottom: 16px;
    line-height: 1.5;
}

.task-meta {
    margin-bottom: 16px;
}

.task-date,
.task-location {
    display: flex;
    align-items: center;
    color: #7f8c8d;
    font-size: 14px;
    margin-bottom: 8px;
}

.icon {
    margin-right: 8px;
}

.task-actions {
    display: flex;
    gap: 8px;
    flex-wrap: wrap;
}

/* Modal */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
    overflow-y: auto;
}

.modal-container {
    width: 100%;
    max-width: 700px;
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.modal-content {
    padding: 24px;
}

.modal-title {
    font-size: 22px;
    color: #2c3e50;
    margin-bottom: 20px;
}

/* Nueva clase para el contenido desplazable del modal */
.modal-body-scrollable {
    max-height: calc(84vh - 120px);
    overflow-y: auto;
    padding-right: 15px;
}

/* Formulario */
.task-form {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.form-group {
    margin-bottom: 16px;
}

.form-row {
    display: flex;
    gap: 16px;
}

.form-group-half {
    flex: 1;
}

.form-label {
    display: block;
    margin-bottom: 8px;
    font-weight: 500;
    color: #2c3e50;
}

.form-input,
.form-select,
.form-textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 6px;
    font-size: 14px;
    transition: border-color 0.3s ease;
}

.form-input:focus,
.form-select:focus,
.form-textarea:focus {
    outline: none;
    border-color: #3498db;
    box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.2);
}

.form-textarea {
    min-height: 80px;
    resize: vertical;
}

.form-actions {
    display: flex;
    justify-content: flex-end;
    gap: 12px;
    margin-top: 20px;
}

.modal-map {
    max-width: 700px;
}

.map-container {
    margin-bottom: 10px;
    border-radius: 8px;
    overflow: hidden;
    border: 1px solid #ddd;
}

.map-coordinates {
    display: flex;
    justify-content: space-between;
    padding: 10px;
    background-color: #f5f5f5;
    border-radius: 6px;
}

.coordinates-display {
    display: flex;
    justify-content: space-between;
    font-size: 14px;
    color: #555;
    padding: 5px 0;
}

.coordinates-display span {
    background-color: #f5f5f5;
    padding: 3px 8px;
    border-radius: 4px;
}

.modal-close {
    position: absolute;
    top: 15px;
    right: 15px;
    font-size: 24px;
    background: none;
    border: none;
    cursor: pointer;
    color: #666;
}

.btn-map {
    background-color: #9b59b6;
    color: white;
}

.btn-map:hover {
    background-color: #8e44ad;
}

.btn:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}

.bi-geo-alt {
    margin-right: 5px;
}

/* Nuevos estilos para la barra de búsqueda */
.header-actions {
  display: flex;
  align-items: center;
  gap: 15px;
}

.search-container {
  display: flex;
  align-items: center;
  background: white;
  border-radius: 6px;
  overflow: hidden;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}

.search-input {
  padding: 10px 15px;
  border: none;
  outline: none;
  min-width: 250px;
  font-size: 14px;
}

.btn-search {
  background-color: #6c757d;
  color: white;
  border-radius: 0 6px 6px 0;
  padding: 10px 15px;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn-search:hover {
  background-color: #5a6268;
}

.bi-search {
  margin-right: 5px;
}


/* Responsive */
@media (max-width: 768px) {
    .task-manager-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 16px;
    }

    .task-grid {
        grid-template-columns: 1fr;
    }

    .form-row {
        flex-direction: column;
        gap: 0;
    }

      .header-actions {
    flex-direction: column;
    align-items: flex-start;
    width: 100%;
  }
  
  .search-container {
    width: 100%;
    margin-bottom: 10px;
  }
  
  .search-input {
    min-width: auto;
    flex-grow: 1;
  }
  
  .btn-primary {
    width: 100%;
  }
}
</style>