package com.olenickglobal.exceptions;

import org.apache.commons.text.StringEscapeUtils;

public class TextNotFoundException extends FindFailedException {
    protected static String getPrefixMessage(String text) {
        return "Text \"" + StringEscapeUtils.escapeJava(text) + "\" not found";
    }

    public TextNotFoundException(String text) {
        this(text, (Throwable) null);
    }

    public TextNotFoundException(String text, String message) {
        this(text, message, null);
    }

    public TextNotFoundException(String text, Throwable cause) {
        super(getPrefixMessage(text) + ".", cause);
    }

    public TextNotFoundException(String text, String message, Throwable cause) {
        super(getPrefixMessage(text) + ": " + message, cause);
    }
}
