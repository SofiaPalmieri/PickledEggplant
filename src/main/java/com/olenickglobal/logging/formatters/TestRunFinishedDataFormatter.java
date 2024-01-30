package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.TestRunFinishedData;

public class TestRunFinishedDataFormatter extends EventFormatter<TestRunFinishedData,Throwable>{

    @Override
    public String getMessage(Event<TestRunFinishedData, Throwable> event) {
        return "Finished test run";
    }
}
