package com.olenickglobal.elements.events;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Screenshot saving data.
 * @param boundaries
 * @param image
 * @param path
 */
public record SaveScreenshotData(Rectangle boundaries, BufferedImage image, String path) {
    public SaveScreenshotData(Rectangle boundaries, BufferedImage image) {
        this(boundaries, image, null);
    }

    public SaveScreenshotData(Rectangle boundaries, String path) {
        this(boundaries, null, path);
    }
}
