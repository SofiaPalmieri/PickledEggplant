package com.olenickglobal.logging.formatters;

import com.olenickglobal.elements.ScreenElement;
import com.olenickglobal.elements.events.ClickData;
import com.olenickglobal.elements.events.Event;

import java.awt.*;

public class ClickEventFormatter extends EventFormatter<ClickData,Throwable>{

    @Override
    public String getMessage(Event<ClickData, Throwable> event) {
        ClickData data = event.data();

        ScreenElement screenElement = data.element();
        Rectangle elementRectangle = data.elementRectangle();


        return String.format("Performing action %s over %s", this.screenElementFormatter.format(screenElement), this.rectFormatter.format(elementRectangle));
    }

}
