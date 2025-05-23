package com.tbd.GestorTareas.controllers;

import com.tbd.GestorTareas.DTO.TareasPendientesPorSectorDTO;
import com.tbd.GestorTareas.DTO.TareasPorSectorDTO;
import com.tbd.GestorTareas.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
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
}

