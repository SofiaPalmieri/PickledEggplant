package com.olenickglobal.Utils;


import com.olenickglobal.Exceptions.SUTRobot;

import java.awt.*;
import java.awt.image.BufferedImage;


public class SUT {

    private Robot robot;

    public SUT() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new SUTRobot(e);
        }

    }

    public ScreenCapture getCroppedScreen(Rectangle rectangle) {
        ScreenCapture capture = this.getCurrentScreen();
        return capture.cropTo(rectangle);
    }

    public void typeText(String text) {
        for (char c : text.toCharArray()) {
            this.robot.keyPress(c);
            this.robot.keyRelease(c);
        }
    }

    public ScreenCapture getCurrentScreen() {
        return new ScreenCapture(this.robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
    }

}

