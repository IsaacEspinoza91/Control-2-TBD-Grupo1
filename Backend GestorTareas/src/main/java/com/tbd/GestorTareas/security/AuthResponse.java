package com.tbd.GestorTareas.security;

public class AuthResponse {
    private String token;
    private String tipoUsuario;
    private String nombreUsuario;
    private Integer idUsuario;

    public AuthResponse(String token, String tipoUsuario, String nombreUsuario, Integer idUsuario) {
        this.token = token;
        this.tipoUsuario = tipoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idUsuario = idUsuario;
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

    public Integer getIdUsuario() {
        return idUsuario;
    }
}
