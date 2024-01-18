package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.OCRData;

public class OCRDataFormatter extends EventFormatter<OCRData, Throwable> {


    @Override
    public String getMessage(Event<OCRData, Throwable> event) {
        return String.format("Performing OCR to find text %s", event.data().text());
    }

}
