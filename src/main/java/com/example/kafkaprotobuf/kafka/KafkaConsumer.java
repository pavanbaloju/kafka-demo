package com.example.kafkaprotobuf.kafka;

import com.example.kafkaprotobuf.model.PersonOuterClass;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "test", groupId = "test", containerFactory = "kafkaListenerContainerFactory")
    public void listen(DynamicMessage message) throws InvalidProtocolBufferException {
        PersonOuterClass.Person person = PersonOuterClass.Person.parseFrom(message.toByteArray());
        System.out.println("Received Message: " + person);
    }
}
