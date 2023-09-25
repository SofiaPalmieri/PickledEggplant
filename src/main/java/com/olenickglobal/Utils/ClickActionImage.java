package com.olenickglobal.Utils;

import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ClickActionImage {
    void executeAction(ScreenSection image, int timeout) throws FindFailedHandler, IOException, FindFailed, URISyntaxException;

}
