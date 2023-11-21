package com.example.kafkaprotobuf.pact;

import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.kafkaprotobuf.model.PersonOuterClass;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "kafkaProtoProducer", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V4)
class KafkaProtoConsumerContractTest {

    @Pact(consumer = "kafkaProtoConsumer", provider = "kafkaProtoProducer")
    public V4Pact kafkaProtoConsumerPact(PactBuilder builder) {
        return builder
            .usingPlugin("protobuf")
            .expectsToReceive("message with person details", "core/interaction/message")
            .with(Map.of(
                "message.contents", Map.of(
                    "pact:proto", PactBuilder.filePath("src/main/proto/Person.proto"),
                    "pact:message-type", "Person",
                    "pact:content-type", "application/protobuf"
                )
            ))
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "kafkaProtoConsumerPact")
    void validatePersonMessage(V4Interaction.AsynchronousMessage message) throws InvalidProtocolBufferException {
        PersonOuterClass.Person person = PersonOuterClass.Person.parseFrom(message.getContents().getContents().getValue());
        assertThat(person.getName()).isEmpty();
        assertThat(person.getAge()).isZero();
    }
}
