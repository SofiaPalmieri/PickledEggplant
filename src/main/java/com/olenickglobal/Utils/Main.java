package com.olenickglobal.Utils;

import org.testng.TestNG;
import org.testng.TestRunner;

import java.io.*;
import java.util.Collections;

public class Main {

    public static void main(String[] args){
        ConfigReader configReader = ConfigReader.getInstance();
        String testNGXMLFile = configReader.readConfig(ConfigReader.Configs.TESTNG_XML_FILE);
        File featureListFile = new File(configReader.readConfig(ConfigReader.Configs.FEATURE_LIST_FILE));
        TestNG testNG = new TestNG();

        String features = getFeatures(featureListFile);
        System.setProperty("cucumber.features", features);

        testNG.setTestSuites(Collections.singletonList(testNGXMLFile));
        testNG.run();
    }

    private static String getFeatures(File featureListFile) {
        StringBuilder features = new StringBuilder();
        try(FileReader reader = new FileReader(featureListFile)){
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
