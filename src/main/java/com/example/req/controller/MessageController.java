package com.example.req.controller;

import com.example.req.dto.MessageRequest;
import com.example.req.producer.MessageProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageProducer messageProducer;

    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody MessageRequest request){
        messageProducer.sendMessage(request.getMessage());
        return ResponseEntity.ok("✅ Sent message: " + request.getMessage());
    }
}
