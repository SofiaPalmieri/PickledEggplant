package com.olenickglobal.Utils;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class ImageElement implements ScreenElement {

    private final String imageName;
    private final Screen screen;


    public ImageElement(String imageName){
        this.imageName = this.getFullImagePath(imageName);
        this.screen = new Screen();
    }

    public String getFullImagePath(String imageName){
        try {
            return new File(Objects.requireNonNull(getClass().getClassLoader().getResource("images/" + imageName)).toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void click(Integer timeout) throws FindFailed {
        this.screen.wait(imageName, timeout).getTarget().click();
    }

    @Override
    public void doubleClick(Integer timeout) throws FindFailed {
        this.screen.wait(imageName,timeout).getTarget().doubleClick();
    }

    @Override
    public void rightClick(Integer timeout) throws FindFailed {
        this.screen.wait(imageName,timeout).getTarget().rightClick();
    }

    @Override
    public void moveTo(Integer timeout) throws FindFailed {
this.screen.wait(imageName,timeout).getTarget().hover();
    }

    @Override
    public Boolean isFound(Integer timeout) {
        try {
            this.screen.wait(imageName,timeout);
            return true;
        } catch (FindFailed findFailed) {
            return false;
        }
    }

    @Override
    public void waitFor(Integer timeout) throws FindFailed {
        this.screen.wait(imageName,timeout);
    }

    @Override
    public Location getLocation(Integer timeout) throws FindFailed {
        return this.screen.wait(imageName,timeout).getTarget();
    }

    @Override
    public void dragTo(ScreenElement src,Integer timeout) throws FindFailed {
        Location targetLocation = src.getLocation(timeout);
        Location destinationLocation = this.getLocation(timeout);
        this.screen.dragDrop(targetLocation,destinationLocation);
    }



}





