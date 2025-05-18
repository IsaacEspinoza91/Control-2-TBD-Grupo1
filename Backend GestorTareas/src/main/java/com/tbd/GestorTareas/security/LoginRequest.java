package com.tbd.GestorTareas.security;

public class LoginRequest {
    private String emailOrNick;       // puede ser email o nick
    private String password;

    // Getters y Setters
    public String getemailOrNick() {
        return emailOrNick;
    }

    public void setemailOrNick(String emailOrNick) {
        this.emailOrNick = emailOrNick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
