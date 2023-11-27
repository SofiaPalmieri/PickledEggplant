package com.olenickglobal.exceptions;

public class SavingScreenCapture extends RuntimeException {
    public SavingScreenCapture(String image) {
        super("There was a failure saving the following image: " + image);
    }
}
