package com.example.kafka_client.repository;

import com.example.kafka_client.model.entity.Message;

import java.util.List;

public interface MessageRepository {
    void saveAll(List<Message> messages);
}
