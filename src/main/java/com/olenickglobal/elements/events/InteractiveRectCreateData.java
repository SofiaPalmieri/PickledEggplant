package com.olenickglobal.elements.events;

import com.olenickglobal.elements.ScreenElement;
import com.olenickglobal.entities.Screen;

import java.awt.*;

/**
 * Interactive rectangle element creation data.
 *
 * @param screen    Screen where it will be/has taken place.
 * @param rectangle Rectangle that was selected--or null if not yet/cancelled.
 * @param element   Element that was created--or null if not yet/cancelled.
 */
// TODO: Include boundary frame?
public record InteractiveRectCreateData(Screen screen, Rectangle rectangle, ScreenElement element)
        implements EventData<InteractiveRectCreateData.InteractiveRectCreateDataBuilder> {
    public static class InteractiveRectCreateDataBuilder implements EventDataBuilder<InteractiveRectCreateData> {
        protected Screen screen;
        protected Rectangle rectangle;
        protected ScreenElement element;

        public InteractiveRectCreateDataBuilder(InteractiveRectCreateData data) {
            this.screen = data.screen;
            this.rectangle = data.rectangle;
            this.element = data.element;
        }

        public InteractiveRectCreateData build() {
            return new InteractiveRectCreateData(screen, rectangle, element);
        }

        public InteractiveRectCreateDataBuilder withRectangle(Rectangle rectangle) {
            this.rectangle = rectangle;
            return this;
        }

        public InteractiveRectCreateDataBuilder withElement(ScreenElement element) {
            this.element = element;
            return this;
        }

        public InteractiveRectCreateDataBuilder withScreen(Screen screen) {
            this.screen = screen;
            return this;
        }
    }

    public InteractiveRectCreateData(Screen screen) {
        this(screen, null, null);
    }

    public InteractiveRectCreateDataBuilder builder() {
        return new InteractiveRectCreateDataBuilder(this);
    }
}
