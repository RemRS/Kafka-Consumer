package com.example.kafka_client.controller.component;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

public interface ManualCommittingConsumer<T1, T2> {
    void commitSync(Consumer<T1, T2> consumer, Map<TopicPartition, OffsetAndMetadata> records);

    void commitAsync(Consumer<T1, T2> consumer, Map<TopicPartition, OffsetAndMetadata> records);

    void seek(Consumer<T1, T2> consumer, TopicPartition topicPartition, OffsetAndMetadata offsetAndMetadata);
}
