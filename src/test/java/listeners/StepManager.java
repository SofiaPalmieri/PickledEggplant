package listeners;

import com.olenickglobal.TestResults;
import com.olenickglobal.Utils.StepRunInfo;
import com.olenickglobal.Utils.TestRunInfo;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;

import java.time.LocalDateTime;

public class StepManager {
    public void initializeStep(TestStepStarted event, TestResults testResults) {
        TestRunInfo info = testResults.getInfoFor(event.getTestCase());
        StepRunInfo stepInfo = new StepRunInfo(event.getTestCase().getName(),LocalDateTime.now());
        info.addStepInfo(stepInfo);
    }

    public void finalizeStep(TestStepFinished event, TestResults testResults) {
    }
}
