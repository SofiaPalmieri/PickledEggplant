package com.olenickglobal.elements;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;

public interface ScreenElement {
    void click(double timeout) throws FindFailed;
    void doubleClick(double timeout) throws FindFailed;
    void rightClick(double timeout) throws FindFailed;
    void moveTo(double timeout) throws FindFailed;
    Boolean isFound(double timeout);
    void waitFor(double timeout) throws FindFailed;
    void dragTo(ScreenElement scr, double timeout) throws FindFailed;
    Location getLocation(double timeout) throws FindFailed;
}
