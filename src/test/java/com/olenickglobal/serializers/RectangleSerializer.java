package com.olenickglobal.serializers;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonGenerator;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.SerializerProvider;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.awt.*;
import java.io.IOException;

public class RectangleSerializer extends StdSerializer<Rectangle> {

    public RectangleSerializer(Class<Rectangle> t) {
        super(t);
    }

    @Override
    public void serialize(Rectangle rectangle, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        // Serialize each field of the Rectangle
        jsonGenerator.writeNumberField("x", rectangle.x);
        jsonGenerator.writeNumberField("y", rectangle.y);
        jsonGenerator.writeNumberField("width", rectangle.width);
        jsonGenerator.writeNumberField("height", rectangle.height);

        // End writing the Rectangle object
        jsonGenerator.writeEndObject();
    }


}