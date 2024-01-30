package listeners;


import com.olenickglobal.Exceptions.WritingToFileSystem;
import com.olenickglobal.TestResults;
import com.olenickglobal.Utils.ConfigReader;
import com.olenickglobal.Utils.SUT;
import com.olenickglobal.Utils.StepRunInfo;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepFinished;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotCapturer {

    private static final DateTimeFormatter FILENAME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");

    private static final SUT sut = new SUT();

    public void captureScreenshotForStep(TestStepFinished event, TestResults testResults) {
        StepRunInfo lastStepInfo = testResults.getInfoFor(event.getTestCase()).getLastStepInfo();
        lastStepInfo.screenshot = getScreenshot(event);
    }

    public File getScreenshot(TestStepFinished event) {
        String localDateTime = LocalDateTime.now().format(FILENAME_FORMATTER);
        String fileName = ((PickleStepTestStep) event.getTestStep()).getStep().getText().replaceAll("[\\s,.:;]", "_").replaceAll("[^0-9A-Za-z_]", "") + localDateTime + ".jpg";
        return sut.getCurrentScreen().saveFileAsScreenshot(fileName);
    }


}
