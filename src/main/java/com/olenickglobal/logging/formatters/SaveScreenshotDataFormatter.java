package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.SaveScreenshotData;

public class SaveScreenshotDataFormatter extends EventFormatter<SaveScreenshotData, Throwable> {

    @Override
    public String getMessage(Event<SaveScreenshotData, Throwable> event) {
        return String.format("Saving screenshot at %s", event.data().path());
    }

}
