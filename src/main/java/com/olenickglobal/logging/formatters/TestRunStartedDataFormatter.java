package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.TestRunStartedData;

public class TestRunStartedDataFormatter extends EventFormatter<TestRunStartedData,Throwable>{

    @Override
    public String getMessage(Event<TestRunStartedData, Throwable> event) {
        return "Starting test run";
    }
}
