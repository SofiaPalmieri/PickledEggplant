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

    public void moveTo(int x,int y){
        this.robot.mouseMove(x,y);
    }

    public ScreenCapture getCroppedScreen(Rectangle rectangle) {
        ScreenCapture capture = this.getCurrentScreen();
        return capture.cropTo(rectangle);
    }

    public void typeText(String text) {
        for (char c : text.toCharArray()) {
            this.robot.keyPress(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c));
            this.robot.keyRelease(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c));
        }
    }

    public ScreenCapture getCurrentScreen() {
        return new ScreenCapture(this.robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
    }

}

