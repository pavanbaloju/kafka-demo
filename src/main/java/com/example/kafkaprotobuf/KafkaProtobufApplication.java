package com.example.kafkaprotobuf;

import com.example.kafkaprotobuf.kafka.KafkaProducer;
import com.example.kafkaprotobuf.model.PersonOuterClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;

@SpringBootApplication
public class KafkaProtobufApplication {

    private static KafkaProducer kafkaProducer;

    public KafkaProtobufApplication(KafkaProducer kafkaProducer) {
        KafkaProtobufApplication.kafkaProducer = kafkaProducer;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(KafkaProtobufApplication.class, args);

        publish();
    }

    private static void publish() throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder()
                .setName("Person " + i)
                .setAge(20 + i)
                .build();

            kafkaProducer.sendMessage("test", person);
            Thread.sleep(Duration.ofSeconds(5));
        }
    }
}
