package com.olenickglobal.listeners;

import com.olenickglobal.TestResults;
import com.olenickglobal.entities.SUT;
import com.olenickglobal.testinfo.StepRunInfo;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepFinished;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotManager {
    private static final DateTimeFormatter FILENAME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");

    private final SUT sut;

    public ScreenshotManager(SUT sut) {
        this.sut = sut;
    }

    public void captureScreenshotForStep(TestStepFinished event, TestResults testResults) {
        StepRunInfo lastStepInfo = testResults.getInfoFor(event.getTestCase()).getLastStepInfo();
        lastStepInfo.screenshot = getScreenshot(event);
    }

    public File getScreenshot(TestStepFinished event) {
        // TODO: Change this so that it uses the Screen instance in the SUT.
        String localDateTime = LocalDateTime.now().format(FILENAME_FORMATTER);
        String fileName = ((PickleStepTestStep) event.getTestStep()).getStep().getText().replaceAll("[\\s,.:;]", "_").replaceAll("[^0-9A-Za-z_]", "") + localDateTime + ".jpg";
        return sut.saveScreenshot(fileName);
    }
}
