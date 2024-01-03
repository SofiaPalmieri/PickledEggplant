package com.olenickglobal.elements.events;

import com.olenickglobal.elements.Offset;
import com.olenickglobal.elements.ScreenElement;

import java.awt.*;

/**
 * Click data.
 *
 * @param action           Click action type.
 * @param timeout          Timeout in seconds.
 * @param modifiers        Modifiers (bits).
 * @param element          Element to be/that was clicked--if any.
 * @param elementRectangle Rectangle where the element was matched--if any.
 * @param offset           Element offset--if any.
 * @param target           Target point.
 * @param delay            Delay in seconds before click action.
 */
public record ClickData(ClickActionType action, double timeout, int modifiers, ScreenElement element,
                        Rectangle elementRectangle, Offset offset, Point target, double delay)
        implements EventData<ClickData.ClickDataBuilder> {
    public enum ClickActionType {
        DOUBLE_CLICK,
        LEFT_CLICK,
        RIGHT_CLICK,
    }

    public static class ClickDataBuilder implements EventDataBuilder<ClickData> {
        protected ClickActionType action;
        protected double timeout;
        protected int modifiers;
        protected ScreenElement element;
        protected Rectangle elementRectangle;
        protected Offset offset;
        protected Point target;
        protected double delay;

        public ClickDataBuilder(ClickData data) {
            this.action = data.action;
            this.timeout = data.timeout;
            this.modifiers = data.modifiers;
            this.element = data.element;
            this.elementRectangle = data.elementRectangle;
            this.offset = data.offset;
            this.target = data.target;
            this.delay = data.delay;
        }

        public ClickData build() {
            return new ClickData(action, timeout, modifiers, element, elementRectangle, offset, target, delay);
        }

        public ClickDataBuilder withAction(ClickActionType action) {
            this.action = action;
            return this;
        }

        public ClickDataBuilder withTimeout(double timeout) {
            this.timeout = timeout;
            return this;
        }

        public ClickDataBuilder withModifiers(int modifiers) {
            this.modifiers = modifiers;
            return this;
        }

        public ClickDataBuilder withElement(ScreenElement element) {
            this.element = element;
            return this;
        }

        public ClickDataBuilder withElementRectangle(Rectangle elementRectangle) {
            this.elementRectangle = elementRectangle;
            return this;
        }

        public ClickDataBuilder withOffset(Offset offset) {
            this.offset = offset;
            return this;
        }

        public ClickDataBuilder withTarget(Point target) {
            this.target = target;
            return this;
        }

        public ClickDataBuilder withDelay(double delay) {
            this.delay = delay;
            return this;
        }
    }

    public ClickData(ClickActionType action, int modifiers, Point target, double delay) {
        this(action, 0.0, modifiers, null, null, null, target, delay);
    }

    public ClickDataBuilder builder() {
        return new ClickDataBuilder(this);
    }
}
