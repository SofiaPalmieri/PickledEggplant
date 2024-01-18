package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.TestStepStartedData;

public class TestStepStartedDataFormatter extends EventFormatter<TestStepStartedData,Throwable>{
    @Override
    public String getMessage(Event<TestStepStartedData, Throwable> event) {
        return String.format("Starting step %s",event.data().event().getTestStep().toString());
    }
}
