package com.olenickglobal.elements;

import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.entities.Screen;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.ImageNotFoundException;

import java.awt.*;

public class ImageElement extends ScreenElement {
    protected final String imageName;

    /**
     * Image element with the default target at the geometrical center of the element.
     * @param imageName Image name, either filename or directory name.
     */
    public ImageElement(String imageName) {
        this(imageName, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    public ImageElement(String imageName, Offset offset) {
        // TODO: Different screens? Inner rectangles? Parent element?
        super(new Screen(), offset);
        // TODO: Check if we need to do this in a different way.
        this.imageName = ConfigReader.getInstance().getImageName(imageName);
    }

    @Override
    protected Rectangle getMatch(double timeout) throws ElementNotFoundException {
        try {
            return screen.findImage(timeout, imageName);
        } catch (ImageNotFoundException e) {
            throw new ElementNotFoundException(e);
        }
    }
}
