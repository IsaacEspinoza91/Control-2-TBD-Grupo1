package com.tbd.GestorTareas.repositories;

import com.tbd.GestorTareas.DTO.TareasRealizadasUsuariosSectorDTO;
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

    public Tarea save(Tarea tarea) {
        String sql = "INSERT INTO tarea (titulo, descripcion, fechacreacion, fechavencimiento, estado, ubicacion, eliminado, usuario_id, sector_id) " +
                "VALUES (:titulo, :descripcion, :fechacreacion, :fechavencimiento, :estado, :ubicacion::geography, :eliminado, :usuario_id, :sector_id) RETURNING id";
        try (var con = sql2o.open()) {
            Integer id = con.createQuery(sql)
                    .addParameter("titulo", tarea.getTitulo())
                    .addParameter("descripcion", tarea.getDescripcion())
                    .addParameter("fechacreacion", tarea.getFechacreacion())
                    .addParameter("fechavencimiento", tarea.getFechavencimiento())
                    .addParameter("estado", tarea.getEstado())
                    .addParameter("ubicacion", tarea.getUbicacion())
                    .addParameter("eliminado", tarea.isEliminado())
                    .addParameter("usuario_id", tarea.getUsuario_id())
                    .addParameter("sector_id", tarea.getSector_id())
                    .executeScalar(Integer.class);
            tarea.setId(id);
            return tarea;
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

    // sentencia 6 williams
    public List<TareasRealizadasUsuariosSectorDTO> contarTareasRealizadasUsuariosSector() {
        String sql = """
                SELECT U.rut, U.nombre, U.apellido, COUNT(*) AS total_tareas_hechas, T.sector_id
                FROM usuario U
                JOIN tarea T ON U.id = T.usuario_id
                WHERE T.estado = 'realizada'
                GROUP BY U.rut, U.nombre, U.apellido, T.sector_id
                ORDER BY U.rut;
                """;
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(TareasRealizadasUsuariosSectorDTO.class);
        }
    }
    

}
