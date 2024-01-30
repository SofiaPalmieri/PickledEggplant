package com.olenickglobal.elements;

import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.elements.events.EventType;
import com.olenickglobal.elements.events.ImageLocator;
import com.olenickglobal.elements.events.LocatingData;
import com.olenickglobal.entities.SUT;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.ImageNotFoundException;
import com.olenickglobal.formatting.ElementFormatter;

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
    @SuppressWarnings("unchecked")
    protected Rectangle getMatch(final double timeout) throws ElementNotFoundException {
        return performAction(EventType.BEFORE_LOCATING, EventType.AFTER_LOCATING, EventType.LOCATING_ERROR,
                new Class[] { ElementNotFoundException.class },
                new LocatingData<>(timeout, new ImageLocator(imageName, minSimilarity), null, this),
                (LocatingData.LocatingDataBuilder<ImageLocator> builder) -> {
                    Rectangle parentArea, rectangle;
                    builder.withSearchFrame(parentArea = getParentBoundingRectangle(timeout));
                    try {
                        builder.withRectangle(rectangle = screen.findImage(timeout, parentArea, imageName, minSimilarity));
                    } catch (ImageNotFoundException e) {
                        throw new ElementNotFoundException(e);
                    }
                    setLastMatchLocation(rectangle);
                    return rectangle;
                });
    }

    @Override
    public String formatBy(ElementFormatter elementFormatter) {
        return elementFormatter.formatImageElement(this);
    }

    public String getName() {
        return this.imageName;
    }
}
