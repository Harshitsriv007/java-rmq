package com.example.req.listener;

import com.example.req.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DEMO)
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TEST)
    public void receiveDataMessage(int number) {
        System.out.println("Received number: " + number);
    }
}
