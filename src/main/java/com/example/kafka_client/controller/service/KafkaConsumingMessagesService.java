package com.example.kafka_client.controller.service;

import com.example.kafka_client.model.entity.Messages;
import com.example.kafka_client.model.response.Response;
import com.example.kafka_client.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;


@Service
public class KafkaConsumingMessagesService implements MessagesService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumingMessagesService.class);
    private final MessageRepository messageRepository;

    @Autowired
    public KafkaConsumingMessagesService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Response saveMessages(Messages messages) {
        try {
            messageRepository.saveAll(messages.getMessages());

            return Response.OK;
        } catch (DataIntegrityViolationException ex) {
            LOG.warn("Constraint violation for\n" + messages + "\n" + (ex.getMessage() == null ? ex.toString() : ex.getMessage()));

            return Response.DATA_INTEGRITY_VIOLATION;
        } catch (CannotCreateTransactionException ex) {
            LOG.error("Unable to create transaction\n" + (ex.getMessage() == null ? ex.toString() : ex.getMessage()));

            return Response.CONNECTION_ERROR;
        } catch (Exception ex) {
            ex.printStackTrace();

            return Response.EXCEPTION;
        }
    }
}
