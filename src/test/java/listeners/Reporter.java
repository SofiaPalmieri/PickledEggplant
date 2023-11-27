package listeners;

import com.olenickglobal.TestResults;
import io.cucumber.plugin.event.TestCaseFinished;
import org.json.JSONObject;

public abstract class Reporter {
    public Reporter(JSONObject section) {
    }

    public abstract void reportEvent(TestCaseFinished event, TestResults testResults);
}
