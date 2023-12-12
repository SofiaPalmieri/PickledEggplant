package com.olenickglobal.entities;

import com.olenickglobal.elements.ScreenElement;
import com.olenickglobal.exceptions.SUTRobot;

import java.awt.*;

// FIXME: Try to use Screen to access SikuliX instead of it directly.
public class SUT {
    // TODO: See if it would be better to move the use of RPA into Screen.
    private final Robot robot;
    // TODO: Multiple screens?
    protected Screen screen;
    protected volatile ScreenElement mainParentElement;

    public SUT() {
        try {
            robot = new Robot();
            screen = new Screen();
        } catch (AWTException e) {
            throw new SUTRobot(e);
        }
    }

    public Screen getScreen() {
        return screen;
    }

    // TODO: Call this differently?
    public ScreenElement getMainParentElement() {
        if (mainParentElement == null) {
            synchronized (this) {
                if (mainParentElement == null) {
                    mainParentElement = screen.interactivelyCreateElement();
                }
            }
        }
        return mainParentElement;
    }

    // TODO: Remove this method. Use Screen instead.
    public ScreenCapture getCurrentScreen() {
        return new ScreenCapture(robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
    }

    public void typeText(String text) {
        // TODO: Should we move this to the Screen class?
        for (char c : text.toCharArray()) {
            robot.keyPress(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c));
            robot.keyRelease(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c));
        }
    }
}
