package com.olenickglobal.Utils;

import java.io.File;
import java.time.LocalDateTime;

public class StepRunInfo {

    public LocalDateTime startTime;
    public LocalDateTime endTime;

    public String stepName;

    public Status status;

    public void setScreenshot(File image) {
    }

    public enum Status {
        PASSED,
        FAILED,
        SKIPPED
    }

    public StepRunInfo(String name,LocalDateTime startTime) {
        this.stepName = name;
        this.startTime = startTime;
    }





}
