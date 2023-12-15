package listeners;

import com.olenickglobal.TestResults;
import com.olenickglobal.entities.Application;
import com.olenickglobal.testinfo.TestRunInfo;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestManager {
    public static String extractTag(String tag, String input) {
        String regex = "@" + tag + "\\((.*?)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1).replaceAll("_", " ");
        }
        return "";
    }

    public void finalizeTest(TestCaseFinished event, TestResults testResults) {
        TestRunInfo testInfo = testResults.getInfoFor(event.getTestCase());
        testInfo.endTime = LocalDateTime.now();
    }

    public void initializeTest(TestCaseStarted event, TestResults testResults) {
        TestRunInfo testInfo = new TestRunInfo(this.getName(event), this.getApplication(event), this.getExpectedResult(event));
        testResults.addTest(event.getTestCase(), testInfo);
    }

    private Application getApplication(TestCaseStarted event) {
        String appName = extractTag("Application", event.getTestCase().getTags().stream().filter(tag -> tag.startsWith("@Application")).collect(Collectors.joining(", ")));
        String version = extractTag("Version", event.getTestCase().getTags().stream().filter(tag -> tag.startsWith("@Version")).collect(Collectors.joining(", ")));
        if (version.isEmpty()) {
            return new Application(appName);
        } else {
            return new Application(appName, version);
        }
    }

    private String getExpectedResult(TestCaseStarted event) {
        return event.getTestCase().getTags().stream().filter(tag -> tag.startsWith("@ExpectedResult")).collect(Collectors.joining(", "));
    }

    private String getName(TestCaseStarted event) {
        return event.getTestCase().getName();
    }
}
