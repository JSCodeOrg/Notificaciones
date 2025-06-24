package com.JSCode.Notificaciones.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.JSCode.DTO.NotificacionAsignacionDTO;
import com.JSCode.config.RabbitMQConfig;
import com.JSCode.service.NotificacionService;

@Service
public class NotificacionesAsignacionConsumer {

    @Autowired
    private NotificacionService notificacionService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NOTIFICACIONES)
    public void recibirNotificacionAsignacion(NotificacionAsignacionDTO receptor) {
        try {
            System.out.println("Recibida notificación de asignación:");
            System.out.println("Order ID: " + receptor.getOrderId());
            System.out.println("User ID: " + receptor.getUserId());
            System.out.println("User ID: " + receptor.getToken());

            notificacionService.enviarNotificacionCorreo(receptor.getUserId(), receptor.getOrderId(), receptor.getToken());

            
            
        } catch (Exception e) {
            System.err.println("Error al procesar mensaje de notificación: " + e.getMessage());
        }
    }
}