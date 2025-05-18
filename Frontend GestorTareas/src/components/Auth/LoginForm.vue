<template>
    <div class="card">
        <div class="card-body">
            <h2 class="card-title text-center mb-4">Iniciar Sesión</h2>
            <form @submit.prevent="handleSubmit">
                <div class="mb-3">
                    <label for="emailOrNick" class="form-label">Email o Nick</label>
                    <input type="text" class="form-control" id="emailOrNick" v-model="emailOrNick" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" v-model="password" required>
                </div>
                <button type="submit" class="btn btn-primary w-100" :disabled="loading">
                    <span v-if="loading" class="spinner-border spinner-border-sm" role="status"></span>
                    <span v-else>Iniciar Sesión</span>
                </button>
            </form>
            <div v-if="error" class="alert alert-danger mt-3">
                {{ error }}
            </div>
            <div class="mt-3 text-center">
                <router-link to="/register">¿No tienes cuenta? Regístrate aquí</router-link>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '../../stores/auth'
import { useRouter } from 'vue-router'

const authStore = useAuthStore()
const router = useRouter()

const emailOrNick = ref('')
const password = ref('')
const loading = ref(false)
const error = ref('')

const handleSubmit = async () => {
    try {
        loading.value = true
        error.value = ''
        await authStore.login({
            emailOrNick: emailOrNick.value,
            password: password.value
        })
    } catch (err) {
        error.value = err.message
    } finally {
        loading.value = false
    }
}
</script>

<style scoped>
.card {
    max-width: 400px;
    margin: 0 auto;
}
</style>