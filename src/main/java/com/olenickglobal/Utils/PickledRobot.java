package com.olenickglobal.Utils;

import org.sikuli.script.Screen;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.imageio.ImageIO;


public class PickledRobot {

    private Robot robot = new Robot();

    Screen screen;
    PatternHandler patternHandler;

    String text;

    ImageStep step;

    String fileName;

    String screenshot;

    public PickledRobot(ImageStep step) throws FindFailedHandler, AWTException {
        try {
            this.robot = new Robot();
            this.step = step;
        } catch (AWTException awtException) {
            throw new FindFailedHandler(screen, patternHandler);
        }
    }

    public PickledRobot() throws FindFailedHandler, AWTException {
        try {
            this.robot = new Robot();
        } catch (AWTException awtException) {
            throw new FindFailedHandler(screen, patternHandler);
        }
    }

    public PickledRobot(String textToFind) throws AWTException {
        this.text = textToFind;
    }

    public BufferedImage getCurrentScreen() throws IOException {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage originalCapture = this.robot.createScreenCapture(screenRect);
        BufferedImage capture = new BufferedImage(originalCapture.getWidth(), originalCapture.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        capture.getGraphics().drawImage(originalCapture, 0, 0, null);

        return originalCapture;
    }

    public String saveScreenshot() throws IOException {
        BufferedImage originalCapture = this.getCurrentScreen();
        String localDateTime = LocalDateTime.now().toString();
        System.out.println(localDateTime);
        fileName = step.imageName + localDateTime + ".jpg";
        File file = new File("C:/Users/sofia/OneDrive - UTN.BA/PC/OneDrive - UTN.BA/Qualitest/PickledEggplant/src/main/resources/" + fileName);
        ImageIO.write(originalCapture, "jpg", file);
        return file.getAbsolutePath();
    }

    public BufferedImage getCroppedScreen(Rectangle rectangle) throws IOException {
        return this.getCurrentScreen().getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public Robot getRobot() {
        return this.robot;
    }

    public void moveTo(Rectangle rect) {
        this.robot.mouseMove(rect.x + rect.width / 2, rect.y + rect.height / 2);
    }

    public void click() {
        this.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        this.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void rightClick() {
        this.robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        this.robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    public void typeText(String text) throws FindFailedHandler {
        for (char c : text.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                throw new FindFailedHandler(screen, patternHandler);
            }
                this.robot.keyPress(keyCode);
                robot.delay(100);
                robot.keyRelease(keyCode);
                robot.delay(100);
            }
        }


    }

