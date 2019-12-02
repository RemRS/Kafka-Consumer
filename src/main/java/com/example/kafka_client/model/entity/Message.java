package com.example.kafka_client.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "datatable")
public class Message {

    @Id
    private int messageId;
    private String payload;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean isValid() {
        return this.messageId >= 0;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", payload='" + payload + '\'' +
                '}';
    }
}
