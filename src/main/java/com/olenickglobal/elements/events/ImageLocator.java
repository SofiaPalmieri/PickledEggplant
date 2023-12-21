package com.olenickglobal.elements.events;

import java.awt.image.BufferedImage;

/**
 * Locator for an image.
 * @param image Binary image contents--Having this implies that the path field will be null.
 * @param path Path to image--Having this implies that the image field will be null.
 * @param minSimilarity Minimum similarity to match.
 */
public record ImageLocator(BufferedImage image, String path, double minSimilarity) {
    public ImageLocator(BufferedImage image, double minSimilarity) {
        this(image, null, minSimilarity);
    }

    public ImageLocator(String path, double minSimilarity) {
        this(null, path, minSimilarity);
    }
}
