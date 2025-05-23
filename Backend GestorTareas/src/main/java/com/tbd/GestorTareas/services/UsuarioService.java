package com.tbd.GestorTareas.services;

import com.tbd.GestorTareas.entities.Usuario;
import com.tbd.GestorTareas.DTO.SectorConMasTareasRealizadasCercanasDTO;
import com.tbd.GestorTareas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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

    public Usuario autenticarUsuario(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmailOrNick(email);

        if (usuario == null) {
            throw new RuntimeException("Nick o Email incorrecto");
        } else if (!passwordEncoder.matches(password, usuario.getPassword())) {
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

    public SectorConMasTareasRealizadasCercanasDTO obtenerSectorConMasTareasRealizadasEn2Km(Long usuarioId) {
        return usuarioRepository.encontrarSectorConMasTareasRealizadasCercanas(usuarioId, 2.0);
    }

    // Para futuro uso con 5km
    public SectorConMasTareasRealizadasCercanasDTO obtenerSectorConMasTareasRealizadasEn5Km(Long usuarioId) {
        return usuarioRepository.encontrarSectorConMasTareasRealizadasCercanas(usuarioId, 5.0);
    }
}