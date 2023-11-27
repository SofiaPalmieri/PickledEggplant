package com.olenickglobal.elements;

import com.olenickglobal.configuration.ConfigReader;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

public class ImageElement implements ScreenElement {
    private final String imageName;
    private final Screen screen;

    public ImageElement(String imageName) {
        this.imageName = this.getFullImagePath(imageName);
        this.screen = new Screen();
    }

    @Override
    public void click(double timeout) throws FindFailed {
        this.screen.wait(imageName, timeout).getTarget().click();
    }

    @Override
    public void doubleClick(double timeout) throws FindFailed {
        this.screen.wait(imageName, timeout).getTarget().doubleClick();
    }

    @Override
    public void dragTo(ScreenElement src, double timeout) throws FindFailed {
        Location targetLocation = src.getLocation(timeout);
        Location destinationLocation = this.getLocation(timeout);
        this.screen.dragDrop(targetLocation, destinationLocation);
    }

    public String getFullImagePath(String imageName) {
        return ConfigReader.getInstance().getImageName(imageName);
    }

    @Override
    public Location getLocation(double timeout) throws FindFailed {
        return this.screen.wait(imageName, timeout).getTarget();
    }

    @Override
    public Boolean isFound(double timeout) {
        try {
            this.screen.wait(imageName, timeout);
            return true;
        } catch (FindFailed findFailed) {
            return false;
        }
    }

    @Override
    public void moveTo(double timeout) throws FindFailed {
        this.screen.wait(imageName, timeout).getTarget().hover();
    }

    @Override
    public void rightClick(double timeout) throws FindFailed {
        this.screen.wait(imageName, timeout).getTarget().rightClick();
    }

    @Override
    public void waitFor(double timeout) throws FindFailed {
        this.screen.wait(imageName, timeout * 1000);
    }
}
