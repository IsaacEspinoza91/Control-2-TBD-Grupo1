package com.tbd.GestorTareas.security;

public class AuthResponse {
    private String token;
    private String tipoUsuario;

    public AuthResponse(String token, String tipoUsuario) {
        this.token = token;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
}
