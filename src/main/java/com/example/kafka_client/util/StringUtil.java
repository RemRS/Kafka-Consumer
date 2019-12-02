package com.example.kafka_client.util;

import java.nio.charset.StandardCharsets;

public class StringUtil {

    public static String buildFromBytes(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
