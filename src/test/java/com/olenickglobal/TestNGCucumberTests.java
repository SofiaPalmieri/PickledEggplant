package com.olenickglobal;

import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.configuration.SUTAwarePicoFactory;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@CucumberOptions(objectFactory = SUTAwarePicoFactory.class, plugin = { "com.olenickglobal.CucumberFlowManager", "com.olenickglobal.CucumberLogManager" })
public class TestNGCucumberTests extends AbstractTestNGCucumberTests {
    @BeforeClass(alwaysRun = true)
    public void setUpClass(ITestContext context) {
        ConfigReader configReader = ConfigReader.getInstance();
        File featureListFile = new File(configReader.<String>readConfig(ConfigReader.ConfigParam.FEATURE_LIST_FILE, ConfigReader.SupportedType.STRING));
        String features = getFeatures(featureListFile);
        System.setProperty("cucumber.features", features);
        super.setUpClass(context);
    }

    private static String getFeatures(File featureListFile) {
        StringBuilder features = new StringBuilder();
        try (FileReader reader = new FileReader(featureListFile)) {
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                features.append(line).append(",");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return features.toString();
    }
}
