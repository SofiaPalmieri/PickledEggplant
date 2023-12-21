package com.olenickglobal.elements.events;

import com.olenickglobal.elements.Offset;
import com.olenickglobal.elements.ScreenElement;

import java.awt.*;

/**
 * Click data.
 * @param action Click action type.
 * @param timeout Timeout in seconds.
 * @param modifiers Modifiers (bits).
 * @param element Element to be/that was clicked--if any.
 * @param elementRectangle Rectangle where the element was matched--if any.
 * @param offset Element offset--if any.
 * @param target Target point.
 * @param delay Delay in seconds before click action.
 */
public record ClickData(ClickActionType action, double timeout, int modifiers, ScreenElement element,
                        Rectangle elementRectangle, Offset offset, Point target, double delay) {
    public enum ClickActionType {
        DOUBLE_CLICK,
        LEFT_CLICK,
        RIGHT_CLICK,
    }

    public ClickData(ClickActionType action, int modifiers, Point target, double delay) {
        this(action, 0.0, modifiers, null, null, null, target, delay);
    }
}
