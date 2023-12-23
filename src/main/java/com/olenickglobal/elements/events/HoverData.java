package com.olenickglobal.elements.events;

import com.olenickglobal.elements.Offset;
import com.olenickglobal.elements.ScreenElement;

import java.awt.*;

/**
 * Hover data.
 *
 * @param timeout          Timeout in seconds.
 * @param modifiers        Modifier bits.
 * @param element          Element to be/that was hovered--if any.
 * @param elementRectangle Rectangle where the element was matched--if any.
 * @param offset           Target offset--if any.
 * @param target           Target point.
 * @param linger           Linger time in seconds.
 */
public record HoverData(double timeout, int modifiers, ScreenElement element, Rectangle elementRectangle, Offset offset,
                        Point target, double linger) implements EventData<HoverData.HoverDataBuilder> {
    public static class HoverDataBuilder implements EventDataBuilder<HoverData> {
        protected double timeout;
        protected int modifiers;
        protected ScreenElement element;
        protected Rectangle elementRectangle;
        protected Offset offset;
        protected Point target;
        protected double linger;

        public HoverDataBuilder(HoverData data) {
            this.timeout = data.timeout;
            this.modifiers = data.modifiers;
            this.element = data.element;
            this.elementRectangle = data.elementRectangle;
            this.offset = data.offset;
            this.target = data.target;
            this.linger = data.linger;
        }

        public HoverData build() {
            return new HoverData(timeout, modifiers, element, elementRectangle, offset, target, linger);
        }

        public HoverDataBuilder withTimeout(double timeout) {
            this.timeout = timeout;
            return this;
        }

        public HoverDataBuilder withModifiers(int modifiers) {
            this.modifiers = modifiers;
            return this;
        }

        public HoverDataBuilder withElement(ScreenElement element) {
            this.element = element;
            return this;
        }

        public HoverDataBuilder withElementRectangle(Rectangle elementRectangle) {
            this.elementRectangle = elementRectangle;
            return this;
        }

        public HoverDataBuilder withOffset(Offset offset) {
            this.offset = offset;
            return this;
        }

        public HoverDataBuilder withTarget(Point target) {
            this.target = target;
            return this;
        }

        public HoverDataBuilder withLinger(double linger) {
            this.linger = linger;
            return this;
        }
    }

    public HoverData(int modifiers, Point target, double linger) {
        this(0.0, modifiers, null, null, null, target, linger);
    }

    public HoverDataBuilder builder() {
        return new HoverDataBuilder(this);
    }
}
