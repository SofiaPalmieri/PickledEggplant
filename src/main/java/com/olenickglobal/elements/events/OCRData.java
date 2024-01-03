package com.olenickglobal.elements.events;

import com.olenickglobal.elements.ScreenElement;

import java.awt.*;

/**
 * OCR data.
 *
 * @param timeout   Timeout in seconds.
 * @param element   Element to be/that was OCRed--if any.
 * @param rectangle Rectangle that was OCRed--either matched element or simple screen rectangle.
 * @param text      Captured text.
 */
public record OCRData(double timeout, ScreenElement element, Rectangle rectangle, String text)
        implements EventData<OCRData.OCRDataBuilder> {
    public static class OCRDataBuilder implements EventDataBuilder<OCRData> {
        protected double timeout;
        protected ScreenElement element;
        protected Rectangle rectangle;
        protected String text;

        public OCRDataBuilder(OCRData data) {
            this.timeout = data.timeout;
            this.element = data.element;
            this.rectangle = data.rectangle;
            this.text = data.text;
        }

        public OCRData build() {
            return new OCRData(timeout, element, rectangle, text);
        }

        public OCRDataBuilder withTimeout(double timeout) {
            this.timeout = timeout;
            return this;
        }

        public OCRDataBuilder withElement(ScreenElement element) {
            this.element = element;
            return this;
        }

        public OCRDataBuilder withRectangle(Rectangle rectangle) {
            this.rectangle = rectangle;
            return this;
        }

        public OCRDataBuilder withText(String text) {
            this.text = text;
            return this;
        }
    }

    public OCRData(Rectangle rectangle) {
        this(0.0, null, rectangle, null);
    }

    public OCRData(Rectangle rectangle, String text) {
        this(0.0, null, rectangle, text);
    }

    public OCRData(double timeout, ScreenElement element, Rectangle rectangle) {
        this(timeout, element, rectangle, null);
    }

    public OCRDataBuilder builder() {
        return new OCRDataBuilder(this);
    }
}
