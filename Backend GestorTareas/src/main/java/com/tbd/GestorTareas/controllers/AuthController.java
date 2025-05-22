package com.tbd.GestorTareas.controllers;

import com.tbd.GestorTareas.entities.Usuario;
import com.tbd.GestorTareas.security.*;
import com.tbd.GestorTareas.services.UsuarioService;
import com.tbd.GestorTareas.util.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public AuthController(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // MAP para mostrar respuestas erroenas
            Map<String, String> errorResponse = new HashMap<>();

            // Validacion rut ya existe
            if (usuarioService.existsByRut(request.getRut())) {
                errorResponse.put("message", "Error: El rut ya está en uso");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Validacion email existente
            if (usuarioService.existsByEmail(request.getEmail())) {
                errorResponse.put("message", "Error: El email ya está registrado");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Validacion nick existente
            if (usuarioService.existsByNick(request.getNick())) {
                errorResponse.put("message", "Error: El nick ya está en uso");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Crear punto de ubicación
            Point ubicacion = GeometryUtil.createPoint(request.getLat(), request.getLng());

            // creacion de usuario. Patron BUILDER
            Usuario usuario = new Usuario();
            usuario.setRut(request.getRut());
            usuario.setNombre(request.getNombre());
            usuario.setApellido(request.getApellido());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(request.getPassword());
            usuario.setNick(request.getNick());
            usuario.setTipo(request.getTipo());
            usuario.setUbicacion(ubicacion);

            // Usuario registrado
            Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);

            // Generacion de token
            String jwtToken = jwtService.generateToken(new UserDetailsImpl(usuarioRegistrado));

            return ResponseEntity.ok(new AuthResponse(jwtToken, usuarioRegistrado.getTipo(), usuarioRegistrado.getNick()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error durante el registro: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Generacion de suario
            Usuario usuarioAutenticado = usuarioService.autenticarUsuario(request.getemailOrNick(), request.getPassword());

            // Generacion de token
            String jwtToken = jwtService.generateToken(new UserDetailsImpl(usuarioAutenticado));

            return ResponseEntity.ok(new AuthResponse(jwtToken, usuarioAutenticado.getTipo(), usuarioAutenticado.getNick()));
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
