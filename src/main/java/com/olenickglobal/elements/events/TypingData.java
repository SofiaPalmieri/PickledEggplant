package com.olenickglobal.elements.events;

/**
 * Typing data.
 *
 * @param text Text to be typed.
 */
public record TypingData(String text) implements EventData<TypingData.TypingDataBuilder> {
    public static class TypingDataBuilder implements EventDataBuilder<TypingData> {
        protected String text;

        public TypingDataBuilder(TypingData data) {
            this.text = data.text;
        }

        public TypingData build() {
            return new TypingData(text);
        }

        public TypingDataBuilder withText(String text) {
            this.text = text;
            return this;
        }
    }

    public TypingDataBuilder builder() {
        return new TypingDataBuilder(this);
    }
}
