package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.TestCaseFinishedData;

public class TestCaseFinishedDataFormatter extends EventFormatter<TestCaseFinishedData, Throwable> {


    @Override
    public String getMessage(Event<TestCaseFinishedData, Throwable> event) {
        return String.format("Finished test case %s with result %s", event.data().event().getTestCase().getName(), event.data().event().getResult().toString());
    }
}
