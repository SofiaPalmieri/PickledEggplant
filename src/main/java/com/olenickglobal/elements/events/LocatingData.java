package com.olenickglobal.elements.events;

import com.olenickglobal.elements.ScreenElement;

import java.awt.*;

/**
 * Locating action data.
 *
 * @param <T>         Type of locator. TODO: See if we'll need a couple of classes, or at least one for image matching that includes minSimilarity.
 * @param timeout     Timeout in seconds.
 * @param locator     Locator, e.g., text/image.
 * @param searchFrame Boundaries where the search will be executed.
 * @param element     Element to be/that was located--if any.
 * @param rectangle   Rectangle that was matched--either matched element or simple screen rectangle.
 */
public record LocatingData<T>(double timeout, T locator, Rectangle searchFrame, ScreenElement element,
                              Rectangle rectangle) implements EventData<LocatingData.LocatingDataBuilder<T>> {
    public static class LocatingDataBuilder<T> implements EventDataBuilder<LocatingData<T>> {
        protected double timeout;
        protected T locator;
        protected Rectangle searchFrame;
        protected ScreenElement element;
        protected Rectangle rectangle;

        public LocatingDataBuilder(LocatingData<T> data) {
            this.timeout = data.timeout;
            this.locator = data.locator;
            this.searchFrame = data.searchFrame;
            this.element = data.element;
            this.rectangle = data.rectangle;
        }

        public LocatingData<T> build() {
            return new LocatingData<>(timeout, locator, searchFrame, element, rectangle);
        }

        public LocatingDataBuilder<T> withTimeout(double timeout) {
            this.timeout = timeout;
            return this;
        }

        public LocatingDataBuilder<T> withLocator(T locator) {
            this.locator = locator;
            return this;
        }

        public LocatingDataBuilder<T> withSearchFrame(Rectangle searchFrame) {
            this.searchFrame = searchFrame;
            return this;
        }

        public LocatingDataBuilder<T> withElement(ScreenElement element) {
            this.element = element;
            return this;
        }

        public LocatingDataBuilder<T> withRectangle(Rectangle rectangle) {
            this.rectangle = rectangle;
            return this;
        }
    }

    public LocatingData(double timeout, T locator, Rectangle searchFrame) {
        this(timeout, locator, searchFrame, null, null);
    }

    public LocatingData(double timeout, T locator, Rectangle searchFrame, ScreenElement element) {
        this(timeout, locator, searchFrame, element, null);
    }

    public LocatingDataBuilder<T> builder() {
        return new LocatingDataBuilder<>(this);
    }

    @SuppressWarnings("unchecked")
    public Class<T> getLocatorType() {
        return (Class<T>) locator.getClass();
    }
}
