package com.olenickglobal.elements.events;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Screenshot saving data.
 *
 * @param boundaries Boundaries of the screenshot to save.
 * @param image      Resulting buffered image.
 * @param path       Path of the saved screenshot.
 */
public record SaveScreenshotData(Rectangle boundaries, BufferedImage image, String path)
        implements EventData<SaveScreenshotData.SaveScreenshotDataBuilder> {
    public static class SaveScreenshotDataBuilder implements EventDataBuilder<SaveScreenshotData> {
        protected Rectangle boundaries;
        protected BufferedImage image;
        protected String path;

        public SaveScreenshotDataBuilder(SaveScreenshotData data) {
            this.boundaries = data.boundaries;
            this.image = data.image;
            this.path = data.path;
        }

        public SaveScreenshotData build() {
            return new SaveScreenshotData(boundaries, image, path);
        }

        public SaveScreenshotDataBuilder withBoundaries(Rectangle boundaries) {
            this.boundaries = boundaries;
            return this;
        }

        public SaveScreenshotDataBuilder withImage(BufferedImage image) {
            this.image = image;
            return this;
        }

        public SaveScreenshotDataBuilder withPath(String path) {
            this.path = path;
            return this;
        }
    }

    public SaveScreenshotData(Rectangle boundaries, BufferedImage image) {
        this(boundaries, image, null);
    }

    public SaveScreenshotData(Rectangle boundaries, String path) {
        this(boundaries, null, path);
    }

    public SaveScreenshotDataBuilder builder() {
        return new SaveScreenshotDataBuilder(this);
    }
}
