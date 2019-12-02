package com.example.kafka_client.validation;

import com.example.kafka_client.model.entity.Message;
import com.example.kafka_client.model.entity.Messages;
import com.example.kafka_client.model.entity.NotDeserializedMessages;
import com.example.kafka_client.util.StringUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessagesRecordValidator {
    private static final Logger LOG = LoggerFactory.getLogger(MessagesRecordValidator.class);

    public static boolean isPermittedToSave(ConsumerRecord<String, Messages> record) {
        Messages messages = record.value();
        if (messages instanceof NotDeserializedMessages) {
            LOG.warn("Offset " + record.offset() + " could't be deserialized. Value: " + StringUtil.buildFromBytes(((NotDeserializedMessages) messages)
                    .getFailedDeserializationInfo()
                    .getData()));
            return false;
        }
        List<Message> messageList = messages.getMessages();

        if (messageList.isEmpty()) {
            LOG.warn("Offset " + record + " shouldn't be saved. MessageModel array is empty");
            return false;
        }

        if (messageList.stream().parallel().anyMatch(message -> !message.isValid())) {
            LOG.warn("Offset " + record + " not matching constrains and shouldn't be saved");
            return false;
        } else {
            return true;
        }
    }
}
