package com.olenickglobal.elements;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.EventType;
import com.olenickglobal.elements.events.LocatingData;
import com.olenickglobal.entities.SUT;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.ImageNotFoundException;

import java.awt.*;
import java.time.LocalDateTime;

public class TextElement extends ScreenElement {
    protected final String text;

    public TextElement(String text) {
        this(text, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    public TextElement(String text, Offset offset) {
        // TODO: Different screens?
        super(SUT.getInstance().getScreen(), offset);
        this.text = text;
    }

    public TextElement(ScreenElement parent, String text) {
        this(parent, text, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    public TextElement(ScreenElement parent, String text, Offset offset) {
        // TODO: Different screens?
        super(SUT.getInstance().getScreen(), parent, offset);
        this.text = text;
    }

    @Override
    protected Rectangle getMatch(double timeout) throws ElementNotFoundException {
        EventType endEventType = EventType.AFTER_LOCATING;
        ElementNotFoundException error = null;
        Rectangle rectangle = null;
        Rectangle parentArea = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_LOCATING, new LocatingData<>(timeout, text, null, this)));
        try {
            parentArea = getParentBoundingRectangle(timeout);
            rectangle = screen.findText(timeout, parentArea, text);
            setLastMatchLocation(rectangle);
        } catch (ImageNotFoundException e) {
            endEventType = EventType.LOCATING_ERROR;
            throw error = new ElementNotFoundException(e);
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new LocatingData<>(timeout,
                    text, parentArea, this, rectangle), error));
        }
        return rectangle;
    }
}
