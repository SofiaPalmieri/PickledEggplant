package com.olenickglobal.Utils;

import org.sikuli.script.FindFailed;

import java.io.IOException;

public enum ActionTypeText {
    CLICK((PickledRobot robot, String text) -> {
        robot.click();
    }),
    RIGHT_CLICK((PickledRobot robot, String text) -> {
        robot.rightClick();
    }),

    DOUBLE_CLICK((PickledRobot robot, String text) -> {
        robot.click();
        robot.click();
    }),
    TYPE_TEXT((PickledRobot robot, String text) -> {
       robot.typeText(text);
    })
    ;


    public final ClickActionText action;

    ActionTypeText(ClickActionText action) {
        this.action = action;
    }


    public String executeAction(PickledRobot robot, String text) throws FindFailedHandler, IOException, FindFailed {
        this.action.executeAction(robot, text);
        return text;
    }
}
