package com.provectus.proveng.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.provectus.proveng.domain.BaseEntity;

import java.io.IOException;

public class EntityOnlyIdSerializer extends JsonSerializer<BaseEntity> {

    @Override
    public void serialize(BaseEntity value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeEndObject();

    }

}
