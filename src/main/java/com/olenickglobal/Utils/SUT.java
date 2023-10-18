package com.olenickglobal.Utils;




import java.awt.*;
import java.awt.image.BufferedImage;



public class SUT {

    private Robot robot;
    private final ExceptionManager manager = new ExceptionManager();

    public SUT(){
        manager.withException(() -> {
            this.robot = new Robot();
        }, "Robot initialization failed in SUT constructor");
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


    public BufferedImage getCurrentScreen() {
        return this.robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }
}

