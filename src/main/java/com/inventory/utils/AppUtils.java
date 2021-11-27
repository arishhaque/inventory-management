package com.inventory.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class AppUtils {

    public static final  ObjectMapper objectMapper = new ObjectMapper();

    public static String writeValueAsString(Object object) {

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(String str, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(str, valueTypeRef);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
