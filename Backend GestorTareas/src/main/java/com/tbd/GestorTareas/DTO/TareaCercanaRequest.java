package com.tbd.GestorTareas.DTO;

public class TareaCercanaRequest {

    private Long usuarioId;
    private Double lng;
    private Double lat;

    public TareaCercanaRequest() {}
    public TareaCercanaRequest(Long usuarioId, Double lng, Double lat) {
        this.usuarioId = usuarioId;
        this.lng = lng;
        this.lat = lat;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
