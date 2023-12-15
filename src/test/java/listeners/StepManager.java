package listeners;

import com.olenickglobal.TestResults;
import com.olenickglobal.testinfo.StepRunInfo;
import com.olenickglobal.testinfo.TestRunInfo;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;

import java.time.LocalDateTime;

public class StepManager {
    public void finalizeStep(TestStepFinished event, TestResults testResults) {
        TestRunInfo info = testResults.getInfoFor(event.getTestCase());
        StepRunInfo lastStepInfo = info.getLastStepInfo();
        lastStepInfo.endTime = LocalDateTime.now();
        lastStepInfo.status = event.getResult().getStatus();
    }

    public void initializeStep(TestStepStarted event, TestResults testResults) {
        TestRunInfo info = testResults.getInfoFor(event.getTestCase());
        StepRunInfo stepInfo = new StepRunInfo(event.getTestCase().getName(), LocalDateTime.now());
        info.addStepInfo(stepInfo);
    }
}
