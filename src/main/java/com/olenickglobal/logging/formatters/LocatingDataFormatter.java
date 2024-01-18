package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.LocatingData;

public class LocatingDataFormatter extends EventFormatter<LocatingData<?>, Throwable> {

    @Override
    public String getMessage(Event<LocatingData<?>, Throwable> event) {
        return String.format("Locating %s", this.screenElementFormatter.format(event.data().element()));
    }

}
