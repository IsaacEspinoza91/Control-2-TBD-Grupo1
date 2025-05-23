package com.tbd.GestorTareas.DTO;

public class TareasRealizadasUsuariosSectorDTO {
    private String rut;
    private String nombre;
    private String apellido;
    private int total_tareas_hechas;
    private int sector_id;

    public TareasRealizadasUsuariosSectorDTO(String rut, String nombre, String apellido, int total_tareas_hechas, int sector) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.total_tareas_hechas = total_tareas_hechas;
        this.sector_id = sector;
    }

    public TareasRealizadasUsuariosSectorDTO() {
    }

    // Getters y Setters


    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTotal_tareas_hechas() {
        return total_tareas_hechas;
    }

    public void setTotal_tareas_hechas(int total_tareas_hechas) {
        this.total_tareas_hechas = total_tareas_hechas;
    }

    public int getSector_id() {
        return sector_id;
    }

    public void setSector_id(int sector_id) {
        this.sector_id = sector_id;
    }
}
