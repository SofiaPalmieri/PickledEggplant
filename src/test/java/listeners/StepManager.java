package listeners;

import com.olenickglobal.TestResults;
import com.olenickglobal.Utils.StepRunInfo;
import com.olenickglobal.Utils.TestRunInfo;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class StepManager {

    public void initializeStep(TestStepStarted event, TestResults testResults) {
        TestRunInfo info = testResults.getInfoFor(event.getTestCase());
        StepRunInfo stepInfo = new StepRunInfo(event.getTestCase().getName(),LocalDateTime.now());
        info.addStepInfo(stepInfo);
    }

    public void finalizeStep(TestStepFinished event, TestResults testResults) {
        TestRunInfo info = testResults.getInfoFor(event.getTestCase());
        StepRunInfo lastStepInfo = info.getLastStepInfo();
        lastStepInfo.endTime = LocalDateTime.now();
        lastStepInfo.status = event.getResult().getStatus();
    }

}
