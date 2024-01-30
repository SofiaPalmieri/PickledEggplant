package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.InteractiveRectSelectData;

public class InteractiveRectSelectDataFormatter extends EventFormatter<InteractiveRectSelectData, Throwable> {


    @Override
    public String getMessage(Event<InteractiveRectSelectData, Throwable> event) {
        return String.format("Selecting rectangle %s", this.rectFormatter.format(event.data().rectangle()));
    }

}
