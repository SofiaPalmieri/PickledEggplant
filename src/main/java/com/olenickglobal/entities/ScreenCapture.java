package com.olenickglobal.entities;

import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.exceptions.SavingScreenCapture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public record ScreenCapture(BufferedImage getImage) {
    public ScreenCapture cropTo(Rectangle rectangle) {
        return new ScreenCapture(this.getImage.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height));
    }

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
}
