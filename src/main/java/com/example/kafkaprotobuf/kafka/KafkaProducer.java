package com.example.kafkaprotobuf.kafka;

import com.example.kafkaprotobuf.model.PersonOuterClass;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, PersonOuterClass.Person> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, PersonOuterClass.Person> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, PersonOuterClass.Person msg) {
        kafkaTemplate.send(topic, "key", msg);
    }
}
