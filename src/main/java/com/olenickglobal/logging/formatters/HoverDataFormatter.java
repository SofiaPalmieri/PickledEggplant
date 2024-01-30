package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.HoverData;

public class HoverDataFormatter extends EventFormatter<HoverData, Throwable> {
    @Override
    public String getMessage(Event<HoverData, Throwable> event) {
        return String.format("Hovering over %s", this.screenElementFormatter.format(event.data().element()));
    }

}
