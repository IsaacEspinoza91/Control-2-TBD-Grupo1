package com.tbd.GestorTareas.services;

import com.tbd.GestorTareas.DTO.TareasPendientesPorSectorDTO;
import com.tbd.GestorTareas.DTO.TareasPorSectorDTO;
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

    public Integer crearTarea(Tarea tarea) {
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
}
