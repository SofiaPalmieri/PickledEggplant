package com.olenickglobal.elements.events;

import io.cucumber.plugin.event.TestCaseFinished;

import java.time.Duration;
import java.time.LocalDateTime;

public record TestCaseFinishedData(TestCaseFinished event) implements EventData<TestCaseFinishedData.DataBuilder>{



    public static class DataBuilder implements EventDataBuilder<TestCaseFinishedData> {
        protected TestCaseFinished event;


        public DataBuilder(TestCaseFinishedData data) {
            this.event = data.event;
        }

        @Override
        public TestCaseFinishedData build() {
            return new TestCaseFinishedData(this.event);
        }
    }

    @Override
    public DataBuilder builder() {
        return new DataBuilder(this);
    }
}

