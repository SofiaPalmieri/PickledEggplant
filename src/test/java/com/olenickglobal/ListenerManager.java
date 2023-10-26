package com.olenickglobal;

import com.olenickglobal.Utils.ConfigReader;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import listeners.*;
import org.testng.Reporter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListenerManager implements ConcurrentEventListener {


    private static final TestResults testResults = new TestResults();


    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestCaseStarted.class, (TestCaseStarted event) -> {new TestManager().initializeTest(event,testResults);});
        eventPublisher.registerHandlerFor(TestStepStarted.class, (TestStepStarted event) -> {new StepManager().initializeStep(event,testResults);});
        eventPublisher.registerHandlerFor(TestStepFinished.class, (TestStepFinished event) -> {new ScreenshotCapturer().captureScreenshotForStep(event,testResults);});
        eventPublisher.registerHandlerFor(TestStepFinished.class, (TestStepFinished event) -> {new StepManager().finalizeStep(event, testResults);});
        eventPublisher.registerHandlerFor(TestCaseFinished.class, (TestCaseFinished event) -> {new TestManager().finalizeTest(event,testResults);});
        eventPublisher.registerHandlerFor(TestCaseFinished.class, (TestCaseFinished event) -> {new ReporterCaller().generateReports(event, testResults);});
    }



}




