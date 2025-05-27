package com.tbd.GestorTareas.controllers;

import com.tbd.GestorTareas.DTO.SectorConMasTareasRealizadasCercanasDTO;
import com.tbd.GestorTareas.DTO.UsuarioDistanciaDTO;
import com.tbd.GestorTareas.entities.Usuario;
import com.tbd.GestorTareas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Crear nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    // Actualizar usuario por id
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = usuarioService.actualizarUsuario(id, usuarioActualizado);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    // Eliminar usuario por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        boolean eliminado = usuarioService.eliminarUsuario(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{usuarioId}/sector-mas-tareas-realizadas-2km")
    public SectorConMasTareasRealizadasCercanasDTO getSectorConMasTareasRealizadasEn2Km(
            @PathVariable Long usuarioId) {
        return usuarioService.obtenerSectorConMasTareasRealizadasEn2Km(usuarioId);
    }

    // Para futuro uso con 5km
    @GetMapping("/{usuarioId}/sector-mas-tareas-realizadas-5km")
    public SectorConMasTareasRealizadasCercanasDTO getSectorConMasTareasRealizadasEn5Km(
            @PathVariable Long usuarioId) {
        return usuarioService.obtenerSectorConMasTareasRealizadasEn5Km(usuarioId);
    }

    //metodo para obtener el promedio de distancia por usuario
    @GetMapping("/promedio-distancia")
    public ResponseEntity<List<UsuarioDistanciaDTO>> obtenerPromedioDistancia() {
        List<UsuarioDistanciaDTO> resultados = usuarioService.obtenerPromedioDistanciaPorUsuario();
        return ResponseEntity.ok(resultados);
    }

    //consulta 3
    @GetMapping("/promedio-de-distancia")
    public List<UsuarioDistanciaDTO> obtenerPromedioDeDistancia(
            @RequestParam double longitud,
            @RequestParam double latitud) {
        System.out.println("➡️ Controller: promedio-de-distancia");
        return usuarioService.obtenerPromedioDistancia(longitud, latitud);
    }

    @GetMapping("/promedio-distancia/{usuarioId}")
    public ResponseEntity<UsuarioDistanciaDTO> obtenerPromedioDistanciaPorUsuarioId(@PathVariable Long usuarioId) {
        System.out.println("➡️ Controller: promedio-distancia");
        UsuarioDistanciaDTO resultado = usuarioService.obtenerPromedioDistanciaPorUsuarioId(usuarioId);
        if (resultado != null) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}