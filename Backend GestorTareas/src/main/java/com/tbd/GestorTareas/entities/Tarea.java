package com.tbd.GestorTareas.entities;

import java.time.LocalDate;

public class Tarea {
    private Integer id;
    private String titulo;
    private String descripcion;
    private LocalDate fechacreacion;
    private LocalDate fechavencimiento;
    private String estado;
    private String ubicacion; // Almacenaremos el WKT del punto
    private boolean eliminado;
    private Long usuario_id;
    private Long sector_id;

    // Constructores
    public Tarea() {}

    public Tarea(String titulo, String descripcion, LocalDate fechacreacion, LocalDate fechavencimiento,
                 String estado, Double latitud, Double longitud,
                 boolean eliminado, Long usuario_id, Long sector_id) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechacreacion = fechacreacion;
        this.fechavencimiento = fechavencimiento;
        this.estado = estado;
        setUbicacion(latitud, longitud); // genera el WKT
        this.eliminado = eliminado;
        this.usuario_id = usuario_id;
        this.sector_id = sector_id;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public LocalDate getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(LocalDate fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public LocalDate getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(LocalDate fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    // Set WKT desde latitud y longitud
    public void setUbicacion(Double latitud, Double longitud) {
        if (latitud != null && longitud != null) {
            this.ubicacion = String.format("POINT(%f %f)", longitud, latitud).replace(",", ".");
        }
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Long getSector_id() {
        return sector_id;
    }

    public void setSector_id(Long sector_id) {
        this.sector_id = sector_id;
    }
}
