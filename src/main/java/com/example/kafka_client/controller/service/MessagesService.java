package com.example.kafka_client.controller.service;

import com.example.kafka_client.model.entity.Messages;
import com.example.kafka_client.model.response.Response;


public interface MessagesService {
    Response saveMessages(Messages messages);
}
