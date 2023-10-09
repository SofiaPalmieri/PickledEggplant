package listeners;


import com.olenickglobal.TestResults;
import com.olenickglobal.Utils.ConfigReader;
import com.olenickglobal.Utils.StepRunInfo;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepFinished;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotCapturer {

    private static final DateTimeFormatter FILENAME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");


    public void captureScreenshotForStep(TestStepFinished event, TestResults testResults) {
        StepRunInfo lastStepInfo = testResults.getInfoFor(event.getTestCase()).getLastStepInfo();
        File image = getScreenshot(event);
        lastStepInfo.setScreenshot(image);
    }

    public File getScreenshot(TestStepFinished event) {
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage tempImage = robot.createScreenCapture(rectangle);
        return imageInMainFolder(event, tempImage);
    }

    public File imageInMainFolder(TestStepFinished event, BufferedImage image){
        BufferedImage mainImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        mainImage.getGraphics().drawImage(image, 0, 0, null);
        String localDateTime = LocalDateTime.now().format(FILENAME_FORMATTER);
        String fileName;
        fileName = ((PickleStepTestStep) event.getTestStep()).getStep().getText().replaceAll("[\\s,.:;]", "_").replaceAll("[^0-9A-Za-z_]", "") + localDateTime + ".jpg";
        File file = new File(new ConfigReader().getScreenshotName(fileName));
        try {
            ImageIO.write(mainImage, "jpg", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
