package com.tbd.GestorTareas.controllers;

import com.tbd.GestorTareas.entities.Notificacion;
import com.tbd.GestorTareas.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {

    private final NotificacionService notificacionService;

    @Autowired
    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping
    public Notificacion crearNotificacion(@RequestBody Notificacion notificacion) {
        return notificacionService.crearNotificacion(notificacion);
    }

    @GetMapping
    public List<Notificacion> obtenerTodas() {
        return notificacionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Notificacion obtenerPorId(@PathVariable Integer id) {
        return notificacionService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public void actualizarNotificacion(@PathVariable Integer id, @RequestBody Notificacion notificacion) {
        notificacion.setId(id);
        notificacionService.actualizarNotificacion(notificacion);
    }

    @DeleteMapping("/{id}")
    public void eliminarNotificacion(@PathVariable Integer id) {
        notificacionService.eliminarNotificacion(id);
    }
}
