package com.tbd.GestorTareas.services;

import com.tbd.GestorTareas.DTO.*;
import com.tbd.GestorTareas.entities.Tarea;
import com.tbd.GestorTareas.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    @Autowired
    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public List<Tarea> obtenerTodas() {
        return tareaRepository.findAll();
    }

    public Tarea obtenerPorId(Long id) {
        return tareaRepository.findById(id);
    }

    //Crear
    public Tarea crearTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public void actualizarTarea(Tarea tarea) {
        tareaRepository.update(tarea);
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

    public TareaCercanaDTO findTareaPendienteMasCercanaSegunUbicacionUsuario(Long usuarioId) {
        return tareaRepository.findTareaPendienteMasCercanaSegunUbicacionUsuario(usuarioId);
    }

    public TareaCercanaDTO findTareaPendienteMasCercanaSegunUbicacionEspecifica(TareaCercanaRequest request) {
        // Validacion coordenadas
        if (request.getLat() == null || request.getLng() == null) {
            throw new IllegalArgumentException("Las coordenadas son requeridas");
        }

        if (request.getLat() < -90 || request.getLat() > 90) {
            throw new IllegalArgumentException("Latitud debe estar entre -90 y 90");
        }

        if (request.getLng() < -180 || request.getLng() > 180) {
            throw new IllegalArgumentException("Longitud debe estar entre -180 y 180");
        }

        return tareaRepository.findTareaPendienteMasCercanaSegunUbicacionEspecifica(request);
    }
}
