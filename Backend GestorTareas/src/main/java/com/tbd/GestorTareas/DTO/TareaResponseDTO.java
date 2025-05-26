package com.tbd.GestorTareas.DTO;

import com.tbd.GestorTareas.entities.Tarea;
import java.time.LocalDate;

public class TareaResponseDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private LocalDate fechacreacion;
    private LocalDate fechavencimiento;
    private String estado;
    private Double latitud; // Para enviar la latitud
    private Double longitud; // Para enviar la longitud
    private boolean eliminado;
    private Long usuario_id;
    private Long sector_id;

    public TareaResponseDTO() {}

    // Constructor para convertir de Tarea a TareaResponseDTO
    public TareaResponseDTO(Tarea tarea) {
        this.id = tarea.getId();
        this.titulo = tarea.getTitulo();
        this.descripcion = tarea.getDescripcion();
        this.fechacreacion = tarea.getFechacreacion();
        this.fechavencimiento = tarea.getFechavencimiento();
        this.estado = tarea.getEstado();
        this.eliminado = tarea.isEliminado();
        this.usuario_id = tarea.getUsuario_id();
        this.sector_id = tarea.getSector_id();

        // Extraer latitud y longitud del WKT
        String wkt = tarea.getUbicacion();
        if (wkt != null && wkt.startsWith("POINT(")) {  // Si wkt no es nulo y comienza con POINT(
            String coords = wkt.substring(6, wkt.length() - 1);
            String[] parts = coords.split("\\s+");

            if (parts.length == 2) {
                try {
                    this.longitud = Double.parseDouble(parts[0].replace(",", "."));
                    this.latitud = Double.parseDouble(parts[1].replace(",", "."));
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear WKT: " + wkt);
                    this.latitud = null;
                    this.longitud = null;
                }
            }
        }

    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

    public Long getUsuario_id() { return usuario_id; }
    public void setUsuario_id(Long usuario_id) { this.usuario_id = usuario_id; }

    public Long getSector_id() { return sector_id; }
    public void setSector_id(Long sector_id) { this.sector_id = sector_id; }
}