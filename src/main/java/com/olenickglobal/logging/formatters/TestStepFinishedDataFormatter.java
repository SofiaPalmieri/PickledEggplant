package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.TestStepFinishedData;

public class TestStepFinishedDataFormatter extends EventFormatter<TestStepFinishedData, Throwable>{

    @Override
    public String getMessage(Event<TestStepFinishedData, Throwable> event) {
        return String.format("Finished test step: %s",event.data().event().getTestStep().toString());
    }
}
