package com.olenickglobal.testinfo;

import com.olenickglobal.entities.Application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestRunInfo {
    public final String name;
    public final Application application;
    public final LocalDateTime startTime = LocalDateTime.now();
    public final String expectedResult;
    public LocalDateTime endTime = null;
    public final List<StepRunInfo> steps = new ArrayList<>();

    public TestRunInfo(String name, Application application, String expectedResult) {
        this.name = name;
        this.expectedResult = expectedResult;
        this.application = application;
    }

    public void addStepInfo(StepRunInfo stepInfo) {
        steps.add(stepInfo);
    }

    public StepRunInfo getLastStepInfo() {
        return steps.get(steps.size() - 1);
    }

    public String getResult() {
        if (steps.stream().allMatch(step -> step.status.equals(io.cucumber.plugin.event.Status.PASSED))) {
            return "Pass";
        } else {
            return "Fail";
        }
    }
}
