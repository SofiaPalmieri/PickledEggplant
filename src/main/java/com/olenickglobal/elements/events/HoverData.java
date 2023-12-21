package com.olenickglobal.elements.events;

import com.olenickglobal.elements.Offset;
import com.olenickglobal.elements.ScreenElement;

import java.awt.*;

/**
 * Hover data.
 * @param timeout Timeout in seconds.
 * @param modifiers Modifier bits.
 * @param element Element to be/that was hovered--if any.
 * @param elementRectangle Rectangle where the element was matched--if any.
 * @param offset Target offset--if any.
 * @param target Target point.
 * @param linger Linger time in seconds.
 */
public record HoverData(double timeout, int modifiers, ScreenElement element, Rectangle elementRectangle, Offset offset,
                        Point target, double linger) {
    public HoverData(int modifiers, Point target, double linger) {
        this(0.0, modifiers, null, null, null, target, linger);
    }
}
