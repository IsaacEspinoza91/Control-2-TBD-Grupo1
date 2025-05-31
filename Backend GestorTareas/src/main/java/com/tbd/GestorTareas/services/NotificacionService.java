package com.tbd.GestorTareas.services;

import com.tbd.GestorTareas.entities.Notificacion;
import com.tbd.GestorTareas.repositories.NotificacionRepository;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class NotificacionService {

    private static final Logger logger = LoggerFactory.getLogger(NotificacionService.class);

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    // Lógica programada: ejecuta cada hora exacta
    @Scheduled(cron = "*/30 * * * * *") // cada 30 segundos
    public void generarNotificacionesProgramadas() {
        logger.info("⏰ Ejecutando verificación de tareas por vencer...");
        notificacionRepository.generarNotificacionesPorVencer();
    }

    // También puedes dejar este método público para pruebas manuales
    public void generarNotificacionesPorVencer() {
        notificacionRepository.generarNotificacionesPorVencer();
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

    public List<Notificacion>  obtenerPorIdUsuarioNoVisto(Integer id) {
        return notificacionRepository.getByIdUserNoVisto(id);
    }

    public List<Notificacion>  obtenerPorIdUsuarioTotal(Integer id) {
        return notificacionRepository.getByIdUserTotal(id);
    }

    public boolean marcarComoVista(Integer id) {
        return notificacionRepository.marcarComoVista(id);
    }

    public void actualizarNotificacion(Notificacion notificacion) {
        notificacionRepository.update(notificacion);
    }

    public void eliminarNotificacion(Integer id) {
        notificacionRepository.delete(id);
    }
}
