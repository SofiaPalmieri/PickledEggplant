package com.olenickglobal.Utils;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import java.io.IOException;

public class ScreenSection {

    private final String imageName;

    Screen screen = new Screen();
    PatternHandler patternHandler = new PatternHandler();

    public ScreenSection(String imageName){
        this.imageName = imageName;
    }

    public void click(double timeout) throws FindFailed {
        screen.wait(patternHandler.handlePattern(this.imageName), timeout).getTarget().click();
    }

    public void rightClick(double timeout) throws FindFailed {
        screen.wait(patternHandler.handlePattern(this.imageName), timeout).getTarget().rightClick();
    }

    public void doubleClick(double timeout) throws FindFailed {
        screen.wait(patternHandler.handlePattern(this.imageName), timeout).getTarget().doubleClick();
    }

    public void takeScreenshot(double timeout) throws FindFailed {
        screen.capture();
    }
    public void move(double timeout) throws FindFailed {
        screen.wait(patternHandler.handlePattern(this.imageName), timeout);
        screen.mouseMove(patternHandler.handlePattern(this.imageName));
    }

    public void executeAction(int timeout, ActionTypeImages type) throws FindFailedHandler, IOException, FindFailed {
        type.executeAction(this, timeout);
    }

}