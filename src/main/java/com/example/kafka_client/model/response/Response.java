package com.example.kafka_client.model.response;

public enum Response {
    CONNECTION_ERROR("Error while trying to establish connection"),
    DATA_INTEGRITY_VIOLATION("Data integrity violation"),
    EXCEPTION("Exception"),
    OK("OK");

    private final String description;

    Response(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
