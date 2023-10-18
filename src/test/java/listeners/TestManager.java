package listeners;

import com.olenickglobal.TestResults;
import com.olenickglobal.Utils.Application;
import com.olenickglobal.Utils.TestRunInfo;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class TestManager {


    public void initializeTest(TestCaseStarted event, TestResults testResults) {
        TestRunInfo testInfo = new TestRunInfo(this.getName(event),this.getApplication(event),this.getExpectedResult(event));
        testResults.addTest(event.getTestCase(),testInfo);
    }

    private String getName(TestCaseStarted event){
        return event.getTestCase().getName();
    }

    private Application getApplication(TestCaseStarted event){
       String appName = event.getTestCase().getTags().stream().filter(tag -> tag.startsWith("@Application")).collect(Collectors.joining(", "));
       String version = event.getTestCase().getTags().stream().filter(tag -> tag.startsWith("@Version")).collect(Collectors.joining(", "));
       if (version.isEmpty()){
           return new Application(appName);
       } else {
           return new Application(appName,version);
       }
    }

    private String getExpectedResult(TestCaseStarted event){
        return event.getTestCase().getTags().stream().filter(tag -> tag.startsWith("@ExpectedResult")).collect(Collectors.joining(", "));
    }

    public void finalizeTest(TestCaseFinished event, TestResults testResults) {
        TestRunInfo testInfo = testResults.getInfoFor(event.getTestCase());
        testInfo.endTime = LocalDateTime.now();
    }
}
