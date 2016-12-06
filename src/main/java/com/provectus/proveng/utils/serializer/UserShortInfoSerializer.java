package com.provectus.proveng.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.provectus.proveng.domain.User;

import java.io.IOException;

public class UserShortInfoSerializer extends JsonSerializer<User> {

    @Override
    public void serialize(User user, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", user.getId());
        gen.writeStringField("firstName", user.getFirstName());
        gen.writeStringField("lastName", user.getLastName());
        gen.writeStringField("url", user.getUrl());
        gen.writeEndObject();

    }
}
