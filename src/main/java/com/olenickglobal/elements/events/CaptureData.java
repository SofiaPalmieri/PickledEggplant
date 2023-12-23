package com.olenickglobal.elements.events;

import com.olenickglobal.elements.ScreenElement;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Captured data.
 *
 * @param timeout   Timeout in seconds.
 * @param element   Element to be/that was captured--if any.
 * @param rectangle Rectangle that was captured--either matched element or simple screen rectangle.
 * @param image     Captured image.
 */
public record CaptureData(double timeout, ScreenElement element, Rectangle rectangle, BufferedImage image)
        implements EventData<CaptureData.CaptureDataBuilder> {
    public static class CaptureDataBuilder implements EventDataBuilder<CaptureData> {
        protected double timeout;
        protected ScreenElement element;
        protected Rectangle rectangle;
        protected BufferedImage image;

        public CaptureDataBuilder(CaptureData data) {
            this.timeout = data.timeout;
            this.element = data.element;
            this.rectangle = data.rectangle;
            this.image = data.image;
        }

        public CaptureData build() {
            return new CaptureData(timeout, element, rectangle, image);
        }

        public CaptureDataBuilder withTimeout(double timeout) {
            this.timeout = timeout;
            return this;
        }

        public CaptureDataBuilder withElement(ScreenElement element) {
            this.element = element;
            return this;
        }

        public CaptureDataBuilder withRectangle(Rectangle rectangle) {
            this.rectangle = rectangle;
            return this;
        }

        public CaptureDataBuilder withImage(BufferedImage image) {
            this.image = image;
            return this;
        }
    }

    public CaptureData(double timeout, ScreenElement element) {
        this(timeout, element, null);
    }

    public CaptureData(double timeout, ScreenElement element, Rectangle rectangle) {
        this(timeout, element, rectangle, null);
    }

    public CaptureData(Rectangle rectangle) {
        this(rectangle, null);
    }

    public CaptureData(Rectangle rectangle, BufferedImage image) {
        this(0.0, null, rectangle, image);
    }

    public CaptureDataBuilder builder() {
        return new CaptureDataBuilder(this);
    }
}
