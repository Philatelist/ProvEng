package com.provectus.proveng.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.provectus.proveng.domain.BaseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionOnlyIdSerializer extends JsonSerializer<Collection<BaseEntity>> {

    @Override
    public void serialize(Collection<BaseEntity> entities, JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {

        List<OnlyId> list = new ArrayList<>();

        for (BaseEntity entity : entities) {
            list.add(new OnlyId(entity.getId()));
        }
        gen.writeObject(list);
    }

    private static class OnlyId {

        private Long id;

        OnlyId(Long id) {
            this.setId(id);
        }

        @SuppressWarnings("unused")
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
