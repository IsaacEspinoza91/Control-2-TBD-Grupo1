package com.tbd.GestorTareas.controllers;

import com.tbd.GestorTareas.DTO.*;
import com.tbd.GestorTareas.entities.Tarea;
import com.tbd.GestorTareas.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/tarea")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
public class TareaController {

    private final TareaService tareaService;

    @Autowired
    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping("/")
    public List<TareaResponseDTO> getAllTareas() {
        return tareaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public TareaResponseDTO getTareaById(@PathVariable Long id) {
        return tareaService.obtenerPorId(id);
    }

    @PostMapping("/")
    public TareaResponseDTO createTarea(@RequestBody TareaRequestDTO tarea) {
        return tareaService.crearTarea(tarea);
    }

    @PutMapping("/{id}")
    public TareaResponseDTO updateTarea(@PathVariable Long id, @RequestBody TareaRequestDTO tarea) {
        TareaResponseDTO updatedTarea = tareaService.actualizarTarea(id, tarea);
        return updatedTarea;
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
    public ResponseEntity<TareaCercanaResponseDTO> getTareaPendienteMasCercana(@PathVariable Long usuarioId) {
        TareaCercanaResponseDTO tarea = tareaService.findTareaPendienteMasCercanaSegunUbicacionUsuario(usuarioId);

        // Caso tarea no existe
        if (tarea == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tarea);
    }

    @GetMapping("/tarea-cercana")
    public ResponseEntity<TareaCercanaResponseDTO> getTareaPendienteMasCercanaSegunUbicacionEspecifica(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("longitud") Double longitud,
            @RequestParam("latitud") Double latitud) {

        System.out.println("➡️ Controller: llegó tarea-cercana (punto mapa)");

        TareaCercanaRequest request = new TareaCercanaRequest(usuarioId, longitud, latitud);
        TareaCercanaResponseDTO tarea = tareaService.findTareaPendienteMasCercanaSegunUbicacionEspecifica(request);

        if (tarea == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tarea);
    }




    // Nuevos metodos

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TareaResponseDTO>> getTareasByUsuarioId(@PathVariable Long usuarioId) {
        List<TareaResponseDTO> tareas = tareaService.obtenerTareasByUsuarioId(usuarioId);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    // Obtener todas las tareas de la semana actual del usuario segun id usuario
    @GetMapping("/usuario/semana-actual/{usuarioId}")
    public ResponseEntity<List<TareaResponseDTO>> getTareasCurrentWeekByUsuarioId(@PathVariable Long usuarioId) {
        List<TareaResponseDTO> tareas = tareaService.obtenerTareasSemanaActualByUsuarioId(usuarioId);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    // Obtener todas las tareas completadas del usuario segun id usuario
    @GetMapping("/usuario/completadas/{usuarioId}")
    public ResponseEntity<List<TareaResponseDTO>> getCompletedTareasByUsuarioId(@PathVariable Long usuarioId) {
        List<TareaResponseDTO> tareas = tareaService.obtenerTareasCompletadasByUsuarioId(usuarioId);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    // Obtener todas las tareas pendientes del usuario segun id usuario
    @GetMapping("/usuario/pendientes/{usuarioId}")
    public ResponseEntity<List<TareaResponseDTO>> getUncompletedTareasByUsuarioId(@PathVariable Long usuarioId) {
        List<TareaResponseDTO> tareas = tareaService.obtenerTareasPendientesByUsuarioId(usuarioId);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    // Obtener todas las tareas proximas del usuario sin completar segun id usuario
    @GetMapping("/usuario/proximas-incompletas/{usuarioId}")
    public ResponseEntity<List<TareaResponseDTO>> getUpcomingUncompletedTareasByUsuarioId(@PathVariable Long usuarioId) {
        List<TareaResponseDTO> tareas = tareaService.obtenerTareasPendientesProximasByUsuarioId(usuarioId);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    // Cambiar estado de tareas de "realizada" a "pendiente" segun id de tarea
    @PatchMapping("/marcar-pendiente/{id}")
    public ResponseEntity<Void> changeEstadoToPendiente(@PathVariable Long id) {
        boolean updated = tareaService.cambiarEstadoAPendiente(id);
        return updated ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Cambiar estado de tareas de "pendiente" a "realizada" segun id de tarea
    @PatchMapping("marcar-realizada/{id}")
    public ResponseEntity<Void> changeEstadoToRealizada(@PathVariable Long id) {
        boolean updated = tareaService.cambiarEstadoARealizada(id);
        return updated ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Cambiar bool de eliminado de una tarea segun id de tarea
    @PatchMapping("/eliminar/{id}")
    public ResponseEntity<Void> toggleEliminado(@PathVariable Long id) {
        boolean updated = tareaService.cambiarEstadoEliminado(id);
        return updated ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

