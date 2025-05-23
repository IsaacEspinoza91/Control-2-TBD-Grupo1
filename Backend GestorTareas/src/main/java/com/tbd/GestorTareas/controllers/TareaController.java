package com.tbd.GestorTareas.controllers;

import com.tbd.GestorTareas.DTO.*;
import com.tbd.GestorTareas.entities.Tarea;
import com.tbd.GestorTareas.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarea")
public class TareaController {

    private final TareaService tareaService;

    @Autowired
    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping("/{id}")
    public Tarea getTareaById(@PathVariable Long id) {
        return tareaService.obtenerPorId(id);
    }

    @PostMapping
    public Tarea createTarea(@RequestBody Tarea tarea) {
        return tareaService.crearTarea(tarea);
    }

    @PutMapping("/{id}")
    public void updateTarea(@PathVariable Integer id, @RequestBody Tarea tarea) {
        tarea.setId(id);
        tareaService.actualizarTarea(tarea);
    }

    @DeleteMapping("/{id}")
    public void deleteTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
    }

    @GetMapping("/pendientes-por-sector")
    public List<TareasPendientesPorSectorDTO> getTareasPendientesPorSector() {
        System.out.println("➡️ Controller: llegó la petición pendientes por sector");
        return tareaService.obtenerTareasPendientesPorSector();
    }

    @GetMapping("/por-sector/{nick}")
    public List<TareasPorSectorDTO> getTareasPorSector(@PathVariable String nick) {
        System.out.println("➡️ Controller: llegó la petición por sector");
        return tareaService.obtenerTareasPorSector(nick);
    }

    @GetMapping("/tareas-realizadas-sector")
    public List<TareasRealizadasUsuariosSectorDTO> getTareasRealizadasSector() {
        System.out.println("➡️ Controller: llego la peticion de realizadas sector");
        return tareaService.obtenerTareasRealizadasUsuariosSector();
    }

    @GetMapping("/usuario/{usuarioId}/tarea-cercana")
    public ResponseEntity<TareaCercanaDTO> getTareaPendienteMasCercana(@PathVariable Long usuarioId) {
        TareaCercanaDTO tarea = tareaService.findTareaPendienteMasCercanaSegunUbicacionUsuario(usuarioId);

        // Caso tarea no existe
        if (tarea == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tarea);
    }

    @PostMapping("/tarea-cercana")
    public ResponseEntity<TareaCercanaDTO> getTareaPendienteMasCercana(@RequestBody TareaCercanaRequest request) {
        TareaCercanaDTO tarea = tareaService.findTareaPendienteMasCercanaSegunUbicacionEspecifica(request);

        if (tarea == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tarea);
    }
}

