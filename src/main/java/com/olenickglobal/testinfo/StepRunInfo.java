package com.olenickglobal.testinfo;

import io.cucumber.plugin.event.Status;

import java.io.File;
import java.time.LocalDateTime;

public class StepRunInfo {
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public String stepName;
    public String expectedResult;
    public Status status;
    public File screenshot;

    public StepRunInfo(String name, LocalDateTime startTime) {
        this.stepName = name;
        this.startTime = startTime;
    }
}
