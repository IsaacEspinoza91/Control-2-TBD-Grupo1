import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json'
    }
})

// Interceptor para añadir el token a las peticiones
api.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
}, error => {
    return Promise.reject(error)
})


api.interceptors.response.use(response => {
    return response
}, error => {
    if (error.response?.status === 401) {
        // Token expirado o inválido
        const authStore = useAuthStore()
        authStore.logout()
        window.location.href = '/login'
    }
    return Promise.reject(error)
})

export default api