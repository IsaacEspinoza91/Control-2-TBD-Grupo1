package com.tbd.GestorTareas.repositories;


import com.tbd.GestorTareas.DTO.TareasPendientesPorSectorDTO;
import com.tbd.GestorTareas.DTO.TareasPorSectorDTO;
import com.tbd.GestorTareas.entities.Tarea;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TareaRepository {

    private final Sql2o sql2o;

    @Autowired
    public TareaRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<Tarea> findAll() {
        String sql = "SELECT * FROM tarea WHERE eliminado = false";
        try (var con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Tarea.class);
        }
    }

    public Tarea findById(Long id) {
        String sql = "SELECT * FROM tarea WHERE id = :id AND eliminado = false";
        try (var con = sql2o.open()) {
            return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Tarea.class);
        }
    }

    public Long save(Tarea tarea) {
        String sql = "INSERT INTO tarea (titulo, descripcion, fechacreacion, fechavencimiento, estado, ubicacion, eliminado, usuario_id, sector_id) " +
                "VALUES (:titulo, :descripcion, :fechacreacion, :fechavencimiento, :estado, :ubicacion, :eliminado, :usuarioId, :sectorId)";
        try (var con = sql2o.open()) {
            return (Long) con.createQuery(sql, true)
                    .bind(tarea)
                    .executeUpdate()
                    .getKey();
        }
    }

    public void update(Tarea tarea) {
        String sql = "UPDATE tarea SET titulo = :titulo, descripcion = :descripcion, fechacreacion = :fechacreacion, " +
                "fechavencimiento = :fechavencimiento, estado = :estado, ubicacion = :ubicacion, usuario_id = :usuarioId, " +
                "sector_id = :sectorId WHERE id = :id";
        try (var con = sql2o.open()) {
            con.createQuery(sql).bind(tarea).executeUpdate();
        }
    }

    public void delete(Long id) {
        String sql = "UPDATE tarea SET eliminado = true WHERE id = :id";
        try (var con = sql2o.open()) {
            con.createQuery(sql).addParameter("id", id).executeUpdate();
        }
    }

    //Sentencia 4 Omar
    public List<TareasPendientesPorSectorDTO> contarTareasPendientesPorSector() {
        String sql = """
        SELECT s.nombre AS sector, COUNT(*) AS total_tareas_pendientes
        FROM tarea t
        JOIN sector s ON ST_Contains(
            s.geom::geometry,
            t.ubicacion::geometry
        )
        WHERE t.estado = 'pendiente' AND t.eliminado = false
        GROUP BY s.id
        ORDER BY total_tareas_pendientes DESC;
    """;

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(TareasPendientesPorSectorDTO.class);
        }
    }


    //Sentencia 7 Omar
    public List<TareasPorSectorDTO> contarTareasPorSectorPorNick(String nick) {
        String sql = """
        SELECT s.nombre AS sector, COUNT(*) AS total_tareas
        FROM tarea t
        JOIN usuario u ON t.usuario_id = u.id
        JOIN sector s ON t.sector_id = s.id
        WHERE t.estado = 'realizada'
          AND t.eliminado = false
          AND u.nick = :nick
        GROUP BY s.id
        ORDER BY sector
    """;

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("nick", nick)
                    .executeAndFetch(TareasPorSectorDTO.class);
        }
    }


}
