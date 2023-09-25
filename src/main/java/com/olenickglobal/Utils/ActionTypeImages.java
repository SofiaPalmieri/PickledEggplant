package com.olenickglobal.Utils;

import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.net.URISyntaxException;

public enum ActionTypeImages {
    CLICK((ScreenSection image, int timeout) -> {
        image.click(timeout);}),
    RIGHT_CLICK((ScreenSection image, int timeout) -> {
        image.rightClick(timeout);
    }),

    DOUBLE_CLICK((ScreenSection image, int timeout) -> {
        image.doubleClick(timeout);
    }),
    MOVE_TO((ScreenSection image, int timeout) -> {
        image.move(timeout);
    }),
    TAKE_SCREENSHOT((ScreenSection image, int timeout) -> {
        image.takeScreenshot(timeout);
    })
    ;



    ActionTypeImages(ClickActionImage action) {
        this.action = action;
    }

    public final ClickActionImage action;

    public void executeAction(ScreenSection image, int timeout) throws FindFailedHandler, IOException, FindFailed, URISyntaxException {
        this.action.executeAction(image, timeout);
        //ActionTypeImages.TAKE_SCREENSHOT.executeAction(image, timeout);
    }

}