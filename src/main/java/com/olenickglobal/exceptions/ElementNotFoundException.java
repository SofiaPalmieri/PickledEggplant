package com.olenickglobal.exceptions;

public class ElementNotFoundException extends FindFailedException {
    public ElementNotFoundException() {
        this("Element not found.");
    }

    public ElementNotFoundException(String message) {
        this(message, null);
    }

    public ElementNotFoundException(Throwable cause) {
        super("Element not found.", cause);
    }

    public ElementNotFoundException(String message, Throwable cause) {
        super("Element not found: " + message, cause);
    }
}
