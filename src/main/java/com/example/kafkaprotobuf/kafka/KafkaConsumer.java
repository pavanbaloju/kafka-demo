package com.example.kafkaprotobuf.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "test", groupId = "test")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message: " + message);
    }
}
