package com.tbd.GestorTareas.entities;

public class Sector {
    private Integer id;
    private String nombre;
    private String categoria;
    private String geom; // WKT del polígono

    // Constructores
    public Sector() {}

    public Sector(String nombre, String categoria, String geom) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.geom = geom;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    // Para setear el polígono a partir de una lista de coordenadas (lat, lng)
    public void setGeomFromCoordenadas(double[][] coordenadas) {
        StringBuilder sb = new StringBuilder("POLYGON((");
        for (double[] coord : coordenadas) {
            sb.append(coord[1]).append(" ").append(coord[0]).append(", "); // lng lat
        }
        // Cerrar el polígono (el último punto debe ser igual al primero)
        sb.append(coordenadas[0][1]).append(" ").append(coordenadas[0][0]).append("))");
        this.geom = sb.toString();
    }
}
