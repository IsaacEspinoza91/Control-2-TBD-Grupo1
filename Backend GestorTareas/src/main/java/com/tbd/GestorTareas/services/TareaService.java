package com.tbd.GestorTareas.services;

import com.tbd.GestorTareas.DTO.TareasPendientesPorSectorDTO;
import com.tbd.GestorTareas.DTO.TareasPorSectorDTO;
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


    public List<TareasPendientesPorSectorDTO> obtenerTareasPendientesPorSector() {
        return tareaRepository.contarTareasPendientesPorSector();
    }


    public List<TareasPorSectorDTO> obtenerTareasPorSector(String nick) {
        return tareaRepository.contarTareasPorSectorPorNick(nick);
    }
}
