package com.olenickglobal.elements.events;

import io.cucumber.plugin.event.TestStepFinished;

public record TestStepFinishedData(TestStepFinished event) implements EventData<TestStepFinishedData.DataBuilder> {

    public static class DataBuilder implements EventDataBuilder<TestStepFinishedData> {
        protected TestStepFinished event;

        public DataBuilder(TestStepFinishedData data) {
            this.event = data.event;
        }

        @Override
        public TestStepFinishedData build() {
            return new TestStepFinishedData(this.event);
        }
    }

    @Override
    public DataBuilder builder() {
        return new DataBuilder(this);
    }
}
