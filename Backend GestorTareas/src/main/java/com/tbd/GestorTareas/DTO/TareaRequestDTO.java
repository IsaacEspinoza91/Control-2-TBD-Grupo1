package com.tbd.GestorTareas.DTO;

import java.time.LocalDate;

public class TareaRequestDTO {
    private String titulo;
    private String descripcion;
    private LocalDate fechacreacion;
    private LocalDate fechavencimiento;
    private String estado;
    private Double latitud; // Para recibir la latitud
    private Double longitud; // Para recibir la longitud
    private boolean eliminado;
    private Integer usuario_id;
    private Integer sector_id;

    // Getters y Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechacreacion() { return fechacreacion; }
    public void setFechacreacion(LocalDate fechacreacion) { this.fechacreacion = fechacreacion; }

    public LocalDate getFechavencimiento() { return fechavencimiento; }
    public void setFechavencimiento(LocalDate fechavencimiento) { this.fechavencimiento = fechavencimiento; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

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

    // Método para generar el WKT desde latitud y longitud
    public String toWkt() {
        if (latitud != null && longitud != null) {
            return String.format("POINT(%f %f)", longitud, latitud).replace(",", ".");
        }
        return null;
    }
}