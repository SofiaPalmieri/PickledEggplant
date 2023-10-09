package listeners;

import com.olenickglobal.TestResults;
import com.olenickglobal.Utils.TestRunInfo;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;

import java.util.stream.Collectors;

public class TestManager {


    public void initializeTest(TestCaseStarted event, TestResults testResults) {
        TestRunInfo testInfo = new TestRunInfo(this.getName(event),this.getApplication(event));
        testResults.addTest(event.getTestCase(),testInfo);
    }

    private String getName(TestCaseStarted event){
        return event.getTestCase().getName();
    }

    private String getApplication(TestCaseStarted event){
        return event.getTestCase().getTags().stream().filter(tag -> tag.startsWith("@Application")).collect(Collectors.joining(", "));
    }

    public void finalizeTest(TestCaseFinished event, TestResults testResults) {
    }
}
