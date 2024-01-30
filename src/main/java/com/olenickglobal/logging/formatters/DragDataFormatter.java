package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.DragData;
import com.olenickglobal.elements.events.Event;

public class DragDataFormatter extends EventFormatter<DragData,Throwable>{

    @Override
    public String getMessage(Event<DragData,Throwable> event) {
        return String.format("Performing drag from %s to %s", this.screenElementFormatter.format(event.data().origin()), this.screenElementFormatter.format(event.data().destination()));
    }

}
