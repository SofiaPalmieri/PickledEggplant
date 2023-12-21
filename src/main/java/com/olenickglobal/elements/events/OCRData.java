package com.olenickglobal.elements.events;

import com.olenickglobal.elements.ScreenElement;

import java.awt.*;

/**
 * OCR data.
 * @param timeout Timeout in seconds.
 * @param element Element to be/that was OCRed--if any.
 * @param rectangle Rectangle that was OCRed--either matched element or simple screen rectangle.
 * @param text Captured text.
 */
public record OCRData(double timeout, ScreenElement element, Rectangle rectangle, String text) {
    public OCRData(Rectangle rectangle) {
        this(0.0, null, rectangle, null);
    }

    public OCRData(Rectangle rectangle, String text) {
        this(0.0, null, rectangle, text);
    }

    public OCRData(double timeout, ScreenElement element, Rectangle rectangle) {
        this(timeout, element, rectangle, null);
    }
}
