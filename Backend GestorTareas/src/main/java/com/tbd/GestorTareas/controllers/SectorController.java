package com.tbd.GestorTareas.controllers;

import com.tbd.GestorTareas.entities.Sector;
import com.tbd.GestorTareas.services.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sector")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    // Crear
    @PostMapping
    public Sector crearSector(@RequestBody Sector sector) {
        return sectorService.crearSector(sector);
    }

    // Obtener todos
    @GetMapping
    public List<Sector> obtenerSectores() {
        return sectorService.obtenerSectores();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public Sector obtenerSectorPorId(@PathVariable Integer id) {
        return sectorService.obtenerSectorPorId(id);
    }

    // Actualizar
    @PutMapping
    public boolean actualizarSector(@RequestBody Sector sector) {
        return sectorService.actualizarSector(sector);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public boolean eliminarSector(@PathVariable Integer id) {
        return sectorService.eliminarSector(id);
    }
}
