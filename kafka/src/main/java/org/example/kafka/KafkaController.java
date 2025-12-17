package org.example.kafka;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    final KafkaService kafkaService;

    public KafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @PostMapping("/send")
    ResponseEntity<String> sendMessage(@RequestParam String message) {
        kafkaService.sendMessage(message);
        return ResponseEntity.ok("sent message: " + message);
    }

    @PostMapping("/keyed")
    ResponseEntity<String> keyedMessage(@RequestParam String key, @RequestParam String message) {
        kafkaService.sendMessage(key, message);
        return ResponseEntity.ok("keyed message (%s): %s".formatted(key, message));
    }
}
