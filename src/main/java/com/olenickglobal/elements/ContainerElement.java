package com.olenickglobal.elements;

import com.olenickglobal.entities.SUT;
import com.olenickglobal.exceptions.ElementNotFoundException;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Container element. This is so that we can represent an element by specifying its boundaries with ImageElements.
 * It will have no parent.
 */
public class ContainerElement extends ScreenElement {
    protected final List<ScreenElement> contents;

    public ContainerElement(ScreenElement... contents) {
        // TODO: Different screens?
        super(SUT.getInstance().getScreen(), new FixedOffset(Alignment.CENTER, 0, 0));
        this.contents = Arrays.asList(contents);
    }

    @Override
    protected Rectangle getMatch(double timeout) throws ElementNotFoundException {
        // TODO: Do we need to emit a LOCATING event here?
        Rectangle screenBounds = screen.getBounds();
        int x = screenBounds.x;
        int y = screenBounds.y;
        int w = screenBounds.width;
        int h = screenBounds.height;
        for (ScreenElement element : contents) {
            Rectangle eBounds = element.getLastMatchLocation();
            // TODO: See if it would make sense to check the elapsed time and reduce the timeout at the calling methods.
            eBounds = isUsingCachedParentLocation() && eBounds != null ? eBounds : element.getMatch(timeout);
            if (eBounds.x < x) x = eBounds.x;
            if (eBounds.y < y) y = eBounds.y;
            if (eBounds.width > w) w = eBounds.width;
            if (eBounds.height > h) h = eBounds.height;
        }
        Rectangle rectangle = new Rectangle(x, y, w, h);
        setLastMatchLocation(rectangle);
        return rectangle;
    }
}
