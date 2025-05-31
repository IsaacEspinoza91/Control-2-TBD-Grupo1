package com.tbd.GestorTareas.poblador;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Component
public class PobladorUsuariosBase {

    @Autowired
    private Sql2o sql2o;

    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void poblarUsuarios() {
        try (Connection con = sql2o.open()) {
            Long total = con.createQuery("SELECT COUNT(*) FROM usuario")
                    .executeScalar(Long.class);

            if (total > 0) {
                System.out.println("Los usuarios ya existen se omite el poblador.");
                return;
            }

            String sql = "INSERT INTO usuario (rut, nombre, apellido, email, password, ubicacion, nick, tipo) " +
                    "VALUES (:rut, :nombre, :apellido, :email, :password, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :nick, :tipo)";

            insertarUsuario(con, sql, "11111111-1", "Admin", "Uno", "admin@tareas.cl", "admin123", -70.6506, -33.4372, "admin1", "admin");
            insertarUsuario(con, sql, "22222222-2", "Obrero", "Uno", "usuario1@tareas.cl", "usuario123", -70.6700, -33.4200, "Bob El Contructor", "cliente");
            insertarUsuario(con, sql, "33333333-3", "Trabajador", "Dos", "usuario2@tareas.cl", "usuario123", -70.6100, -33.4900, "Felix", "cliente");

            System.out.println("Usuarios insertados correctamente.");
        } catch (Exception e) {
            System.err.println("Error al poblar usuarios: " + e.getMessage());
        }
    }

    private void insertarUsuario(Connection con, String sql, String rut, String nombre, String apellido, String email,
                                 String rawPassword, double lon, double lat, String nick, String tipo) {
        try {
            con.createQuery(sql)
                    .addParameter("rut", rut)
                    .addParameter("nombre", nombre)
                    .addParameter("apellido", apellido)
                    .addParameter("email", email)
                    .addParameter("password", encoder.encode(rawPassword))
                    .addParameter("lon", lon)
                    .addParameter("lat", lat)
                    .addParameter("nick", nick)
                    .addParameter("tipo", tipo)
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("No se pudo insertar usuario " + email + ": " + e.getMessage());
        }
    }
}

