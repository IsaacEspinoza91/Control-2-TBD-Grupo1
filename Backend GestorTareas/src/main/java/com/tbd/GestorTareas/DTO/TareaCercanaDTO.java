package com.tbd.GestorTareas.DTO;

public class TareaCercanaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String sector;
    private Double distanciaMetros;

    // Constructor
    public TareaCercanaDTO() {}
    public TareaCercanaDTO(Long id, String titulo, String descripcion, String sector, Double distanciaMetros) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.sector = sector;
        this.distanciaMetros = distanciaMetros;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Double getDistanciaMetros() {
        return distanciaMetros;
    }

    public void setDistanciaMetros(Double distanciaMetros) {
        this.distanciaMetros = distanciaMetros;
    }
}