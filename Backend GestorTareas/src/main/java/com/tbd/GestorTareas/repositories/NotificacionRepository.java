package com.tbd.GestorTareas.repositories;

import com.tbd.GestorTareas.entities.Notificacion;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificacionRepository {

    private final Sql2o sql2o;

    public NotificacionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    public void generarNotificacionesPorVencer() {
        String sql = "CALL generar_notificaciones_por_vencer()";

        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql).executeUpdate();
            con.commit(); // guarda los cambios
        } catch (Exception e) {
            System.err.println("Error al generar notificaciones: " + e.getMessage());
            // opcional: puedes registrar más detalles si quieres
        }
    }



    // Crear una nueva notificación
    public Notificacion create(Notificacion notificacion) {
        String sql = "INSERT INTO notificacion (mensaje, fechaenvio, tarea_id, usuario_id) " +
                "VALUES (:mensaje, :fechaenvio, :tarea_id, :usuario_id) RETURNING id";
        try (Connection conn = sql2o.beginTransaction()) {
            Integer id = conn.createQuery(sql)
                    .addParameter("mensaje", notificacion.getMensaje())
                    .addParameter("fechaenvio", notificacion.getFechaenvio())
                    .addParameter("tarea_id", notificacion.getTarea_id())
                    .addParameter("usuario_id", notificacion.getUsuario_id())
                    .executeScalar(Integer.class);

            if (id == null) {
                conn.rollback();
                throw new RuntimeException("No se pudo obtener el id al crear notificacion");
            }
            notificacion.setId(id);

            conn.commit();
            return notificacion;
        } catch (Exception e) {
            throw e;
        }
    }




    // Obtener todas las notificaciones
    public List<Notificacion> getAll() {
        String sql = "SELECT * FROM notificacion";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .executeAndFetch(Notificacion.class);
        }
    }

    // Obtener notificación por id
    public Notificacion getById(Integer id) {
        String sql = "SELECT * FROM notificacion WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Notificacion.class);
        }
    }

    // Actualizar notificación
    public void update(Notificacion notificacion) {
        String sql = "UPDATE notificacion SET mensaje = :mensaje, fechaenvio = :fechaenvio, " +
                "tarea_id = :tarea_id, usuario_id = :usuario_id WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql)
                    .bind(notificacion)
                    .executeUpdate();
        }
    }

    // Eliminar notificación por id
    public void delete(Integer id) {
        String sql = "DELETE FROM notificacion WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
