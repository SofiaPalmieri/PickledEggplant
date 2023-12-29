package com.olenickglobal.elements;

import com.olenickglobal.entities.Screen;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.ImageNotFoundException;

import java.awt.*;

public class TextElement extends ScreenElement {
    protected final String text;

    public TextElement(String text) {
        this(text, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    public TextElement(String text, Offset offset) {
        // TODO: Different screens?
        super(new Screen(), offset);
        this.text = text;
    }

    public TextElement(ScreenElement parent, String text) {
        this(parent, text, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    public TextElement(ScreenElement parent, String text, Offset offset) {
        // TODO: Different screens?
        super(new Screen(), parent, offset);
        this.text = text;
    }

    @Override
    protected Rectangle getMatch(double timeout) throws ElementNotFoundException {
        try {
            Rectangle area = getParentBoundingRectangle(timeout);
            Rectangle rectangle = screen.internalFindText(timeout, area, text);
            setLastMatchLocation(rectangle);
            return rectangle;
        } catch (ImageNotFoundException e) {
            throw new ElementNotFoundException(e);
        }
    }
}
