package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.TestCaseFinishedData;
import com.olenickglobal.elements.events.TestCaseStartedData;


public class TestCaseStartedDataFormatter extends EventFormatter<TestCaseStartedData,Throwable> {
    @Override
    public String getMessage(Event<TestCaseStartedData, Throwable> event) {
        return String.format("Test case started: %s", event.data().event().getTestCase().getName());
    }
}
