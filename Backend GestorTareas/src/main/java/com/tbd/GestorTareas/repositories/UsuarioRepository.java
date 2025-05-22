package com.tbd.GestorTareas.repositories;


import com.tbd.GestorTareas.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmail(String email);

    Usuario findByNick(String nick);

    // Obtiene Usuario segun email o nick. Utilizado para login
    @Query("SELECT u FROM Usuario u WHERE u.email = :username OR u.nick = :username")
    Optional<Usuario> findByEmailOrNick(@Param("username") String username);

    boolean existsByEmail(String email);

    boolean existsByNick(String nick);

    boolean existsByRut(String rut);

}