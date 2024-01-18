package com.olenickglobal.logging.formatters.elementFormatters;

import com.olenickglobal.logging.formatters.ElementFormatter;

import java.awt.*;

public class RectangleFormatter implements ElementFormatter<Rectangle> {
    @Override
    public String format(Rectangle element) {
        return element == null ? "" : String.format("Starting point: (%d,%d), width: %d, height: %d", element.x, element.y, element.width, element.height);
    }
}
