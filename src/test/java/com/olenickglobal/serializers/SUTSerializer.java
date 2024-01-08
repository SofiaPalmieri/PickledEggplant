package com.olenickglobal.serializers;

import com.olenickglobal.entities.SUT;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonGenerator;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.SerializerProvider;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SUTSerializer extends StdSerializer<SUT> {

    public SUTSerializer(Class<SUT> t) {
        super(t);
    }

    @Override
    public void serialize(SUT sut, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeEndObject();
    }
}