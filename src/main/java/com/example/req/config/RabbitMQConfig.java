package com.example.req.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_DEMO = "demo-queue";
    public static final String QUEUE_TEST = "test-queue";

    @Bean
    public Queue demoqueue() {
        return new Queue(QUEUE_DEMO, true);
    }

    @Bean
    public Queue tesQueue() {
        return new Queue(QUEUE_TEST, true);
    }


}
