package com.tbd.GestorTareas.DTO;

public class TareasPendientesPorSectorDTO {
    private String sector;
    private int total_tareas_pendientes;

    public TareasPendientesPorSectorDTO(String sector, int totalTareasPendientes) {
        this.sector = sector;
        this.total_tareas_pendientes = totalTareasPendientes;
    }

    public TareasPendientesPorSectorDTO() {
    }

    // Getters y setters

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getTotal_tareas_pendientes() {
        return total_tareas_pendientes;
    }

    public void setTotal_tareas_pendientes(int total_tareas_pendientes) {
        this.total_tareas_pendientes = total_tareas_pendientes;
    }
}
