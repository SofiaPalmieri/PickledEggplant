package com.olenickglobal.Utils;

import org.sikuli.script.FindFailed;

import java.io.IOException;

public interface ClickActionText {
    void executeAction(PickledRobot robot, String text) throws FindFailedHandler, IOException, FindFailed;


}
