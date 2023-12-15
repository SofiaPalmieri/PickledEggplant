package com.olenickglobal;

import com.olenickglobal.Utils.TestRunInfo;
import io.cucumber.plugin.event.TestCase;


import java.util.LinkedHashMap;
import java.util.Map;

public class TestResults {

    private static final Map<TestCase,TestRunInfo> testResults = new LinkedHashMap<>();

    public void addTest(TestCase testCase, TestRunInfo info) {
        testResults.put(testCase,info);
    }

    public TestRunInfo getInfoFor(TestCase testCase) {
        return testResults.get(testCase);
    }
}
