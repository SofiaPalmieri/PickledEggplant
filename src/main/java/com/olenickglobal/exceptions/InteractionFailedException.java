package com.olenickglobal.exceptions;

public class InteractionFailedException extends RuntimeException {
    public InteractionFailedException() {
        super("Interaction failed.");
    }

    public InteractionFailedException(String message) {
        this(message, null);
    }

    public InteractionFailedException(Throwable cause) {
        super("Interaction failed.", cause);
    }

    public InteractionFailedException(String message, Throwable cause) {
        super("Interaction failed: " + message, cause);
    }
}
