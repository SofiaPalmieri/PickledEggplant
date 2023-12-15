package com.olenickglobal.entities;

import com.olenickglobal.exceptions.SUTRobot;

import java.awt.*;

public class SUT {
    private final Robot robot;

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

    public ScreenCapture getCurrentScreen() {
        return new ScreenCapture(this.robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
    }

    public void moveTo(int x, int y) {
        this.robot.mouseMove(x, y);
    }

    public void typeText(String text) {
        for (char c : text.toCharArray()) {
            this.robot.keyPress(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c));
            this.robot.keyRelease(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c));
        }
    }
}
