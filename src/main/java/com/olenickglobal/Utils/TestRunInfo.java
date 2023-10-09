package com.olenickglobal.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestRunInfo {

    public final String name;
    public final String application;

    public final LocalDateTime startTime = LocalDateTime.now();

    public final List<StepRunInfo> steps = new ArrayList<>();

    public TestRunInfo(String name, String application){
        this.name = name;
        this.application = application;
    }

    public void addStepInfo(StepRunInfo stepInfo) {
        steps.add(stepInfo);
    }

    public StepRunInfo getLastStepInfo() {
        return steps.get(steps.size()-1);
    }
}
