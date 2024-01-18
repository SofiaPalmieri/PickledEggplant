package com.olenickglobal.logging.formatters;


import com.olenickglobal.elements.events.*;
import com.olenickglobal.elements.events.Event;

import java.awt.*;

public class CaptureEventFormatter extends EventFormatter<CaptureData,Throwable>{

    @Override
    public String getMessage(Event<CaptureData, Throwable> event) {

        CaptureData data = event.data();
        Rectangle rectangle = data.rectangle();


        return String.format("Peforming capture over rectangle %s",this.rectFormatter.format(rectangle));
    }


}



