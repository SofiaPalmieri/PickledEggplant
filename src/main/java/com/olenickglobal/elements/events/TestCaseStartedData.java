package com.olenickglobal.elements.events;

import io.cucumber.plugin.event.TestCaseStarted;

import java.time.Duration;
import java.time.LocalDateTime;

public record TestCaseStartedData(TestCaseStarted event) implements EventData<TestCaseStartedData.DataBuilder>{




    public static class DataBuilder implements EventDataBuilder<TestCaseStartedData> {

        protected TestCaseStarted event;

        public DataBuilder(TestCaseStartedData data){
            this.event = data.event;

        }
        @Override
        public TestCaseStartedData build() {
            return new TestCaseStartedData(this.event);
        }

    }

    @Override
    public DataBuilder builder() {
        return new DataBuilder(this);
    }


}
