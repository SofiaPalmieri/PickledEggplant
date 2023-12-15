package com.olenickglobal;

import com.olenickglobal.configuration.ConfigReader;
import org.testng.TestNG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ConfigReader configReader = ConfigReader.getInstance();
        String testNGXMLFile = configReader.readConfig(ConfigReader.ConfigParam.TESTNG_XML_FILE, ConfigReader.SupportedType.STRING);
        File featureListFile = new File(configReader.<String>readConfig(ConfigReader.ConfigParam.FEATURE_LIST_FILE, ConfigReader.SupportedType.STRING));
        TestNG testNG = new TestNG();
        String features = getFeatures(featureListFile);
        System.setProperty("cucumber.features", features);
        testNG.setTestSuites(Collections.singletonList(testNGXMLFile));
        testNG.run();
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
