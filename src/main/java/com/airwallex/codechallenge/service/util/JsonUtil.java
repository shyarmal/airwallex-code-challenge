package com.airwallex.codechallenge.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        JavaTimeModule timeModule = new JavaTimeModule();
        objectMapper.registerModule(timeModule);
    }

    public static <T> String writeAsString(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return t.toString();
        }
    }
}
