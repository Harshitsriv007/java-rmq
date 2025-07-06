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

    @PostMapping("/send/int")
    public ResponseEntity<String> sendInt(@RequestBody MessageRequest request){
        System.out.println(request.getNumber());
        for (Integer number : request.getNumber()) {
            messageProducer.sendNumber(number); // send each number to queue
        }
        return ResponseEntity.ok("✅ Sent number message: " + request.getNumber());
    }

//    @PostMapping("/send/int")
//    public ResponseEntity<String> sendTest(@RequestBody String body) {
//        return ResponseEntity.ok("✅ Raw body: " + body);
//    }

}
