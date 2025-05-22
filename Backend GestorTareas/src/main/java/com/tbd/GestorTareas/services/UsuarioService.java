package com.tbd.GestorTareas.services;

import com.tbd.GestorTareas.entities.Usuario;
import com.tbd.GestorTareas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Validar coordenadas si son requeridas
        if (usuario.getUbicacion() == null) {
            throw new RuntimeException("La ubicación es requerida");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticarUsuario(String emailOrNick, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailOrNick(emailOrNick);

        Usuario usuario = usuarioOpt.orElseThrow(() ->
                new RuntimeException("Nick o Email incorrecto"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }

    // Verificar email no usado. Permite validacion previa al registro
    public Boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Verificar nick no usado. Permite validacion previa al registro
    public Boolean existsByNick(String nick) {
        return usuarioRepository.existsByNick(nick);
    }

    // Verificar rut no usado. Permite validacion previa al registro
    public Boolean existsByRut(String rut) {
        return usuarioRepository.existsByRut(rut);
    }
}