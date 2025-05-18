<template>
    <MainNavBar />
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <LoginForm />
            </div>
        </div>
    </div>
</template>

<script setup>
import MainNavBar from '../../components/NavBars/MainNavBar.vue'
import LoginForm from '../../components/Auth/LoginForm.vue'
</script>