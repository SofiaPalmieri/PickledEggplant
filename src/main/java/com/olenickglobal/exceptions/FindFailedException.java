package com.olenickglobal.exceptions;

public class FindFailedException extends RuntimeException {
    public FindFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
