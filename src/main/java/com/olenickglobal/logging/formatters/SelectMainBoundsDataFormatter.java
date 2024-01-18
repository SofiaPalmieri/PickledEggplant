package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.SelectMainBoundsData;

public class SelectMainBoundsDataFormatter extends EventFormatter<SelectMainBoundsData, Throwable> {

    @Override
    public String getMessage(Event<SelectMainBoundsData, Throwable> event) {

        return "Selecting main bounds";
    }
}
