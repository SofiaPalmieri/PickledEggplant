package com.olenickglobal.steps;

import com.olenickglobal.Utils.TestEventResult;
import io.cucumber.java.Scenario;

public class GlobalHooks {

    TestEventResult testCaseResult;

    public GlobalHooks(TestEventResult testCaseResult) {
        this.testCaseResult = testCaseResult;
    }


    //@BeforeStep
    public void beforeStep(Scenario scenario) {
        System.out.println(scenario.getClass().getCanonicalName());
    }

   // @AfterStep
    public void afterStep(Scenario scenario) {
        System.out.println(scenario.getClass().getCanonicalName());


    }


}
