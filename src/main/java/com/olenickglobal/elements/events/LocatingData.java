package com.olenickglobal.elements.events;

import com.olenickglobal.elements.ScreenElement;

import java.awt.*;

/**
 * Locating action data.
 * @param <T> Type of locator. TODO: See if we'll need a couple of classes, or at least one for image matching that includes minSimilarity.
 * @param timeout Timeout in seconds.
 * @param locator Locator, e.g., text/image.
 * @param searchFrame Boundaries where the search will be executed.
 * @param element Element to be/that was located--if any.
 * @param rectangle Rectangle that was matched--either matched element or simple screen rectangle.
 */
public record LocatingData<T>(double timeout, T locator, Rectangle searchFrame, ScreenElement element,
                              Rectangle rectangle) {
    public LocatingData(double timeout, T locator, Rectangle searchFrame) {
        this(timeout, locator, searchFrame, null, null);
    }

    @SuppressWarnings("unchecked")
    public Class<T> getLocatorType() {
        return (Class<T>) locator.getClass();
    }
}
