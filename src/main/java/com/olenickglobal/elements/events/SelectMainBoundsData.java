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
public record SelectMainBoundsData(Screen screen, Rectangle rectangle, ScreenElement element)
        implements EventData<SelectMainBoundsData.SelectMainBoundsDataBuilder> {
    public static class SelectMainBoundsDataBuilder implements EventDataBuilder<SelectMainBoundsData> {
        protected Screen screen;
        protected Rectangle rectangle;
        protected ScreenElement element;

        public SelectMainBoundsDataBuilder(SelectMainBoundsData data) {
            this.screen = data.screen;
            this.rectangle = data.rectangle;
            this.element = data.element;
        }

        public SelectMainBoundsData build() {
            return new SelectMainBoundsData(screen, rectangle, element);
        }

        public SelectMainBoundsDataBuilder withScreen(Screen screen) {
            this.screen = screen;
            return this;
        }

        public SelectMainBoundsDataBuilder withRectangle(Rectangle rectangle) {
            this.rectangle = rectangle;
            return this;
        }

        public SelectMainBoundsDataBuilder withElement(ScreenElement element) {
            this.element = element;
            return this;
        }
    }

    public SelectMainBoundsData(Screen screen) {
        this(screen, null, null);
    }

    public SelectMainBoundsDataBuilder builder() {
        return new SelectMainBoundsDataBuilder(this);
    }
}
