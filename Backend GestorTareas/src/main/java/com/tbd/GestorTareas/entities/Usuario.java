package com.tbd.GestorTareas.entities;

public class Usuario {
    private Integer  id;
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String nick;
    private String tipo;    // admin o cliente
    private String ubicacion; // Almacenaremos el WKT (Well-Known Text) del punto

    // Constructores
    public Usuario() {}

    public Usuario(String rut, String nombre, String apellido, String email,
                   String password, String nick, String tipo, Double latitud, Double longitud) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.tipo = tipo;
        setUbicacion(latitud, longitud);
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Crear ubicacion segun latitud y longitud
    public void setUbicacion(Double latitud, Double longitud) {
        if (latitud != null && longitud != null) {
            // PostGIS usa orden X,Y (lng,lat). Ademas se reemplazan las comas por puntos para el formato
            this.ubicacion = String.format("POINT(%f %f)", longitud, latitud).replace(",", ".");
        }
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
