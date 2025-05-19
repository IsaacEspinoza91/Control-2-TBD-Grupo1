<template>
    <div class="app-container">
        <router-view v-slot="{ Component }">
            <component :is="Component" />
        </router-view>
        <AppFooter />
    </div>
</template>

<script>
import AppFooter from '/src/components/AppFooter.vue'

export default {
    components: { AppFooter }
}
</script>

<style>
@import 'bootstrap/dist/css/bootstrap.min.css';
@import 'bootstrap-icons/font/bootstrap-icons.css';



/* Estilos globales */
body,
html {
    margin: 0;
    padding: 0;
    height: 100%;
}

body {
    /* para navbar fija */
    padding-top: 60px;
}

.app-container {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
    position: relative;
}

.navbar {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 1030;
    height: 60px;
}

.modal-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1040;
    width: 100vw;
    height: 100vh;
    background-color: #000;
}

.modal {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1050;
    display: none;
    width: 100%;
    height: 100%;
    overflow: hidden;
    outline: 0;
}

.modal.show {
    display: block;
    overflow-x: hidden;
    overflow-y: auto;
}
</style>