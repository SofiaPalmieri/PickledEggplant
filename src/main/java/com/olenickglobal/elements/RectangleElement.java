package com.olenickglobal.elements;

import com.olenickglobal.entities.Screen;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.ImageNotFoundException;

import java.awt.*;
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
        super(new Screen(), parent, offset);
        this.translationFunction = translationFunction;
    }

    @Override
    protected Rectangle getMatch(double timeout) throws ElementNotFoundException {
        try {
            Rectangle area = getParentBoundingRectangle(timeout);
            Rectangle rectangle = translationFunction.apply(area == null ? screen.getBounds() : area);
            setLastMatchLocation(rectangle);
            return rectangle;
        } catch (ImageNotFoundException e) {
            throw new ElementNotFoundException(e);
        }
    }
}
