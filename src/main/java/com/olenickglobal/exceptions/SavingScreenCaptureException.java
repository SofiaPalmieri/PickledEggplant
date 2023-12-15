package com.olenickglobal.exceptions;

public class SavingScreenCaptureException extends RuntimeException {
    public SavingScreenCaptureException(String image) {
        this(image, null);
    }

    public SavingScreenCaptureException(String image, Throwable cause) {
        super("There was a failure saving the following image: " + image, cause);
    }
}
