package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.TypingData;

public class TypingDataFormatter extends EventFormatter<TypingData,Throwable>{

    @Override
    public String getMessage(Event<TypingData, Throwable> event) {
        return String.format("Typing text %s",event.data().text());
    }
}
