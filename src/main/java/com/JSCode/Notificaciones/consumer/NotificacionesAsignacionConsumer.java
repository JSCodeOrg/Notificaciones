package com.JSCode.Notificaciones.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.JSCode.DTO.NotificacionAsignacionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotificacionesAsignacionConsumer {

    @RabbitListener(queues = "cola_notificaciones")
    public void recibirNotificacionAsignacion(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            NotificacionAsignacionDTO notificacion = objectMapper.readValue(message, NotificacionAsignacionDTO.class);
            
            System.out.println("Recibida notificación de asignación:");
            System.out.println("Order ID: " + notificacion.getOrderId());
            System.out.println("User ID: " + notificacion.getUserId());
            
            // Aquí puedes procesar la notificación como necesites
            
        } catch (Exception e) {
            System.err.println("Error al procesar mensaje de notificación: " + e.getMessage());
        }
    }
}