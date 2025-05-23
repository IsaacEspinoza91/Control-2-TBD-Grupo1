package com.tbd.GestorTareas.services;

import com.tbd.GestorTareas.DTO.UsuarioDistanciaDTO;
import com.tbd.GestorTareas.entities.Usuario;
import com.tbd.GestorTareas.DTO.SectorConMasTareasRealizadasCercanasDTO;
import com.tbd.GestorTareas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener usuario por id
    public Usuario obtenerUsuarioPorId(Integer id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        return usuarioOpt.orElse(null);
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

    // Actualizar usuario
    public Usuario actualizarUsuario(Integer id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Actualiza solo los campos que quieres permitir modificar
            usuario.setRut(usuarioActualizado.getRut());
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setNick(usuarioActualizado.getNick());
            usuario.setTipo(usuarioActualizado.getTipo());
            usuario.setUbicacion(usuarioActualizado.getUbicacion());

            // Si actualizas la contraseña, la encriptas
            if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
                usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
            }

            return usuarioRepository.save(usuario);
        } else {
            return null; // Usuario no encontrado
        }
    }

    // Eliminar usuario
    public boolean eliminarUsuario(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<UsuarioDistanciaDTO> obtenerPromedioDistanciaPorUsuario() {
        return usuarioRepository.obtenerPromedioDistanciaPorUsuario();
    }

}