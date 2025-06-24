package com.JSCode.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Service
public class EmailService {

    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;

    @Value("${email.host}")
    private String host;

    @Value("${email.port}")
    private String port;

    public void sendNotificationEmail(String toEmail) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("🚚 ¡Tu entrega está en camino!");

            String htmlContent = """
                    <!DOCTYPE html>
                    <html lang='es'>
                    <head>
                      <meta charset='UTF-8'>
                      <style>
                        body {
                          background: #f4f4f4;
                          font-family: 'Segoe UI', sans-serif;
                          color: #333;
                          padding: 0;
                          margin: 0;
                        }
                        .container {
                          max-width: 600px;
                          margin: 40px auto;
                          background: #ffffff;
                          border-radius: 8px;
                          padding: 30px;
                          box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                        }
                        h2 {
                          color: #6a0dad;
                          text-align: center;
                        }
                        p {
                          font-size: 16px;
                          line-height: 1.6;
                          margin: 20px 0;
                        }
                        .status {
                          background-color: #6a0dad;
                          color: white;
                          padding: 12px 20px;
                          text-align: center;
                          border-radius: 6px;
                          font-size: 18px;
                          margin-top: 30px;
                          font-weight: bold;
                        }
                        .footer {
                          margin-top: 40px;
                          font-size: 12px;
                          color: #999;
                          text-align: center;
                        }
                      </style>
                    </head>
                    <body>
                      <div class="container">
                        <h2>¡Buenas noticias!</h2>
                        <p>Tu pedido ha sido preparado y ya está en camino hacia tu dirección.</p>
                        <p>Pronto recibirás tu compra en la puerta de tu casa. Gracias por confiar en nosotros.</p>
                        <div class="status">📦 Entrega en camino</div>
                        <p style="margin-top: 30px;">Si tienes alguna duda o inconveniente, puedes contactarnos respondiendo a este correo.</p>
                        <div class="footer">© 2025 Shop. Todos los derechos reservados.</div>
                      </div>
                    </body>
                    </html>
                    """;

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new MessagingException("Error al enviar el correo electrónico", e);
        }
    }
}
