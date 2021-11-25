package com.inventory.exceptions;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ExceptionSerializer extends JsonSerializer<BaseExceptionResponse> {

    @Override
    public void serialize(BaseExceptionResponse e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("code", e.getCode());
        jsonGenerator.writeStringField("message", e.getMessage());
        jsonGenerator.writeEndObject();
    }
}
