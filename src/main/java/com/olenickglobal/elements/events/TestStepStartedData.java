package com.olenickglobal.elements.events;

import io.cucumber.plugin.event.TestStepStarted;

public record TestStepStartedData(TestStepStarted event) implements EventData<TestStepStartedData.DataBuilder> {

    public static class DataBuilder implements EventDataBuilder<TestStepStartedData> {
        protected TestStepStarted event;

        public DataBuilder(TestStepStartedData data) {
            this.event = data.event;
        }

        @Override
        public TestStepStartedData build() {
            return new TestStepStartedData(this.event);
        }
    }

    @Override
    public DataBuilder builder() {
        return new DataBuilder(this);
    }
}

