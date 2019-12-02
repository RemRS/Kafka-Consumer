package com.example.kafka_client.model.entity;

import org.springframework.kafka.support.serializer.FailedDeserializationInfo;

public class NotDeserializedMessages extends Messages {
    private final FailedDeserializationInfo failedDeserializationInfo;

    public NotDeserializedMessages(FailedDeserializationInfo failedDeserializationInfo) {
        this.failedDeserializationInfo = failedDeserializationInfo;
    }

    public FailedDeserializationInfo getFailedDeserializationInfo() {
        return this.failedDeserializationInfo;
    }
}
