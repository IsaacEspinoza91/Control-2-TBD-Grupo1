package com.tbd.GestorTareas.services;

import com.tbd.GestorTareas.DTO.*;
import com.tbd.GestorTareas.entities.Tarea;
import com.tbd.GestorTareas.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    @Autowired
    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public List<TareaResponseDTO> obtenerTodas() {
        List<Tarea> tareas = tareaRepository.findAll();
        return tareas.stream()
                .map(TareaResponseDTO::new) // Convierte cada Tarea a TareaResponseDTO
                .collect(Collectors.toList());
    }

    public TareaResponseDTO obtenerPorId(Long id) {
        Tarea tarea = tareaRepository.findById(id);
        return tarea != null ? new TareaResponseDTO(tarea) : null;
    }

    // Crear tarea (recibir DTO, guardar entidad)
    public TareaResponseDTO crearTarea(TareaRequestDTO tareaRequestDTO) {
        // Validar coordenadas en el servicio
        validateCoordinates(tareaRequestDTO.getLatitud(), tareaRequestDTO.getLongitud());

        Tarea tarea = new Tarea();
        tarea.setTitulo(tareaRequestDTO.getTitulo());
        tarea.setDescripcion(tareaRequestDTO.getDescripcion());
        tarea.setFechacreacion(LocalDate.now());
        tarea.setFechavencimiento(tareaRequestDTO.getFechavencimiento());
        tarea.setEstado(tareaRequestDTO.getEstado());
        tarea.setUbicacion(tareaRequestDTO.toWkt()); // Convierte lat/lng a WKT
        tarea.setEliminado(tareaRequestDTO.isEliminado());
        tarea.setUsuario_id(tareaRequestDTO.getUsuario_id());
        tarea.setSector_id(tareaRequestDTO.getSector_id());

        Tarea savedTarea = tareaRepository.save(tarea);
        return new TareaResponseDTO(savedTarea);
    }

    // Actualizar tarea (recibir DTO, actualizar entidad)
    public TareaResponseDTO actualizarTarea(Long id, TareaRequestDTO tareaRequestDTO) {
        Tarea existingTarea = tareaRepository.findById(id);
        if (existingTarea == null) {
            return null; // O lanzar una excepción ResourceNotFoundException
        }

        // Validar coordenadas en el servicio
        validateCoordinates(tareaRequestDTO.getLatitud(), tareaRequestDTO.getLongitud());

        existingTarea.setTitulo(tareaRequestDTO.getTitulo());
        existingTarea.setDescripcion(tareaRequestDTO.getDescripcion());

        existingTarea.setFechavencimiento(tareaRequestDTO.getFechavencimiento());
        existingTarea.setEstado(tareaRequestDTO.getEstado());
        existingTarea.setUbicacion(tareaRequestDTO.toWkt()); // Convierte lat/lng a WKT
        existingTarea.setEliminado(tareaRequestDTO.isEliminado());
        existingTarea.setUsuario_id(tareaRequestDTO.getUsuario_id());
        existingTarea.setSector_id(tareaRequestDTO.getSector_id());

        tareaRepository.update(existingTarea);
        return new TareaResponseDTO(existingTarea);
    }


    public void eliminarTarea(Long id) {
        tareaRepository.delete(id);
    }

    public List<TareasPendientesPorSectorDTO> obtenerTareasPendientesPorSector() {
        return tareaRepository.contarTareasPendientesPorSector();
    }


    public List<TareasPorSectorDTO> obtenerTareasPorSector(String nick) {
        return tareaRepository.contarTareasPorSectorPorNick(nick);
    }

    public List<TareasRealizadasUsuariosSectorDTO> obtenerTareasRealizadasUsuariosSector() {
        return tareaRepository.contarTareasRealizadasUsuariosSector();
    }

    public TareaCercanaResponseDTO findTareaPendienteMasCercanaSegunUbicacionUsuario(Long usuarioId) {
        TareaWithDistanciaDTO tareaConDistancia =  tareaRepository.findTareaPendienteMasCercanaSegunUbicacionUsuario(usuarioId);
        TareaCercanaResponseDTO tareaResponse = new TareaCercanaResponseDTO(tareaConDistancia);
        return tareaResponse;
    }

    public TareaCercanaResponseDTO findTareaPendienteMasCercanaSegunUbicacionEspecifica(TareaCercanaRequest request) {
        validateCoordinates(request.getLatitud(), request.getLongitud());
        TareaWithDistanciaDTO tareaConDistancia =  tareaRepository.findTareaPendienteMasCercanaSegunUbicacionEspecifica(request);
        TareaCercanaResponseDTO tareaResponse = new TareaCercanaResponseDTO(tareaConDistancia);
        return tareaResponse;
    }


    // Metodo privado para validacion de tareas
    private void validateCoordinates(Double lat, Double lng) {
        if (lat == null || lng == null) {
            throw new IllegalArgumentException("Latitud y Longitud son requeridas.");
        }
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("Latitud debe estar entre -90 y 90.");
        }
        if (lng < -180 || lng > 180) {
            throw new IllegalArgumentException("Longitud debe estar entre -180 y 180.");
        }
    }






    // Nuevos metodos para front


    // Obtener todas las tareas de un usuario según id
    public List<TareaResponseDTO> obtenerTareasByUsuarioId(Long usuarioId) {
        List<Tarea> tareas = tareaRepository.findByUsuarioId(usuarioId);
        return tareas.stream()
                .map(TareaResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Obtener todas las tareas de la semana actual del usuario según id usuario
    public List<TareaResponseDTO> obtenerTareasSemanaActualByUsuarioId(Long usuarioId) {
        List<Tarea> tareas = tareaRepository.findTareasSemanaActualByUsuarioId(usuarioId);
        return tareas.stream()
                .map(TareaResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Obtener todas las tareas completadas del usuario según id usuario
    public List<TareaResponseDTO> obtenerTareasCompletadasByUsuarioId(Long usuarioId) {
        List<Tarea> tareas = tareaRepository.findTareasCompletadasByUsuarioId(usuarioId);
        return tareas.stream()
                .map(TareaResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Obtener todas las tareas pendientes del usuario según id usuario
    public List<TareaResponseDTO> obtenerTareasPendientesByUsuarioId(Long usuarioId) {
        List<Tarea> tareas = tareaRepository.findTareasPendientesByUsuarioId(usuarioId);
        return tareas.stream()
                .map(TareaResponseDTO::new)
                .collect(Collectors.toList());
    }


    // Obtener todas las tareas próximas del usuario sin completar según id usuario
    public List<TareaResponseDTO> obtenerTareasPendientesProximasByUsuarioId(Long usuarioId) {
        List<Tarea> tareas = tareaRepository.findTareasPendientesProximasByUsuarioId(usuarioId);
        return tareas.stream()
                .map(TareaResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Cambiar estado de tareas de "realizada" a "pendiente" según id de tarea
    public boolean cambiarEstadoARealizada(Long tareaId) {
        return tareaRepository.cambiarEstadoARealizada(tareaId);
    }

    // Cambiar estado de tareas de "pendiente" a "realizada" según id de tarea
    public boolean cambiarEstadoAPendiente(Long tareaId) {
        return tareaRepository.cambiarEstadoAPendiente(tareaId);
    }

    // Cambiar bool de eliminado de una tarea según id de tarea
    public boolean cambiarEstadoEliminado(Long tareaId) {
        return tareaRepository.cambiarEstadoDeEliminado(tareaId);
    }
}
