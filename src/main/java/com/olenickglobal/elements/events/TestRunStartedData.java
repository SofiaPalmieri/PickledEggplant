package com.olenickglobal.elements.events;

import io.cucumber.plugin.event.TestRunStarted;

public record TestRunStartedData(TestRunStarted event) implements EventData<TestRunStartedData.DataBuilder> {

    public static class DataBuilder implements EventDataBuilder<TestRunStartedData> {
        protected TestRunStarted event;

        public DataBuilder(TestRunStartedData data) {
            this.event = data.event;
        }

        @Override
        public TestRunStartedData build() {
            return new TestRunStartedData(this.event);
        }
    }

    @Override
    public DataBuilder builder() {
        return new DataBuilder(this);
    }
}

