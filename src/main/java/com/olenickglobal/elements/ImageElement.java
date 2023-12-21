package com.olenickglobal.elements;

import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.entities.SUT;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.ImageNotFoundException;

import java.awt.*;

public class ImageElement extends ScreenElement {
    public static final double DEFAULT_MIN_IMAGE_SIMILARITY = 0.95;

    protected final String imageName;
    protected final double minSimilarity;

    /**
     * Image element with the default target at the geometrical center of the element.
     * @param imageName Image name, either filename or directory name.
     */
    public ImageElement(String imageName) {
        this(imageName, DEFAULT_MIN_IMAGE_SIMILARITY, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    /**
     * Image element with the default target at the geometrical center of the element.
     * @param imageName Image name, either filename or directory name.
     */
    public ImageElement(ScreenElement parent, String imageName) {
        this(parent, imageName, DEFAULT_MIN_IMAGE_SIMILARITY, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    /**
     * Image element with the default target at the geometrical center of the element.
     * @param imageName Image name, either filename or directory name.
     */
    public ImageElement(String imageName, double minSimilarity) {
        this(imageName, minSimilarity, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    /**
     * Image element with the default target at the geometrical center of the element.
     * @param imageName Image name, either filename or directory name.
     */
    public ImageElement(ScreenElement parent, String imageName, double minSimilarity) {
        this(parent, imageName, minSimilarity, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    public ImageElement(String imageName, Offset offset) {
        this(imageName, DEFAULT_MIN_IMAGE_SIMILARITY, offset);
    }

    public ImageElement(ScreenElement parent, String imageName, Offset offset) {
        this(parent, imageName, DEFAULT_MIN_IMAGE_SIMILARITY, offset);
    }

    public ImageElement(String imageName, double minSimilarity, Offset offset) {
        // TODO: Different screens?
        super(SUT.getInstance().getScreen(), offset);
        // TODO: Check if we need to do this in a different way.
        this.imageName = ConfigReader.getInstance().getImageName(imageName);
        this.minSimilarity = minSimilarity;
    }

    public ImageElement(ScreenElement parent, String imageName, double minSimilarity, Offset offset) {
        // TODO: Different screens?
        super(parent.getScreen(), parent, offset);
        // TODO: Check if we need to do this in a different way.
        this.imageName = ConfigReader.getInstance().getImageName(imageName);
        this.minSimilarity = minSimilarity;
    }

    @Override
    protected Rectangle getMatch(double timeout) throws ElementNotFoundException {
        try {
            Rectangle area = getParentBoundingRectangle(timeout);
            return screen.findImage(timeout, area, imageName, minSimilarity);
        } catch (ImageNotFoundException e) {
            throw new ElementNotFoundException(e);
        }
    }
}
