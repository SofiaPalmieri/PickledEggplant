package com.olenickglobal;

import com.olenickglobal.Utils.ConfigReader;
import com.olenickglobal.Utils.TestEventResult;
import com.olenickglobal.Utils.TestEventType;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.imageio.ImageIO;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CucumberListener implements ConcurrentEventListener {
    static final DateTimeFormatter FILENAME_FORMATTER = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_SSS");

    static final Map<Status, String> STATUS_TRANSLATION = new EnumMap<Status, String>(Map.of(
            Status.PASSED, "Pass",
            Status.FAILED, "Fail",
            Status.AMBIGUOUS, "Fail",
            Status.PENDING, "Fail",
            Status.UNDEFINED, "Fail",
            Status.UNUSED, "Fail"
    ));


    int rowNumber = 0;

    File file = new File(Configuration.getInstance().getConfig(Configuration.Configs.EXCEL_PATH));

    Map<TestCase, TestEventResult> results = new LinkedHashMap<>();


    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestCaseStarted.class, this::initScenarioData);
        eventPublisher.registerHandlerFor(TestStepStarted.class, this::initStep);
        eventPublisher.registerHandlerFor(TestStepFinished.class, this::takeScreenshotHandler);
        eventPublisher.registerHandlerFor(TestStepFinished.class, this::saveStepResults);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::saveScenarioResults);

    }

    public void takeScreenshotHandler(TestStepFinished event) {
        TestEventResult stepResult = getLastStep(event.getTestCase());

        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }


        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage originalCapture = robot.createScreenCapture(screenRect);
        BufferedImage capture = new BufferedImage(originalCapture.getWidth(), originalCapture.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        capture.getGraphics().drawImage(originalCapture, 0, 0, null);
        String localDateTime = LocalDateTime.now().format(FILENAME_FORMATTER);
        System.out.println(localDateTime);
        String fileName;
        fileName = ((PickleStepTestStep) event.getTestStep()).getStep().getText().replaceAll("[\\s,.:;]", "_").replaceAll("[^0-9A-Za-z_]", "") + localDateTime + ".jpg";
        File file = new File(new ConfigReader().readConfig("IMAGES_PATH") + "\\" + fileName);
        stepResult.image = new ConfigReader().readConfig("IMAGES_PATH")+ "\\"+ fileName;
        try {
            ImageIO.write(originalCapture, "jpg", file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TestEventResult getLastStep(TestCase testCase) {
        List<TestEventResult> stepResults = results.get(testCase).children;
        return stepResults.get(stepResults.size() - 1);
    }


    public void saveStepResults(TestStepFinished event) {
        TestEventResult stepResult = getLastStep(event.getTestCase());
        stepResult.result = STATUS_TRANSLATION.get(event.getResult().getStatus());


        try {
            int cellNumber = 0;


            stepResult.endTime = LocalDateTime.now().format(FILENAME_FORMATTER);
            FileInputStream inputStream = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Row newRow = workbook.getSheetAt(0).createRow(rowNumber);
            rowNumber++;
            newRow.createCell(cellNumber).setCellValue(stepResult.startTime);
            cellNumber++;
            newRow.createCell(cellNumber).setCellValue(stepResult.endTime);
            cellNumber++;
            newRow.createCell(cellNumber).setCellValue(stepResult.applicationName);
            cellNumber++;
            newRow.createCell(cellNumber).setCellValue(stepResult.image);
            cellNumber++;
            newRow.createCell(cellNumber).setCellValue(stepResult.test);
            cellNumber++;
            newRow.createCell(cellNumber).setCellValue(stepResult.result);
            inputStream.close();
            FileOutputStream output = new FileOutputStream(file);
            workbook.write(output);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }


    }

    public void saveScenarioResults(TestCaseFinished event) {
        results.remove(event.getTestCase());

    }

    public void initScenarioData(TestCaseStarted event) {
        TestEventResult scenarioResult = new TestEventResult();
        scenarioResult.startTime = LocalDateTime.now().format(FILENAME_FORMATTER);
        scenarioResult.eventType = TestEventType.TESTCASE;
        scenarioResult.applicationName = event.getTestCase().getTags().stream().filter(tag -> tag.startsWith("@Application")).collect(Collectors.joining(", "));
        scenarioResult.test = event.getTestCase().getName();
        results.put(event.getTestCase(), scenarioResult);
    }

    public void initStep(TestStepStarted event) {
        TestEventResult stepResult = new TestEventResult();
        stepResult.startTime = LocalDateTime.now().format(FILENAME_FORMATTER);
        stepResult.eventType = TestEventType.INFO;
        stepResult.applicationName = event.getTestCase().getTags().stream().filter(tag -> tag.startsWith("@Application")).collect(Collectors.joining(", "));
        stepResult.test = event.getTestCase().getName();
        results.get(event.getTestCase()).children.add(stepResult);
    }

}
