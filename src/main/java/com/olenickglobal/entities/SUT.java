package com.olenickglobal.entities;

import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.elements.ScreenElement;
import com.olenickglobal.exceptions.SUTRobotException;
import com.olenickglobal.exceptions.SavingScreenCaptureException;
import com.olenickglobal.utils.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// FIXME: Try to use Screen to access SikuliX instead of it directly.
public class SUT {
    private static volatile SUT instance;

    // TODO: See if we should move this somewhere safer.
    static {
        System.setProperty("sun.java2d.uiScale.enabled", "false");
    }

    // TODO: See if it would be better to move the use of RPA into Screen.
    private final Robot robot;
    // TODO: Multiple screens?
    protected Screen screen;
    protected volatile ScreenElement mainParentElement;

    public static SUT getInstance() {
        if (instance == null) {
            synchronized (SUT.class) {
                if (instance == null) {
                    instance = new SUT();
                }
            }
        }
        return instance;
    }

    protected SUT() throws SUTRobotException {
        try {
            robot = new Robot();
            screen = new Screen();
        } catch (AWTException e) {
            throw new SUTRobotException(e);
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

    public File saveFullScreenshot(String filename) {
        return saveScreenshot(screen.getBounds(), filename);
    }

    public File saveScreenshot(String filename) {
        return saveScreenshot(mainParentElement == null ? screen.getBounds() : mainParentElement.getLastMatchLocation(), filename);
    }

    public File saveScreenshot(Rectangle area, String filename) throws SavingScreenCaptureException {
        String fullPath = ConfigReader.getInstance().getScreenshotName(filename);
        File file;
        try {
            BufferedImage image = screen.capture(area);
            file = FileUtils.createFile(fullPath);
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            throw new SavingScreenCaptureException("There was a failure saving the following image: " + filename, e);
        }
        return file;
    }

    public void typeText(String text) {
        // TODO: Should we move this to the Screen class?
        for (char c : text.toCharArray()) {
            robot.keyPress(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c));
            robot.keyRelease(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c));
        }
    }
}
