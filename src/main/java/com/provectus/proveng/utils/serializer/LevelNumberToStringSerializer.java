package com.provectus.proveng.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.provectus.proveng.enumaration.LevelType;

import java.io.IOException;

public class LevelNumberToStringSerializer extends JsonSerializer<Integer> {

    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeObject(LevelType.convertToString(value));

    }

}
