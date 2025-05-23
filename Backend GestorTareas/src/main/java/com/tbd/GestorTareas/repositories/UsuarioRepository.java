package com.tbd.GestorTareas.repositories;


import com.tbd.GestorTareas.DTO.UsuarioDistanciaDTO;
import com.tbd.GestorTareas.entities.Usuario;
import com.tbd.GestorTareas.DTO.SectorConMasTareasRealizadasCercanasDTO;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {

    private final Sql2o sql2o;

    @Autowired
    public UsuarioRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<Usuario> findAll() {
        String sql = "SELECT id, rut, nombre, apellido, email, password, nick, tipo, ST_AsText(ubicacion) as ubicacion FROM usuario";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Usuario.class);
        }
    }

    public Optional<Usuario> findById(Integer id) {
        String sql = "SELECT * FROM usuario WHERE id = :id";

        try (Connection con = sql2o.open()) {
            Usuario usuario = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Usuario.class);
            return Optional.ofNullable(usuario);
        }
    }

    public Usuario findByEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = :email";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("email", email)
                    .executeAndFetchFirst(Usuario.class);
        }
    }

    public Usuario findByNick(String nick) {
        String sql = "SELECT * FROM usuario WHERE nick = :nick";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("nick", nick)
                    .executeAndFetchFirst(Usuario.class);
        }
    }

    // Obtiene Usuario segun email o nick. Utilizado para login
    // Utiliza implicitamente indice en la base de datos
    public Usuario findByEmailOrNick1(String username) {
        Usuario usuario = findByEmail(username);
        if (usuario == null) {
            usuario = findByNick(username);
        }
        return usuario;
    }

    public Usuario findByEmailOrNick(String username) {
        String sql = "SELECT id, rut, nombre, apellido, email, password, nick, tipo, " +
                "ST_AsText(ubicacion) as ubicacion FROM usuario WHERE email = :username OR nick = :username";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("username", username)
                    .executeAndFetchFirst(Usuario.class);
        }
    }


    public Usuario save(Usuario usuario) {
        String sql = "INSERT INTO usuario (rut, nombre, apellido, email, password, nick, tipo, ubicacion) " +
                "VALUES (:rut, :nombre, :apellido, :email, :password, :nick, :tipo, ST_GeomFromText(:ubicacion, 4326))";

        try (Connection con = sql2o.open()) {
            // Cambiamos el manejo del ID generado
            Integer id = (Integer) con.createQuery(sql, true)
                    .addParameter("rut", usuario.getRut())
                    .addParameter("nombre", usuario.getNombre())
                    .addParameter("apellido", usuario.getApellido())
                    .addParameter("email", usuario.getEmail())
                    .addParameter("password", usuario.getPassword())
                    .addParameter("nick", usuario.getNick())
                    .addParameter("tipo", usuario.getTipo())
                    .addParameter("ubicacion", usuario.getUbicacion())
                    .executeUpdate()
                    .getKey();

            usuario.setId(id);
            return usuario;
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) > 0 FROM usuario WHERE email = :email";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("email", email)
                    .executeScalar(Boolean.class);
        }
    }

    public boolean existsByNick(String nick) {
        String sql = "SELECT COUNT(*) > 0 FROM usuario WHERE nick = :nick";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("nick", nick)
                    .executeScalar(Boolean.class);
        }
    }

    public boolean existsByRut(String rut) {
        String sql = "SELECT COUNT(*) > 0 FROM usuario WHERE rut = :rut";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("rut", rut)
                    .executeScalar(Boolean.class);
        }
    }

    public SectorConMasTareasRealizadasCercanasDTO encontrarSectorConMasTareasRealizadasCercanas(Long usuarioId, Double radioKm) {
        int radioMetros = (int)(radioKm * 1000);

        String sql = """
            WITH usuario_referencia AS (
                SELECT 
                    id,
                    ubicacion
                FROM 
                    usuario
                WHERE 
                    id = :usuarioId
            ),
            tareas_cercanas AS (
                SELECT 
                    t.sector_id,
                    COUNT(*) AS tareas_realizadas
                FROM 
                    tarea t
                JOIN 
                    usuario_referencia u ON ST_DWithin(
                        t.ubicacion::geography, 
                        u.ubicacion::geography, 
                        :radioMetros
                    )
                WHERE 
                    t.estado = 'realizada'
                    AND t.eliminado = false
                GROUP BY 
                    t.sector_id
            )
            SELECT 
                s.id AS sectorId,
                s.nombre AS sectorNombre,
                tc.tareas_realizadas AS tareasRealizadas,
                :radioKm AS radioKm
            FROM 
                sector s
            JOIN 
                tareas_cercanas tc ON s.id = tc.sector_id
            ORDER BY 
                tc.tareas_realizadas DESC
            LIMIT 1;
        """;

        try (Connection con = sql2o.open()) {
            SectorConMasTareasRealizadasCercanasDTO resultado = con.createQuery(sql)
                    .addParameter("usuarioId", usuarioId)
                    .addParameter("radioMetros", radioMetros)
                    .addParameter("radioKm", radioKm)
                    .executeAndFetchFirst(SectorConMasTareasRealizadasCercanasDTO.class);

            if (resultado == null) {
                resultado = new SectorConMasTareasRealizadasCercanasDTO();
                resultado.setRadioKm(radioKm);
            }

            return resultado;
        }
    }

    public void update(Usuario usuario) {
        String sql = "UPDATE usuario SET " +
                "rut = :rut, " +
                "nombre = :nombre, " +
                "apellido = :apellido, " +
                "email = :email, " +
                "password = :password, " +
                "nick = :nick, " +
                "tipo = :tipo, " +
                "ubicacion = ST_GeomFromText(:ubicacion, 4326) " +
                "WHERE id = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("rut", usuario.getRut())
                    .addParameter("nombre", usuario.getNombre())
                    .addParameter("apellido", usuario.getApellido())
                    .addParameter("email", usuario.getEmail())
                    .addParameter("password", usuario.getPassword())
                    .addParameter("nick", usuario.getNick())
                    .addParameter("tipo", usuario.getTipo())
                    .addParameter("ubicacion", usuario.getUbicacion())
                    .addParameter("id", usuario.getId())
                    .executeUpdate();
        }
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM usuario WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) > 0 FROM usuario WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeScalar(Boolean.class);
        }
    }


    public List<UsuarioDistanciaDTO> obtenerPromedioDistanciaPorUsuario() {
        String sql = """
            SELECT
                u.id AS usuario_id,
                u.nombre AS nombre_usuario,
                u.apellido AS apellido_usuario,
                AVG(ST_Distance(t.ubicacion, ST_GeographyFromText(ST_AsText(u.ubicacion)))) AS promedio_distancia
            FROM
                tarea t
            JOIN
                usuario u ON t.usuario_id = u.id
            WHERE
                t.estado = 'realizada'
            GROUP BY
                u.id, u.nombre, u.apellido
            ORDER BY
                u.id;
        """;

        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .addColumnMapping("usuario_id", "usuarioId")
                    .addColumnMapping("nombre_usuario", "nombreUsuario")
                    .addColumnMapping("apellido_usuario", "apellidoUsuario")
                    .addColumnMapping("promedio_distancia", "promedioDistancia")
                    .executeAndFetch(UsuarioDistanciaDTO.class);
        }
    }

}