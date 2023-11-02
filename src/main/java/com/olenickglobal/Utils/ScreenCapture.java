package com.olenickglobal.Utils;

import com.olenickglobal.Exceptions.SavingScreenCapture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public record ScreenCapture(BufferedImage getImage) {

    public File saveFileAsScreenshot(String name) {
        String fullPath = ConfigReader.getInstance().getScreenshotName(name);
        File file = new File(fullPath);
        try {
            file.createNewFile();
            ImageIO.write(this.getImage, "png", file);
        } catch (Throwable e) {
            throw new SavingScreenCapture(name);
        }
        return file;
    }

    public ScreenCapture cropTo(Rectangle rectangle) {
        return new ScreenCapture(this.getImage.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height));
    }
}
