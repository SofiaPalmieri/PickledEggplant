package com.olenickglobal.listeners;

import com.olenickglobal.TestResults;
import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.exceptions.OpeningExcelInReport;
import com.olenickglobal.testinfo.StepRunInfo;
import com.olenickglobal.testinfo.TestRunInfo;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestCaseFinished;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

public class RPVExcelReporter extends Reporter {
    public enum COLUMNS {
        START_TIME(0),
        END_TIME(1),
        TEST_TIME_TYPE(2),
        APPLICATION(3),
        TEST(4),
        RESULT(5),
        VERSION(6),
        EXPECTED(7),
        IMAGE_COMMENT(8),
        IMAGE(9),
        IN_ADO(10),
        SYSTEM_NAME(11),
        WEBSITE(12),
        RUN(13),
        STATE(14),
        SESSION_COLLECTION_ID(15),
        SESSION_ID(16),
        MEASUREMENT_PARAMETERS(17);

        int index;

        COLUMNS(int index) {
            this.index = index;
        }
    }

    private final DateTimeFormatter DATETIME_EXCEL_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
    private final File file = new File(ConfigReader.getInstance().<String>readConfig(ConfigReader.ConfigParam.EXCEL_PATH, ConfigReader.SupportedType.STRING));

    public RPVExcelReporter(JSONObject section) {
        super(section);
        // this.file = new File(section.getString("EXCEL_PATH"));
    }

    private String readResults(Status status) {
        switch (status) {
            case PASSED:
                return "Pass";
            case FAILED, SKIPPED:
                return "Fail";
            default:
                return "Unknown";
        }
    }

    public void reportEvent(TestCaseFinished event, TestResults testResults) {
        TestCase test = event.getTestCase();
        TestRunInfo testRunInfo = testResults.getInfoFor(test);

        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(new FileInputStream(file));
            Sheet sheet = workbook.getSheetAt(0);

            writeFirstEntry(sheet, testRunInfo);
            final int[] index = {1};
            testRunInfo.steps.forEach((stepRunInfo) -> {
                writeStep(sheet, testRunInfo, stepRunInfo, index[0]);
                index[0]++;
            });
            writeLastEntry(sheet, testRunInfo);
            workbook.write(new FileOutputStream(file));
        } catch (Exception e) {
            throw new OpeningExcelInReport(e);
        }
    }

    private void writeFirstEntry(Sheet sheet, TestRunInfo testRunInfo) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        row.createCell(COLUMNS.START_TIME.index).setCellValue(testRunInfo.startTime.format(DATETIME_EXCEL_FORMATTER));
        row.createCell(COLUMNS.END_TIME.index).setCellValue(testRunInfo.startTime.format(DATETIME_EXCEL_FORMATTER));
        row.createCell(COLUMNS.TEST_TIME_TYPE.index).setCellValue("Info");
        row.createCell(COLUMNS.APPLICATION.index).setCellValue(testRunInfo.application.getName());
        row.createCell(COLUMNS.TEST.index).setCellValue(testRunInfo.name + "_I1");
        row.createCell(COLUMNS.RESULT.index).setCellValue("Pass");
        row.createCell(COLUMNS.VERSION.index).setCellValue(testRunInfo.application.getVersion());
        row.createCell(COLUMNS.EXPECTED.index).setCellValue(testRunInfo.expectedResult);
        row.createCell(COLUMNS.IMAGE_COMMENT.index).setCellValue("NULL");
        row.createCell(COLUMNS.IMAGE.index).setCellValue("NULL");
        row.createCell(COLUMNS.IN_ADO.index).setCellValue("NULL");
        row.createCell(COLUMNS.SYSTEM_NAME.index).setCellValue(ConfigReader.getInstance().<String>readConfig("SYSTEM_NAME", ConfigReader.SupportedType.STRING));
        row.createCell(COLUMNS.WEBSITE.index).setCellValue("NULL");
        row.createCell(COLUMNS.RUN.index).setCellValue("NULL");
        row.createCell(COLUMNS.STATE.index).setCellValue("VALID");
        row.createCell(COLUMNS.SESSION_COLLECTION_ID.index).setCellValue(ConfigReader.getInstance().<String>readConfig("SESSION_COLLECTION_ID", ConfigReader.SupportedType.STRING));
        row.createCell(COLUMNS.SESSION_ID.index).setCellValue(ConfigReader.getInstance().<String>readConfig("SESSION_ID", ConfigReader.SupportedType.STRING));
        row.createCell(COLUMNS.MEASUREMENT_PARAMETERS.index).setCellValue("NULL");
    }

    private void writeLastEntry(Sheet sheet, TestRunInfo testRunInfo) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        row.createCell(COLUMNS.START_TIME.index).setCellValue(testRunInfo.startTime.format(DATETIME_EXCEL_FORMATTER));
        row.createCell(COLUMNS.END_TIME.index).setCellValue(testRunInfo.startTime.format(DATETIME_EXCEL_FORMATTER));
        row.createCell(COLUMNS.TEST_TIME_TYPE.index).setCellValue("TestCase");
        row.createCell(COLUMNS.APPLICATION.index).setCellValue(testRunInfo.application.getName());
        row.createCell(COLUMNS.TEST.index).setCellValue(testRunInfo.name + "_T1");
        row.createCell(COLUMNS.RESULT.index).setCellValue(testRunInfo.getResult());
        row.createCell(COLUMNS.VERSION.index).setCellValue(testRunInfo.application.getVersion());
        row.createCell(COLUMNS.EXPECTED.index).setCellValue(testRunInfo.expectedResult);
        row.createCell(COLUMNS.IMAGE_COMMENT.index).setCellValue("NULL");
        row.createCell(COLUMNS.IMAGE.index).setCellValue("NULL");
        row.createCell(COLUMNS.IN_ADO.index).setCellValue("NULL");
        row.createCell(COLUMNS.SYSTEM_NAME.index).setCellValue(ConfigReader.getInstance().<String>readConfig("SYSTEM_NAME", ConfigReader.SupportedType.STRING));
        row.createCell(COLUMNS.WEBSITE.index).setCellValue("NULL");
        row.createCell(COLUMNS.RUN.index).setCellValue("NULL");
        row.createCell(COLUMNS.STATE.index).setCellValue("VALID");
        row.createCell(COLUMNS.SESSION_COLLECTION_ID.index).setCellValue(ConfigReader.getInstance().<String>readConfig("SESSION_COLLECTION_ID", ConfigReader.SupportedType.STRING));
        row.createCell(COLUMNS.SESSION_ID.index).setCellValue(ConfigReader.getInstance().<String>readConfig("SESSION_ID", ConfigReader.SupportedType.STRING));
        row.createCell(COLUMNS.MEASUREMENT_PARAMETERS.index).setCellValue("NULL");
    }

    private void writeStep(Sheet sheet, TestRunInfo testRunInfo, StepRunInfo stepRunInfo, Integer index) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        row.createCell(COLUMNS.START_TIME.index).setCellValue(stepRunInfo.startTime.format(DATETIME_EXCEL_FORMATTER));
        row.createCell(COLUMNS.END_TIME.index).setCellValue(stepRunInfo.endTime.format(DATETIME_EXCEL_FORMATTER));
        row.createCell(COLUMNS.TEST_TIME_TYPE.index).setCellValue("TestStep");
        row.createCell(COLUMNS.APPLICATION.index).setCellValue(testRunInfo.application.getName());
        row.createCell(COLUMNS.TEST.index).setCellValue(testRunInfo.name + "_S" + index);
        row.createCell(COLUMNS.RESULT.index).setCellValue(readResults(stepRunInfo.status));
        row.createCell(COLUMNS.VERSION.index).setCellValue(testRunInfo.application.getVersion());
        row.createCell(COLUMNS.EXPECTED.index).setCellValue(stepRunInfo.expectedResult);
        row.createCell(COLUMNS.IMAGE_COMMENT.index).setCellValue("NULL");
        row.createCell(COLUMNS.IMAGE.index).setCellValue(stepRunInfo.screenshot.getAbsolutePath());
        row.createCell(COLUMNS.IN_ADO.index).setCellValue("NULL");
        row.createCell(COLUMNS.SYSTEM_NAME.index).setCellValue(ConfigReader.getInstance().<String>readConfig("SYSTEM_NAME", ConfigReader.SupportedType.STRING));
        row.createCell(COLUMNS.WEBSITE.index).setCellValue("NULL");
        row.createCell(COLUMNS.RUN.index).setCellValue("NULL");
        row.createCell(COLUMNS.STATE.index).setCellValue("VALID");
        row.createCell(COLUMNS.SESSION_COLLECTION_ID.index).setCellValue(ConfigReader.getInstance().<String>readConfig("SESSION_COLLECTION_ID", ConfigReader.SupportedType.STRING));
        row.createCell(COLUMNS.SESSION_ID.index).setCellValue(ConfigReader.getInstance().<String>readConfig("SESSION_ID", ConfigReader.SupportedType.STRING));
        row.createCell(COLUMNS.MEASUREMENT_PARAMETERS.index).setCellValue("NULL");
    }
}
