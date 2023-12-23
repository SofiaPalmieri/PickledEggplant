package com.olenickglobal.elements.events;

import com.olenickglobal.entities.Screen;

import java.awt.*;

/**
 * Interactive rectangle element creation data.
 *
 * @param screen    Screen where it will be/has taken place.
 * @param rectangle Rectangle that was selected--or null if not yet/cancelled.
 */
public record InteractiveRectSelectData(Screen screen, Rectangle rectangle)
        implements EventData<InteractiveRectSelectData.InteractiveRectSelectDataBuilder> {
    public static class InteractiveRectSelectDataBuilder implements EventDataBuilder<InteractiveRectSelectData> {
        protected Screen screen;
        protected Rectangle rectangle;

        public InteractiveRectSelectDataBuilder(InteractiveRectSelectData data) {
            this.screen = data.screen;
            this.rectangle = data.rectangle;
        }

        public InteractiveRectSelectData build() {
            return new InteractiveRectSelectData(screen, rectangle);
        }

        public InteractiveRectSelectDataBuilder withScreen(Screen screen) {
            this.screen = screen;
            return this;
        }

        public InteractiveRectSelectDataBuilder withRectangle(Rectangle rectangle) {
            this.rectangle = rectangle;
            return this;
        }
    }

    public InteractiveRectSelectData(Screen screen) {
        this(screen, null);
    }

    public InteractiveRectSelectDataBuilder builder() {
        return new InteractiveRectSelectDataBuilder(this);
    }
}
