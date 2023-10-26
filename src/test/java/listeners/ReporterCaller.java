package listeners;


import com.olenickglobal.TestResults;
import com.olenickglobal.Utils.ConfigReader;
import io.cucumber.plugin.event.TestCaseFinished;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;



public class ReporterCaller {

    Map<String, Class<? extends listeners.Reporter>> reporterClasses = Map.of("rpvexcel", RPVExcelReporter.class);

    public listeners.Reporter createReporter(JSONObject section) {
        try {
            return reporterClasses.get(section.get("type")).getConstructor().newInstance().build(section);
        } catch (Exception e) {
            throw new UnableToCreateReporter();
        }
    }

    public void generateReports(TestCaseFinished event, TestResults testResults) {
        JSONArray sections = ConfigReader.getInstance().readConfig(ConfigReader.Configs.REPORTERS, ConfigReader.SupportedTypes.JSON_ARRAY);
        sections.toList().forEach(object -> {
            Reporter reporter = this.createReporter((JSONObject) object);
            reporter.reportEvent(event,testResults);
        });
    }
}


