package com.example.req.producer;

import com.example.req.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_DEMO, message);
    }

    public void sendNumber(Integer number) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_TEST, number.toString());
    }
}
