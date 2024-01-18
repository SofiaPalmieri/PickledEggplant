package com.olenickglobal.logging.formatters.elementFormatters;

import com.olenickglobal.elements.ScreenElement;
import com.olenickglobal.logging.formatters.ElementFormatter;

public class ScreenElementFormatter implements ElementFormatter<ScreenElement> {
    @Override
    public String format(ScreenElement element) {
        return element == null ? "NULL" : String.format("%s", element.getClass().getSimpleName());
    }
}
