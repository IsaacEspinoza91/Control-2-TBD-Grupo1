package com.tbd.GestorTareas.repositories;

import com.tbd.GestorTareas.DTO.*;
import com.tbd.GestorTareas.entities.Tarea;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Repository
public class TareaRepository {

    private final Sql2o sql2o;

    @Autowired
    public TareaRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<Tarea> findAll() {
        String sql = "SELECT id, titulo, descripcion, fechacreacion, fechavencimiento, estado, " +
                "ST_AsText(ubicacion) AS ubicacion, eliminado, usuario_id, sector_id " +
                "FROM tarea WHERE eliminado = false";
        try (var con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Tarea.class);
        }
    }

    public Tarea findById(Long id) {
        String sql = "SELECT id, titulo, descripcion, fechacreacion, fechavencimiento, estado, " +
                "ST_AsText(ubicacion) AS ubicacion, eliminado, usuario_id, sector_id " +
                "FROM tarea WHERE id = :id AND eliminado = false";
        try (var con = sql2o.open()) {
            return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Tarea.class);
        }
    }

    public Tarea save(Tarea tarea) {
        String sql = "INSERT INTO tarea (titulo, descripcion, fechacreacion, fechavencimiento, estado, ubicacion, eliminado, usuario_id, sector_id) " +
                "VALUES (:titulo, :descripcion, :fechacreacion, :fechavencimiento, :estado, ST_GeomFromText(:ubicacion, 4326), :eliminado, :usuario_id, :sector_id)";

        try (var con = sql2o.beginTransaction()) {
            con.createQuery(sql)
                    .addParameter("titulo", tarea.getTitulo())
                    .addParameter("descripcion", tarea.getDescripcion())
                    .addParameter("fechacreacion", tarea.getFechacreacion())
                    .addParameter("fechavencimiento", tarea.getFechavencimiento())
                    .addParameter("estado", tarea.getEstado())
                    .addParameter("ubicacion", tarea.getUbicacion())
                    .addParameter("eliminado", tarea.isEliminado())
                    .addParameter("usuario_id", tarea.getUsuario_id())
                    .addParameter("sector_id", tarea.getSector_id())
                    .executeUpdate();

            Integer id = con.createQuery("SELECT currval('tarea_id_seq')").executeScalar(Integer.class);
            tarea.setId(id);
            con.commit(); // Confirma la transacción
            return tarea;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la tarea y obtener el ID", e);
        }
    }


    public void update(Tarea tarea) {
        String sql = "UPDATE tarea SET titulo = :titulo, descripcion = :descripcion, fechacreacion = :fechacreacion, " +
                "fechavencimiento = :fechavencimiento, estado = :estado, ubicacion = ST_GeomFromText(:ubicacion, 4326), usuario_id = :usuario_id, " +
                "sector_id = :sector_id WHERE id = :id";
        try (var con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("titulo", tarea.getTitulo())
                    .addParameter("descripcion", tarea.getDescripcion())
                    .addParameter("fechacreacion", tarea.getFechacreacion())
                    .addParameter("fechavencimiento", tarea.getFechavencimiento())
                    .addParameter("estado", tarea.getEstado())
                    .addParameter("ubicacion", tarea.getUbicacion())
                    .addParameter("usuario_id", tarea.getUsuario_id())
                    .addParameter("sector_id", tarea.getSector_id())
                    .addParameter("id", tarea.getId())
                    .executeUpdate();
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



    // Sentencia 2 ISAAC
    public TareaCercanaDTO findTareaPendienteMasCercanaSegunUbicacionUsuario(Long usuarioId) {
        String sql = """
                SELECT
                    t.id,
                    t.titulo,
                    t.descripcion,
                    s.nombre AS sector,
                    ST_Distance(t.ubicacion::geography, u.ubicacion::geography) AS distanciaMetros
                FROM tarea AS t
                    JOIN usuario AS u ON t.usuario_id = u.id
                    LEFT JOIN sector AS s ON t.sector_id = s.id
                WHERE u.id = :usuarioId
                    AND t.estado = 'pendiente' AND t.eliminado = false
                ORDER BY distanciaMetros ASC LIMIT 1;
                """;

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("usuarioId", usuarioId)
                    .executeAndFetchFirst(TareaCercanaDTO.class);
        }
    }

    // Sentencia 1 ISAAC
    public TareaCercanaDTO findTareaPendienteMasCercanaSegunUbicacionEspecifica(TareaCercanaRequest request) {
        String sql = """
                SELECT
                    t.id,
                    t.titulo,
                    t.descripcion,
                    s.nombre AS sector,
                    ST_Distance(t.ubicacion::geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography) AS distanciaMetros
                FROM tarea AS t
                    JOIN usuario AS u ON t.usuario_id = u.id
                    LEFT JOIN sector AS s ON t.sector_id = s.id
                WHERE u.id = :usuarioId
                    AND t.estado = 'pendiente' AND t.eliminado = false
                ORDER BY distanciaMetros ASC LIMIT 1;
                """;

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("usuarioId", request.getUsuarioId())
                    .addParameter("lng", request.getLng())
                    .addParameter("lat", request.getLat())
                    .executeAndFetchFirst(TareaCercanaDTO.class); // TareaCercanaDTO debe manejar el WKT
        }
    }



    // Nuevos metodos para trabajar el front

    // Obtener todas las tareas de un usuario según id
    public List<Tarea> findByUsuarioId(Long usuarioId) {
        String sql = "SELECT id, titulo, descripcion, fechacreacion, fechavencimiento, estado, " +
                "ST_AsText(ubicacion) AS ubicacion, eliminado, usuario_id, sector_id " +
                "FROM tarea WHERE usuario_id = :usuarioId AND eliminado = false " +
                "ORDER BY fechavencimiento ASC";
        try (var con = sql2o.open()) {
            return con.createQuery(sql).addParameter("usuarioId", usuarioId).executeAndFetch(Tarea.class);
        }
    }

    // Obtener todas las tareas de la semana actual del usuario según id usuario
    public List<Tarea> findTareasSemanaActualByUsuarioId(Long usuarioId) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)); // Lunes
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));     // Domingo

        String sql = "SELECT id, titulo, descripcion, fechacreacion, fechavencimiento, estado, " +
                "ST_AsText(ubicacion) AS ubicacion, eliminado, usuario_id, sector_id " +
                "FROM tarea WHERE usuario_id = :usuarioId AND eliminado = false AND estado = 'pendiente' " +
                "AND fechavencimiento BETWEEN :startDate AND :endDate ORDER BY fechavencimiento ASC";
        try (var con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("usuarioId", usuarioId)
                    .addParameter("startDate", startOfWeek)
                    .addParameter("endDate", endOfWeek)
                    .executeAndFetch(Tarea.class);
        }
    }

    // Obtener todas las tareas completadas del usuario según id usuario
    public List<Tarea> findTareasCompletadasByUsuarioId(Long usuarioId) {
        String sql = "SELECT id, titulo, descripcion, fechacreacion, fechavencimiento, estado, " +
                "ST_AsText(ubicacion) AS ubicacion, eliminado, usuario_id, sector_id " +
                "FROM tarea WHERE usuario_id = :usuarioId AND estado = 'realizada' AND eliminado = false" +
                "ORDER BY fechavencimiento ASC";
        try (var con = sql2o.open()) {
            return con.createQuery(sql).addParameter("usuarioId", usuarioId).executeAndFetch(Tarea.class);
        }
    }

    // Obtener todas las tareas pendientes del usuario según id usuario
    public List<Tarea> findTareasPendientesByUsuarioId(Long usuarioId) {
        String sql = "SELECT id, titulo, descripcion, fechacreacion, fechavencimiento, estado, " +
                "ST_AsText(ubicacion) AS ubicacion, eliminado, usuario_id, sector_id " +
                "FROM tarea WHERE usuario_id = :usuarioId AND estado = 'pendiente' AND eliminado = false" +
                "ORDER BY fechavencimiento ASC";
        try (var con = sql2o.open()) {
            return con.createQuery(sql).addParameter("usuarioId", usuarioId).executeAndFetch(Tarea.class);
        }
    }

    // Obtener todas las tareas próximas del usuario sin completar según id usuario
    public List<Tarea> findTareasPendientesProximasByUsuarioId(Long usuarioId) {
        LocalDate today = LocalDate.now();
        LocalDate inicioProximaSemana;

        // Si hoy es lunes, sumo una semana
        if (today.getDayOfWeek() == DayOfWeek.MONDAY) {
            inicioProximaSemana = today.plusWeeks(1);
        } else { // Si hoy no es lunes, voy al proximo lunes
            inicioProximaSemana = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }
        LocalDate finProximaSemana = inicioProximaSemana.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        String sql = "SELECT id, titulo, descripcion, fechacreacion, fechavencimiento, estado, " +
                "ST_AsText(ubicacion) AS ubicacion, eliminado, usuario_id, sector_id " +
                "FROM tarea WHERE usuario_id = :usuarioId AND estado != 'realizada' AND eliminado = false " +
                "AND fechavencimiento >= :inicioProximaSemana AND fechavencimiento <= :finProximaSemana " +
                "ORDER BY fechavencimiento ASC";
        try (var con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("usuarioId", usuarioId)
                    .addParameter("inicioProximaSemana", inicioProximaSemana)
                    .addParameter("finProximaSemana", finProximaSemana)
                    .executeAndFetch(Tarea.class);
        }
    }

    // Cambiar estado de tareas de "realizada" a "pendiente" según id de tarea
    public boolean cambiarEstadoAPendiente(Long tareaId) {
        String sql = "UPDATE tarea SET estado = 'pendiente' WHERE id = :tareaId AND estado = 'realizada'";
        try (var con = sql2o.open()) {
            int rowsAffected = con.createQuery(sql).addParameter("tareaId", tareaId).executeUpdate().getResult();
            return rowsAffected > 0;
        }
    }

    // Cambiar estado de tareas de "pendiente" a "realizada" según id de tarea
    public boolean cambiarEstadoARealizada(Long tareaId) {
        String sql = "UPDATE tarea SET estado = 'realizada' WHERE id = :tareaId AND estado = 'pendiente'";
        try (var con = sql2o.open()) {
            int rowsAffected = con.createQuery(sql).addParameter("tareaId", tareaId).executeUpdate().getResult();
            return rowsAffected > 0;
        }
    }

    // Cambiar bool de eliminado de una tarea según id de tarea
    public boolean cambiarEstadoDeEliminado(Long tareaId) {
        String selectSql = "SELECT eliminado FROM tarea WHERE id = :tareaId";
        try (var con = sql2o.open()) {
            Boolean currentEliminado = con.createQuery(selectSql)
                    .addParameter("tareaId", tareaId)
                    .executeScalar(Boolean.class);

            if (currentEliminado == null) {
                return false; // La tarea no existe
            }

            // Actualizar al valor opuesto -> !currentEliminado
            String updateSql = "UPDATE tarea SET eliminado = :newEliminado WHERE id = :tareaId";
            int rowsAffected = con.createQuery(updateSql)
                    .addParameter("newEliminado", !currentEliminado)
                    .addParameter("tareaId", tareaId)
                    .executeUpdate().getResult();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
