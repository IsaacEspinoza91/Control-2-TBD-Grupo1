package com.tbd.GestorTareas.controllers;

import com.tbd.GestorTareas.entities.Usuario;
import com.tbd.GestorTareas.security.*;
import com.tbd.GestorTareas.services.UsuarioService;
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

            if (usuarioService.existsByRut(request.getRut())) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Error: El rut ya está en uso");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }

            // Validacion email existente
            if (usuarioService.existsByEmail(request.getEmail())) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Error: El email ya está registrado");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }

            // Validacion nick existente
            if (usuarioService.existsByNick(request.getNick())) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Error: El nick ya está en uso");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }

            // creacion de usuario
            Usuario usuario = new Usuario(
                    request.getRut(),
                    request.getNombre(),
                    request.getApellido(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getNick(),
                    request.getTipo(),
                    request.getLat(),
                    request.getLng()
            );

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
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            // Generacion de suario
            Usuario usuarioAutenticado = usuarioService.autenticarUsuario(request.getemailOrNick(), request.getPassword());

            // Generacion de token
            String jwtToken = jwtService.generateToken(new UserDetailsImpl(usuarioAutenticado));

            return ResponseEntity.ok(new AuthResponse(jwtToken, usuarioAutenticado.getTipo(), usuarioAutenticado.getNick()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
