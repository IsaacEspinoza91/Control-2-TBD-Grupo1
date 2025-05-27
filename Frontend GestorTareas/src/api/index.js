import axios from 'axios';
import { useAuthStore } from '../stores/auth'; // Asegúrate de que la ruta sea correcta

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    withCredentials: true, // Importante para CORS con credenciales
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
});

// Interceptor para añadir el token
api.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
        config.headers['X-Requested-With'] = 'XMLHttpRequest';
    }
    return config;
}, error => {
    return Promise.reject(error);
});

// Interceptor de respuestas
api.interceptors.response.use(response => {
    return response;
}, error => {
    if (error.response?.status === 401) {
        const authStore = useAuthStore();
        authStore.logout();
        window.location.href = '/login';
    }
    
    // Manejo específico de errores CORS
    if (error.code === 'ERR_NETWORK' || error.message.includes('CORS')) {
        console.error('Error CORS:', error);
        error.response = {
            data: {
                message: 'Error de conexión con el servidor. Verifica CORS.'
            }
        };
    }
    
    return Promise.reject(error);
});

export default api;