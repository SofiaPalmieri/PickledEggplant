package listeners;

import com.olenickglobal.TestResults;
import io.cucumber.plugin.event.TestCaseFinished;
import org.json.JSONObject;

public interface Reporter {

   void reportEvent(TestCaseFinished event, TestResults testResults);

   Reporter build(JSONObject section);


}
