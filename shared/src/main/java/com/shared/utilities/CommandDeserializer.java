package com.shared.utilities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.shared.models.Command;

import java.io.IOException;

public class CommandDeserializer extends JsonDeserializer
{
    @Override
    public Command deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException
    {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);

        Command command = new Command();
        command.set_className(jsonNode.get("_className").asText());
        command.set_methodName(jsonNode.get("_methodName").asText());
        //command.set_paramTypes(jsonNode.get("_paramTypes"));

        return command;
    }
}

/*
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
 */