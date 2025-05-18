<template>
    <div class="card">
        <div class="card-body">
            <h2 class="card-title text-center mb-4">Registrarse</h2>
            <form @submit.prevent="handleSubmit">
                <div class="mb-3">
                    <label for="rut" class="form-label">RUT</label>
                    <input type="text" class="form-control" id="rut" v-model="rut" required>
                </div>
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" v-model="nombre" required>
                </div>
                <div class="mb-3">
                    <label for="apellido" class="form-label">Apellido</label>
                    <input type="text" class="form-control" id="apellido" v-model="apellido" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" v-model="email" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" v-model="password" required>
                </div>
                <div class="mb-3">
                    <label for="nick" class="form-label">Nick</label>
                    <input type="text" class="form-control" id="nick" v-model="nick" required>
                </div>
                <div class="mb-3">
                    <label for="tipo" class="form-label">Tipo de Usuario</label>
                    <select class="form-select" id="tipo" v-model="tipo" required>
                        <option value="admin">Admin</option>
                        <option value="cliente">Cliente</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary w-100" :disabled="loading">
                    <span v-if="loading" class="spinner-border spinner-border-sm" role="status"></span>
                    <span v-else>Registrarse</span>
                </button>
            </form>
            <div v-if="message" class="alert" :class="{ 'alert-success': success, 'alert-danger': !success }">
                {{ message }}
            </div>
            <div class="mt-3 text-center">
                <router-link to="/login">¿Ya tienes cuenta? Inicia sesión aquí</router-link>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '../../stores/auth'

const authStore = useAuthStore()

const rut = ref('')
const nombre = ref('')
const apellido = ref('')
const email = ref('')
const password = ref('')
const nick = ref('')
const tipo = ref('cliente')
const loading = ref(false)
const message = ref('')
const success = ref(false)

const handleSubmit = async () => {
    try {
        loading.value = true
        message.value = ''

        const result = await authStore.register({
            rut: rut.value,
            nombre: nombre.value,
            apellido: apellido.value,
            email: email.value,
            password: password.value,
            nick: nick.value,
            tipo: tipo.value
        })

        success.value = result.success
        message.value = result.message

        if (!result.success) {
            password.value = ''
        }
    } catch (err) {
        success.value = false
        message.value = err.message || 'Error en el registro'
    } finally {
        loading.value = false
    }
}
</script>

<style scoped>
.card {
    max-width: 500px;
    margin: 0 auto;
}
</style>