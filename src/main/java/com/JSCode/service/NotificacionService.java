package com.JSCode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NotificacionService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_EMAIL_URL = "http://api-gateway:8080/usuarios/users/getusermail";

    public void enviarNotificacionCorreo(Long userId, Long orderId, String token) {

        System.out.println("Enviando notificación por correo al usuario: " + userId + " sobre el pedido: " + orderId + " con token: " + token);

        String usermail = solicitarCorreo(userId, token);

        if (usermail != null) {

            try {
                emailService.sendNotificationEmail(usermail);
            } catch (Exception e) {
                System.err.println("Error al enviar el correo de notificación: " + e.getMessage());
            }

        } else {
            System.err.println("No se pudo obtener el correo del usuario con ID: " + userId);
        }

    }

    private String solicitarCorreo(Long userId, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromUriString(USER_EMAIL_URL)
                    .queryParam("userId", userId);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("📧 Correo del usuario obtenido: " + response.getBody());
                return response.getBody();
            } else {
                System.err.println("❗ Respuesta HTTP no exitosa: " + response.getStatusCode());
                return null;
            }

        } catch (Exception e) {
            System.err.println("❌ Error al solicitar correo del usuario: " + e.getMessage());
            return null;
        }
    }
}
