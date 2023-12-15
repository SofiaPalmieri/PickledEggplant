package com.olenickglobal.listeners;

import com.olenickglobal.TestResults;
import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.exceptions.UnableToCreateReporter;
import io.cucumber.plugin.event.TestCaseFinished;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

// TODO: Check this out.
public class ReporterCaller {
    Map<String, Class<? extends Reporter>> reporterClasses = Map.of("rpvexcel", RPVExcelReporter.class);

    public Reporter createReporter(JSONObject section) {
        try {
            return reporterClasses.get(section.get("type")).getConstructor(new Class<?>[]{JSONObject.class}).newInstance(section);
        } catch (Exception e) {
            throw new UnableToCreateReporter();
        }
    }

    public void generateReports(TestCaseFinished event, TestResults testResults) {
        JSONArray sections = ConfigReader.getInstance().readConfig(ConfigReader.ConfigParam.REPORTERS, ConfigReader.SupportedType.JSON_ARRAY);
        for (int i = 0; i < sections.length(); i++) {
            JSONObject object = sections.getJSONObject(i);
            Reporter reporter = this.createReporter(object);
            reporter.reportEvent(event, testResults);
        }
    }
}
