package com.tbd.GestorTareas.DTO;

public class UsuarioDistanciaDTO {
    private Long usuarioId;
    private String nombreUsuario;
    private String apellidoUsuario;
    private Double promedioDistancia;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public Double getPromedioDistancia() {
        return promedioDistancia;
    }

    public void setPromedioDistancia(Double promedioDistancia) {
        this.promedioDistancia = promedioDistancia;
    }

}
