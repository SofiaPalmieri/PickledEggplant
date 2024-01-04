package com.olenickglobal;


import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.elements.events.*;
import com.olenickglobal.elements.events.Event;


import formatting.EventFormatter;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CucumberLogManager implements ConcurrentEventListener {



    private final Map<EventType, Level> eventLevels = new HashMap<>();

    private final EventFormatter eventFormatter = new EventFormatter();



    public void collectLogLevels(){
        JSONObject levels = ConfigReader.getInstance().readConfig(ConfigReader.ConfigParam.LOG_LEVELS, ConfigReader.SupportedType.JSON_OBJECT);
        Arrays.stream(EventType.values()).toList().forEach(type -> {
            try {
                Level level = Level.valueOf(levels.getString(String.valueOf(type)));
                eventLevels.put(type,level);
            } catch (Exception e) {
                eventLevels.put(type,null);
            }
        });
    }


    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {

        collectLogLevels();

        EventEmitter listener = EventEmitter.getGlobal();

        eventPublisher.registerHandlerFor(TestCaseStarted.class, (TestCaseStarted event) -> {
            listener.emit(new Event<TestCaseStartedData, RuntimeException>(LocalDateTime.now(), EventType.TEST_CASE_STARTED, new TestCaseStartedData(event)));
        });
        eventPublisher.registerHandlerFor(TestRunStarted.class, (TestRunStarted event) -> {
            listener.emit(new Event<TestRunStartedData, RuntimeException>(LocalDateTime.now(), EventType.TEST_RUN_STARTED, new TestRunStartedData(event)));
        });
        eventPublisher.registerHandlerFor(TestStepStarted.class, (TestStepStarted event) -> {
            listener.emit(new Event<TestStepStartedData, RuntimeException>(LocalDateTime.now(), EventType.TEST_STEP_STARTED, new TestStepStartedData(event)));
        });
        eventPublisher.registerHandlerFor(TestStepFinished.class, (TestStepFinished event) -> {
            listener.emit(new Event<TestStepFinishedData, RuntimeException>(LocalDateTime.now(), EventType.TEST_STEP_FINISHED, new TestStepFinishedData(event)));
        });
        eventPublisher.registerHandlerFor(TestCaseFinished.class, (TestCaseFinished event) -> {
            listener.emit(new Event<TestCaseFinishedData, RuntimeException>(LocalDateTime.now(), EventType.TEST_CASE_FINISHED, new TestCaseFinishedData(event)));
        });
        eventPublisher.registerHandlerFor(TestRunFinished.class, (TestRunFinished event) -> {
            listener.emit(new Event<TestRunFinishedData, RuntimeException>(LocalDateTime.now(), EventType.TEST_RUN_FINISHED, new TestRunFinishedData(event)));
        });


        Arrays.stream(EventType.values()).toList().forEach((type) -> {
            listener.addEventListener(type,(event) -> {
                Level level = this.eventLevels.get(type);
                if (level == null) return;
                String message = type.writeLog(this.eventFormatter,event);
                Logger logger = LoggerFactory.getLogger("PickledLoggers."+ type);
                logger.atLevel(level).log(message);
            });
        });


    }


}
