package com.tbd.GestorTareas.repositories;


import com.tbd.GestorTareas.entities.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {

    private final Sql2o sql2o;

    @Autowired
    public UsuarioRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
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
    public Usuario findByEmailOrNick(String username) {
        Usuario usuario = findByEmail(username);
        if (usuario == null) {
            usuario = findByNick(username);
        }
        return usuario;
    }


    public Usuario save(Usuario usuario) {
        String sql = "INSERT INTO usuario (rut, nombre, apellido, email, password, nick, tipo) " +
                "VALUES (:rut, :nombre, :apellido, :email, :password, :nick, :tipo)";

        try (Connection con = sql2o.open()) {
            // Cambiamos el manejo del ID generado
            Integer id = (Integer) con.createQuery(sql, true)
                    .bind(usuario)
                    .executeUpdate()
                    .getKey();

            usuario.setId(id); // Convertimos a long
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
}