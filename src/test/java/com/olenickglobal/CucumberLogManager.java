package com.olenickglobal;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.FileAppender;
import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.elements.events.*;
import com.olenickglobal.elements.events.Event;


import com.olenickglobal.entities.SUT;
import com.olenickglobal.formatting.EventFormatter;

import com.olenickglobal.formatting.PickledEventFormatter;
import com.olenickglobal.serializers.RectangleSerializer;
import com.olenickglobal.serializers.SUTSerializer;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonGenerator;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonSerializer;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.SerializationFeature;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.SerializerProvider;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.module.SimpleModule;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.awt.*;
import java.io.IOException;
import java.lang.module.Configuration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CucumberLogManager implements ConcurrentEventListener {



    private final Map<EventType, Level> eventLevels = new HashMap<>();

    private final EventFormatter eventFormatter = new PickledEventFormatter();



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
                String message = type.writeLog(this.eventFormatter, event);
                Logger logger = getConsoleLogger(type);
                logger.atLevel(level).log(message);

                Logger fileLogger = getFileLogger(type);
                String fileMessage = getFileMessage(event,fileLogger);
                fileLogger.atLevel(level).log("Event Type: " + type + ", Data: " + fileMessage);

            });
        });
    }

    private String getFileMessage(Event<?,?> event, Logger fileLogger) {

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(new SUTSerializer(SUT.class));
        module.addSerializer(new RectangleSerializer(Rectangle.class));
        mapper.registerModule(module);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonData = "";

        try {
            jsonData = mapper.writeValueAsString(event.data());
        } catch (JsonProcessingException e) {
            fileLogger.error("Error while parsing event data to JSON", e);
        }

        return jsonData;
    }

    private Logger getFileLogger(EventType type) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        FileAppender<ILoggingEvent> file = new FileAppender<>();
        file.setName("PickledFileLogger");
        file.setFile(ConfigReader.getInstance().readConfig(ConfigReader.ConfigParam.LOG_MANAGER_LOG_FILE, ConfigReader.SupportedType.STRING));
        file.setContext(context);
        file.setAppend(true);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{HH:mm:ss.SSS} - %msg%n");
        encoder.start();

        file.setEncoder(encoder);
        file.start();

        ch.qos.logback.classic.Logger logger = context.getLogger("PickledFileLogger." + type);
        logger.addAppender(file);
        logger.setAdditive(false); // Avoid logging messages being logged by parent loggers

        return logger;
    }


    private Logger getConsoleLogger(EventType type) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setName("PickledConsoleLogger");
        consoleAppender.setContext(context);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{HH:mm:ss.SSS} - %msg%n");
        encoder.start();

        consoleAppender.setEncoder(encoder);
        consoleAppender.start();

        ch.qos.logback.classic.Logger logger = context.getLogger("PickledConsoleLogger." + type);
        logger.addAppender(consoleAppender);
        logger.setAdditive(false); // Avoid logging messages being logged by parent loggers

        return logger;
    }


}
