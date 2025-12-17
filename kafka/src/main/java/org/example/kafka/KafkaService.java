package org.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class KafkaService {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        LOG.debug("sending kafka message: {}", message);
        kafkaTemplate.send("message.sent", message);
    }

    @KafkaListener(topics = "message.sent", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> record) {
        String key =  record.key();
        LOG.debug("consuming message ({}): {}", key, record.value());

        if (Objects.equals(key, "user")) {
            LOG.debug("USER: {}", record.value());
        }
    }

    public void sendMessage(String key, String message) {
        LOG.debug("sending kafka message ({}): {}", key, message);
        kafkaTemplate.send("message.sent", key, message);
    }
}
