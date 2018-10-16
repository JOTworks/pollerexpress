package com.shared.utilities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.shared.models.Command;

import java.io.IOException;


public class CommandSerializer extends JsonSerializer<Command>
{
    @Override
    public void serialize(Command command, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("_className", command.getClassName() );
        jsonGenerator.writeStringField("_methodName", command.getMethodName());
        jsonGenerator.writeNumberField("size",  command.getParamTypes().length);
        jsonGenerator.writeArrayFieldStart("_paramTypes");
        for(Class<?> p: command.getParamTypes())
        {
            jsonGenerator.writeObject(p);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeArrayFieldStart("_paramValues");
        for(Object p: command.getParamValues() )
        {
            jsonGenerator.writeObject(p);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}

/*
{
@Override
public void serialize(Command basketItem,
        JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider) throws IOException, JsonProcessingException
        {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("detail");
        jsonGenerator.writeStringField("product", basketItem.getProduct());
        jsonGenerator.writeStringField("code", basketItem.getCode());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeNumberField("amount", basketItem.getAmount());
        jsonGenerator.writeEndObject();
        }
        }*/

