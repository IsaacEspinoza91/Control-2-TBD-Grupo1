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
    private Integer usuario_id;
    private Integer sector_id;

    // Constructores
    public Tarea() {}

    public Tarea(String titulo, String descripcion, LocalDate fechacreacion, LocalDate fechavencimiento,
                 String estado, String ubicacion, boolean eliminado, Integer usuario_id, Integer sector_id) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechacreacion = fechacreacion;
        this.fechavencimiento = fechavencimiento;
        this.estado = estado;
        this.ubicacion = ubicacion;
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

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Integer getSector_id() {
        return sector_id;
    }

    public void setSector_id(Integer sector_id) {
        this.sector_id = sector_id;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechacreacion=" + fechacreacion +
                ", fechavencimiento=" + fechavencimiento +
                ", estado='" + estado + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", eliminado=" + eliminado +
                ", usuario_id=" + usuario_id +
                ", sector_id=" + sector_id +
                '}';
    }
}
