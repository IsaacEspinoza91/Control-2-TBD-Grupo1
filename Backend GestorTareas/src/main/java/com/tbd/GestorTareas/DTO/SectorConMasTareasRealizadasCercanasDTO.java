package com.tbd.GestorTareas.DTO;

public class SectorConMasTareasRealizadasCercanasDTO {
    private Long sectorId;
    private String sectorNombre;
    private Integer tareasRealizadas;
    private Double radioKm;

    // Getters y Setters
    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorNombre() {
        return sectorNombre;
    }

    public void setSectorNombre(String sectorNombre) {
        this.sectorNombre = sectorNombre;
    }

    public Integer getTareasRealizadas() {
        return tareasRealizadas;
    }

    public void setTareasRealizadas(Integer tareasRealizadas) {
        this.tareasRealizadas = tareasRealizadas;
    }

    public Double getRadioKm() {
        return radioKm;
    }

    public void setRadioKm(Double radioKm) {
        this.radioKm = radioKm;
    }
}