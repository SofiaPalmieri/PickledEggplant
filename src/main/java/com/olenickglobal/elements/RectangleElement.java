package com.olenickglobal.elements;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.EventType;
import com.olenickglobal.elements.events.ImageLocator;
import com.olenickglobal.elements.events.LocatingData;
import com.olenickglobal.entities.SUT;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.ImageNotFoundException;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.function.Function;

public class RectangleElement extends ScreenElement {
    protected final Function<Rectangle, Rectangle> translationFunction;

    public RectangleElement(Rectangle target, boolean isAbsolute) {
        this(null, target, isAbsolute);
    }

    public RectangleElement(int paddingTop, int paddingRight, int paddingBottom, int paddingLeft) {
        this(null, paddingTop, paddingRight, paddingBottom, paddingLeft);
    }

    public RectangleElement(Function<Rectangle, Rectangle> translationFunction) {
        this(null, translationFunction);
    }

    public RectangleElement(ScreenElement parent, Rectangle target, boolean isAbsolute) {
        this(parent, target, isAbsolute, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    public RectangleElement(ScreenElement parent, int paddingTop, int paddingRight, int paddingBottom, int paddingLeft) {
        this(parent, paddingTop, paddingRight, paddingBottom, paddingLeft, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    public RectangleElement(ScreenElement parent, Function<Rectangle, Rectangle> translationFunction) {
        this(parent, translationFunction, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    public RectangleElement(Rectangle target, boolean isAbsolute, Offset offset) {
        this(null, target, isAbsolute, offset);
    }

    public RectangleElement(int paddingTop, int paddingRight, int paddingBottom, int paddingLeft, Offset offset) {
        this(null, paddingTop, paddingRight, paddingBottom, paddingLeft, offset);
    }

    public RectangleElement(Function<Rectangle, Rectangle> translationFunction, Offset offset) {
        this(null, translationFunction, offset);
    }

    public RectangleElement(ScreenElement parent, Rectangle target, boolean isAbsolute, Offset offset) {
        this(
                parent,
                isAbsolute
                        ? parentRect -> target
                        : pr -> new Rectangle(pr.x + target.x, pr.y + target.y, target.width, target.height),
                offset
        );
    }

    public RectangleElement(ScreenElement parent, int paddingTop, int paddingRight, int paddingBottom, int paddingLeft, Offset offset) {
        this(
                parent,
                pr -> new Rectangle(
                        pr.x + paddingLeft,
                        pr.y + paddingTop,
                        pr.x + pr.width - paddingRight,
                        pr.y + pr.height - paddingBottom),
                offset
        );
    }

    public RectangleElement(ScreenElement parent, Function<Rectangle, Rectangle> translationFunction, Offset offset) {
        // TODO: Different screens?
        super(SUT.getInstance().getScreen(), parent, offset);
        this.translationFunction = translationFunction;
    }

    @Override
    protected Rectangle getMatch(double timeout) throws ElementNotFoundException {
        // TODO: Do we need to emit a LOCATING event here?
        EventType endEventType = EventType.AFTER_LOCATING;
        ElementNotFoundException error = null;
        Rectangle rectangle = null;
        Rectangle parentArea = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_LOCATING, new LocatingData<>(timeout,
                translationFunction, null, this)));
        try {
            parentArea = getParentBoundingRectangle(timeout);
            rectangle = translationFunction.apply(parentArea);
            setLastMatchLocation(rectangle);
        } catch (ImageNotFoundException e) {
            endEventType = EventType.LOCATING_ERROR;
            throw error = new ElementNotFoundException(e);
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new LocatingData<>(timeout,
                    translationFunction, parentArea, this, rectangle), error));
        }
        return rectangle;
    }
}
