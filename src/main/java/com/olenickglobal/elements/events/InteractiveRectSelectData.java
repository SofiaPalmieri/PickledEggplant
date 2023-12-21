package com.olenickglobal.elements.events;

import com.olenickglobal.entities.Screen;

import java.awt.*;

/**
 * Interactive rectangle element creation data.
 * @param screen Screen where it will be/has taken place.
 * @param rectangle Rectangle that was selected--or null if not yet/cancelled.
 */
public record InteractiveRectSelectData(Screen screen, Rectangle rectangle) {
    public InteractiveRectSelectData(Screen screen) {
        this(screen, null);
    }
}
