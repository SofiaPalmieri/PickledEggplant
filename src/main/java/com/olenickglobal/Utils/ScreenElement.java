package com.olenickglobal.Utils;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;

public interface ScreenElement {

    void click(Integer timeout) throws FindFailed;

    void doubleClick(Integer timeout) throws FindFailed;

    void rightClick(Integer timeout) throws FindFailed;

    void moveTo(Integer timeout) throws FindFailed;

    Boolean isFound(Integer timeout);

    void waitFor(Integer timeout) throws FindFailed;

    void dragTo(ScreenElement scr, Integer timeout) throws FindFailed;

    Location getLocation(Integer timeout) throws FindFailed;

}
