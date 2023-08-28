package com.olenickglobal.Utils;

import org.sikuli.script.FindFailed;

import java.io.IOException;

public interface ClickActionImage {
    void executeAction(ScreenSection image, int timeout) throws FindFailedHandler, IOException, FindFailed;

}
