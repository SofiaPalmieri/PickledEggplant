package com.olenickglobal.elements.events;

import io.cucumber.plugin.event.TestRunFinished;


    public record TestRunFinishedData(TestRunFinished event) implements EventData<TestRunFinishedData.DataBuilder> {

        public static class DataBuilder implements EventDataBuilder<TestRunFinishedData> {
            protected TestRunFinished event;

            public DataBuilder(TestRunFinishedData data) {
                this.event = data.event;
            }

            @Override
            public TestRunFinishedData build() {
                return new TestRunFinishedData(this.event);
            }
        }

        @Override
        public DataBuilder builder() {
            return new DataBuilder(this);
        }
    }
