package com.example.kafkaprotobuf.pact;

import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.example.kafkaprotobuf.model.PersonOuterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

@Provider("kafkaProtoProducer")
@Consumer("kafkaProtoConsumer")
@PactBroker(url = "http://localhost:9292/")
class KafkaProtoProviderVerificationTest {

    @BeforeEach
    void before(PactVerificationContext context) {
        System.setProperty("pact.verifier.publishResults", "true");
        context.setTarget(new MessageTestTarget());
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @PactVerifyProvider("message with person details")
    MessageAndMetadata verifyPersonMessage() {
        PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder().setName("Pavan").setAge(50).build();
        Map<String, Object> metadata = Map.of("content-type", "application/protobuf");

        return new MessageAndMetadata(person.toByteArray(), metadata);
    }
}
