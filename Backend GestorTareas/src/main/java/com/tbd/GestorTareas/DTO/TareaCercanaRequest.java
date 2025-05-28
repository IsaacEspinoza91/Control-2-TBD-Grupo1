package com.tbd.GestorTareas.DTO;

public class TareaCercanaRequest {

    private Long usuarioId;
    private Double longitud;
    private Double latitud;

    public TareaCercanaRequest() {
    }

    public TareaCercanaRequest(Long usuarioId, Double longitud, Double latitud) {
        this.usuarioId = usuarioId;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
}
