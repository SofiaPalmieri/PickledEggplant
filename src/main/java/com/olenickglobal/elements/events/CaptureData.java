package com.olenickglobal.elements.events;

import com.olenickglobal.elements.ScreenElement;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Captured data.
 * @param timeout Timeout in seconds.
 * @param element Element to be/that was captured--if any.
 * @param rectangle Rectangle that was captured--either matched element or simple screen rectangle.
 * @param image Captured image.
 */
public record CaptureData(double timeout, ScreenElement element, Rectangle rectangle, BufferedImage image) {
    public CaptureData(double timeout, ScreenElement element, Rectangle rectangle) {
        this(timeout, element, rectangle, null);
    }

    public CaptureData(Rectangle rectangle) {
        this(rectangle, null);
    }

    public CaptureData(Rectangle rectangle, BufferedImage image) {
        this(0.0, null, rectangle, image);
    }
}
