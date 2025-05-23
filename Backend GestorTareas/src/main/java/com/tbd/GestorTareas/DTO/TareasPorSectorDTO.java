package com.tbd.GestorTareas.DTO;

public class TareasPorSectorDTO {
    private String sector;
    private int total_tareas;

    public TareasPorSectorDTO() {
    }

    // Getters y setters

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getTotalTareas() {
        return total_tareas;
    }

    public void setTotalTareas(int totalTareas) {
        this.total_tareas = totalTareas;
    }
}

