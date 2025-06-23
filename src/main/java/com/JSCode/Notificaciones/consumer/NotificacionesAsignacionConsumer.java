package com.JSCode.Notificaciones.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.JSCode.DTO.NotificacionAsignacionDTO;
import com.JSCode.config.RabbitMQConfig;

@Service
public class NotificacionesAsignacionConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NOTIFICACIONES)
    public void recibirNotificacionAsignacion(NotificacionAsignacionDTO receptor) {
        try {
            System.out.println("Recibida notificación de asignación:");
            System.out.println("Order ID: " + receptor.getOrderId());
            System.out.println("User ID: " + receptor.getUserId());
            
        } catch (Exception e) {
            System.err.println("Error al procesar mensaje de notificación: " + e.getMessage());
        }
    }
}