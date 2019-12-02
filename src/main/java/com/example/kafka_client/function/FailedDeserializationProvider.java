package com.example.kafka_client.function;

import com.example.kafka_client.model.entity.NotDeserializedMessages;
import org.springframework.kafka.support.serializer.FailedDeserializationInfo;

import java.util.function.Function;

public class FailedDeserializationProvider implements Function<FailedDeserializationInfo, NotDeserializedMessages> {

    @Override
    public NotDeserializedMessages apply(FailedDeserializationInfo info) {
        return new NotDeserializedMessages(info);
    }
}
