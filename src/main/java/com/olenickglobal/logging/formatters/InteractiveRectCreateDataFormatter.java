package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.InteractiveRectCreateData;

public class InteractiveRectCreateDataFormatter extends EventFormatter<InteractiveRectCreateData, Throwable> {


    @Override
    public String getMessage(Event<InteractiveRectCreateData, Throwable> event) {
        return String.format("Creating rectangle %s", this.rectFormatter.format(event.data().rectangle()));
    }


}
