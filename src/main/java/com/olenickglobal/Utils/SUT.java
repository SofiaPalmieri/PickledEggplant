package com.olenickglobal.Utils;



import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class SUT {

    private Robot robot;

    public SUT(){
        try {
            this.robot = new Robot();
        } catch (AWTException awtException) {
            throw new RuntimeException();
        }
    }

    public BufferedImage getCroppedScreen (Rectangle rectangle){
        BufferedImage screen = this.getCurrentScreen();
        return screen.getSubimage(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
    }

    public void typeText(String text){
        for (char c : text.toCharArray()){
            this.robot.keyPress(c);
            this.robot.keyRelease(c);
        }
    }

    public void captureScreenshot()  {
        try {
            BufferedImage image = this.robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "png", new File("src/test/java/resources/screenshots/"+ System.currentTimeMillis()+".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

       }


    public BufferedImage getCurrentScreen() {
        return this.robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }
}

