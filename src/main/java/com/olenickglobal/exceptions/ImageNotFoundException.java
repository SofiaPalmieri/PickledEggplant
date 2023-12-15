package com.olenickglobal.exceptions;

public class ImageNotFoundException extends FindFailedException {
    public ImageNotFoundException(String message) {
        this(message, null);
    }

    public ImageNotFoundException(Throwable cause) {
        super("Image not found.", cause);
    }

    public ImageNotFoundException(String message, Throwable cause) {
        super("Image not found: " + message, cause);
    }
}
