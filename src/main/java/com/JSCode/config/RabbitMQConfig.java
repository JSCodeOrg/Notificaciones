package com.JSCode.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NOTIFICACIONES = "cola_notificaciones";
    public static final String EXCHANGE_NOTIFICACIONES = "exchange_notificaciones";
    public static final String ROUTING_KEY_NOTIFICACION_ASIGNACION = "notificacion.asignacion";

    @Bean
    public Queue queueNotificaciones() {
        return new Queue(QUEUE_NOTIFICACIONES, true);
    }

    @Bean
    public TopicExchange exchangeNotificaciones() {
        return new TopicExchange(EXCHANGE_NOTIFICACIONES);
    }

    @Bean
    public Binding bindingNotificaciones(Queue queueNotificaciones, TopicExchange exchangeNotificaciones) {
        return BindingBuilder.bind(queueNotificaciones).to(exchangeNotificaciones)
                .with(ROUTING_KEY_NOTIFICACION_ASIGNACION);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
