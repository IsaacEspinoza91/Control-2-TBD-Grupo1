package com.tbd.GestorTareas.security;

public class AuthResponse {
    private String token;
    private String tipoUsuario;
    private String nombreUsuario;

    public AuthResponse(String token, String tipoUsuario, String nombreUsuario) {
        this.token = token;
        this.tipoUsuario = tipoUsuario;
        this.nombreUsuario = nombreUsuario;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
