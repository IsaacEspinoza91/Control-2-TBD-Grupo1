package com.tbd.GestorTareas.services;

import com.tbd.GestorTareas.entities.Notificacion;
import com.tbd.GestorTareas.repositories.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public Notificacion crearNotificacion(Notificacion notificacion) {
        return notificacionRepository.create(notificacion);
    }

    public List<Notificacion> obtenerTodas() {
        return notificacionRepository.getAll();
    }

    public Notificacion obtenerPorId(Integer id) {
        return notificacionRepository.getById(id);
    }

    public void actualizarNotificacion(Notificacion notificacion) {
        notificacionRepository.update(notificacion);
    }

    public void eliminarNotificacion(Integer id) {
        notificacionRepository.delete(id);
    }
}
