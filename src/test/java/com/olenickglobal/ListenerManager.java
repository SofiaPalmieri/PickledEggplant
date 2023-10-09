package com.olenickglobal;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import listeners.ExcelLoader;
import listeners.ScreenshotCapturer;
import listeners.StepManager;
import listeners.TestManager;

public class ListenerManager implements ConcurrentEventListener {


    private static final TestResults testResults = new TestResults();


    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestCaseStarted.class, (TestCaseStarted event) -> {new TestManager().initializeTest(event,testResults);});
        eventPublisher.registerHandlerFor(TestStepStarted.class, (TestStepStarted event) -> {new StepManager().initializeStep(event,testResults);});
        eventPublisher.registerHandlerFor(TestStepFinished.class, (TestStepFinished event) -> {new ScreenshotCapturer().captureScreenshotForStep(event,testResults);});
        eventPublisher.registerHandlerFor(TestStepFinished.class, (TestStepFinished event) -> {new StepManager().finalizeStep(event, testResults);});
        eventPublisher.registerHandlerFor(TestCaseFinished.class, (TestCaseFinished event) -> {new TestManager().finalizeTest(event,testResults);});
        eventPublisher.registerHandlerFor(TestCaseFinished.class, (TestCaseFinished event) -> {new ExcelLoader().loadIntoExcel(event,testResults);});
    }



}
