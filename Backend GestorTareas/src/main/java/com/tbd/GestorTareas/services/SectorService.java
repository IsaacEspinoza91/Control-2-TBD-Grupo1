package com.tbd.GestorTareas.services;

import com.tbd.GestorTareas.entities.Sector;
import com.tbd.GestorTareas.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    // Crear
    public Sector crearSector(Sector sector) {
        return sectorRepository.crearSector(sector);
    }

    // Obtener todos
    public List<Sector> obtenerSectores() {
        return sectorRepository.getAll();
    }

    // Obtener por ID
    public Sector obtenerSectorPorId(Integer id) {
        return sectorRepository.getById(id);
    }

    // Actualizar
    public boolean actualizarSector(Sector sector) {
        return sectorRepository.updateSector(sector);
    }

    // Eliminar
    public boolean eliminarSector(Integer id) {
        return sectorRepository.deleteSector(id);
    }
}
