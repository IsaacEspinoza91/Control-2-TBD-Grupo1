package com.tbd.GestorTareas.repositories;

import com.tbd.GestorTareas.entities.Sector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class SectorRepository {

    @Autowired
    private Sql2o sql2o;

    // Crear
    public Sector crearSector(Sector sector) {
        String sql = "INSERT INTO sector (nombre, categoria, geom) " +
                "VALUES (:nombre, :categoria, ST_GeomFromText(:geom, 4326)) RETURNING id";
        try (var conn = sql2o.open()) {
            Integer id = conn.createQuery(sql, true)
                    .addParameter("nombre", sector.getNombre())
                    .addParameter("categoria", sector.getCategoria())
                    .addParameter("geom", sector.getGeom())
                    .executeUpdate()
                    .getKey(Integer.class);
            sector.setId(id);
            return sector;
        }
    }

    // Obtener todos
    public List<Sector> getAll() {
        String sql = "SELECT id, nombre, categoria, ST_AsText(geom) AS geom FROM sector";
        try (var conn = sql2o.open()) {
            return conn.createQuery(sql).executeAndFetch(Sector.class);
        }
    }

    // Obtener por ID
    public Sector getById(Integer id) {
        String sql = "SELECT id, nombre, categoria, ST_AsText(geom) AS geom FROM sector WHERE id = :id";
        try (var conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sector.class);
        }
    }

    // Actualizar
    public boolean updateSector(Sector sector) {
        String sql = "UPDATE sector SET nombre = :nombre, categoria = :categoria, geom = ST_GeomFromText(:geom, 4326) " +
                "WHERE id = :id";
        try (var conn = sql2o.open()) {
            int updated = conn.createQuery(sql)
                    .addParameter("nombre", sector.getNombre())
                    .addParameter("categoria", sector.getCategoria())
                    .addParameter("geom", sector.getGeom())
                    .addParameter("id", sector.getId())
                    .executeUpdate()
                    .getResult();
            return updated > 0;
        }
    }

    // Eliminar
    public boolean deleteSector(Integer id) {
        String sql = "DELETE FROM sector WHERE id = :id";
        try (var conn = sql2o.open()) {
            int deleted = conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate()
                    .getResult();
            return deleted > 0;
        }
    }
}
