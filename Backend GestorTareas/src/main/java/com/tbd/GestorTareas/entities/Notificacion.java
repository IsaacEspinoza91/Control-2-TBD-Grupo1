package com.tbd.GestorTareas.entities;

import java.time.LocalDate;

public class Notificacion {
    private Integer id;
    private String mensaje;
    private LocalDate fechaenvio;
    private Integer tarea_id;
    private Integer usuario_id;
    private boolean visto;

    // Constructores
    public Notificacion() {}

    public Notificacion(String mensaje, LocalDate fechaenvio, Integer tarea_id, Integer usuario_id, boolean visto) {
        this.mensaje = mensaje;
        this.fechaenvio = fechaenvio;
        this.tarea_id = tarea_id;
        this.usuario_id = usuario_id;
        this.visto = false;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDate getFechaenvio() {
        return fechaenvio;
    }

    public void setFechaenvio(LocalDate fechaenvio) {
        this.fechaenvio = fechaenvio;
    }

    public Integer getTarea_id() {
        return tarea_id;
    }

    public void setTarea_id(Integer tarea_id) {
        this.tarea_id = tarea_id;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }
}
