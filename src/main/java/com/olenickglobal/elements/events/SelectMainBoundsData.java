package com.olenickglobal.elements.events;

import com.olenickglobal.elements.ScreenElement;
import com.olenickglobal.entities.Screen;

import java.awt.*;

/**
 * Interactive rectangle element creation data.
 * @param screen Screen where it will be/has taken place.
 * @param rectangle Rectangle that was selected--or null if not yet/cancelled.
 * @param element Element that was created--or null if not yet/cancelled.
 */
public record SelectMainBoundsData(Screen screen, Rectangle rectangle, ScreenElement element) {
    public SelectMainBoundsData(Screen screen) {
        this(screen, null, null);
    }
}
