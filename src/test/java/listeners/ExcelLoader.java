package listeners;

import com.olenickglobal.TestResults;
import com.olenickglobal.Utils.ConfigReader;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestStepFinished;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class ExcelLoader {

    static final Map<Status, String> STATUS_TRANSLATION = new EnumMap<Status, String>(Map.of(
            Status.PASSED, "Pass",
            Status.FAILED, "Fail",
            Status.AMBIGUOUS, "Fail",
            Status.PENDING, "Fail",
            Status.UNDEFINED, "Fail",
            Status.UNUSED, "Fail"
    ));
    private File file = new File(new ConfigReader().readConfig(ConfigReader.Configs.EXCEL_PATH));



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

    public void loadIntoExcel(TestCaseFinished event, TestResults testResults) {

        Integer rowNumber = 0;


    }
}
