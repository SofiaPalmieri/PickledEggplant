package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.ScreenElement;
import com.olenickglobal.elements.events.Event;
import com.olenickglobal.logging.formatters.elementFormatters.RectangleFormatter;
import com.olenickglobal.logging.formatters.elementFormatters.ScreenElementFormatter;

import java.awt.*;

public abstract class EventFormatter<T, U extends Throwable> {

    protected final StringBuilder builder = new StringBuilder();
    protected ElementFormatter<Rectangle> rectFormatter = new RectangleFormatter();
    protected ElementFormatter<ScreenElement> screenElementFormatter = new ScreenElementFormatter();


    public abstract String getMessage(Event<T,U> event);

}
