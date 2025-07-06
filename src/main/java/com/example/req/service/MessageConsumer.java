package com.example.req.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @RabbitListener(queues = "demo-queue")
    public void receiveMessage(String message) {
        try {
            System.out.println("✅ Received message: " + message);
            // your business logic here
        } catch (Exception e) {
            System.err.println("❌ Error processing message: " + e.getMessage());
            throw e; // causes requeue or DLQ (if configured)
        }
    }

    @RabbitListener(queues = "test-queue")
    public void receiveDataMessage(int number) {
        try {
            System.out.println("✅ Received message: " + number);
            // your business logic here
        } catch (Exception e) {
            System.err.println("❌ Error processing message: " + e.getMessage());
            throw e; // causes requeue or DLQ (if configured)
        }
    }
}
