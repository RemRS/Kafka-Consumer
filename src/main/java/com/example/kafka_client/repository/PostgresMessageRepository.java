package com.example.kafka_client.repository;

import com.example.kafka_client.model.entity.Message;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostgresMessageRepository implements MessageRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public void saveAll(List<Message> messages) {
        for (Message message : messages) {
            em.persist(message);
        }
    }
}
