package com.tbd.GestorTareas.controllers;

import com.tbd.GestorTareas.DTO.SectorConMasTareasRealizadasCercanasDTO;
import com.tbd.GestorTareas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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
}