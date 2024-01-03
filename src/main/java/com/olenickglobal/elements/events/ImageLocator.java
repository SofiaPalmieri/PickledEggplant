package com.olenickglobal.elements.events;

import java.awt.image.BufferedImage;

/**
 * Locator for an image.
 *
 * @param image         Binary image contents--Having this implies that the path field will be null.
 * @param path          Path to image--Having this implies that the image field will be null.
 * @param minSimilarity Minimum similarity to match.
 * @param bestMatch     Best matched image.
 */
public record ImageLocator(BufferedImage image, String path, double minSimilarity, BufferedImage bestMatch) {
    public ImageLocator(BufferedImage image, double minSimilarity) {
        this(image, null, minSimilarity, null);
    }

    public ImageLocator(String path, double minSimilarity) {
        this(null, path, minSimilarity, null);
    }

    public ImageLocator(BufferedImage image, double minSimilarity, BufferedImage bestMatch) {
        this(image, null, minSimilarity, bestMatch);
    }

    public ImageLocator(String path, double minSimilarity, BufferedImage bestMatch) {
        this(null, path, minSimilarity, bestMatch);
    }
}
