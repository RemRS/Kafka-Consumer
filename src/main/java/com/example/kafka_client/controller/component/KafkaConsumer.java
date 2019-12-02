package com.example.kafka_client.controller.component;

import com.example.kafka_client.controller.service.MessagesService;
import com.example.kafka_client.model.entity.Messages;
import com.example.kafka_client.model.response.Response;
import com.example.kafka_client.validation.MessagesRecordValidator;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class KafkaConsumer implements ManualCommittingConsumer<String, Messages> {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);
    private final MessagesService service;

    @Autowired
    public KafkaConsumer(MessagesService service) {
        this.service = service;
    }

    @KafkaListener(topics = "test2", containerFactory = "kafkaListenerContainerFactory")
    public void listen(List<ConsumerRecord<String, Messages>> consumerRecords, Consumer<String, Messages> consumer) {
        LOG.debug("Consuming data " + consumerRecords + " with thread " + Thread.currentThread().getId());
        for (ConsumerRecord<String, Messages> record : consumerRecords) {
            if (!MessagesRecordValidator.isPermittedToSave(record)) {
                commitAsync(consumer, Collections.singletonMap(new TopicPartition(record.topic(), record.partition()),
                        new OffsetAndMetadata(record.offset() + 1L)));
                continue;
            }
            Response response = service.saveMessages(record.value());

            if (response == Response.CONNECTION_ERROR) {
                seek(consumer,
                        new TopicPartition(record.topic(), record.partition()),
                        new OffsetAndMetadata(record.offset()));

                return;
            } else {
                commitAsync(consumer, Collections.singletonMap(new TopicPartition(record.topic(), record.partition()),
                        new OffsetAndMetadata(record.offset() + 1L)));
            }
        }
    }

    @Override
    public void commitSync(Consumer<String, Messages> consumer, Map<TopicPartition, OffsetAndMetadata> records) {
        consumer.commitSync(records);
    }

    @Override
    public void commitAsync(Consumer<String, Messages> consumer, Map<TopicPartition, OffsetAndMetadata> records) {
        consumer.commitAsync(records, (map, e) -> {
            if (e != null) {
                LOG.error("Unable to commit records\n" + map + "\nError: " + (e.getMessage() == null ?
                        e.toString() :
                        e.getMessage()));
            } else {
                LOG.info("Records " + map + " committed successfully");
            }
        });
    }

    @Override
    public void seek(Consumer<String, Messages> consumer, TopicPartition topicPartition, OffsetAndMetadata offsetAndMetadata) {
        consumer.seek(topicPartition, offsetAndMetadata);
    }
}
