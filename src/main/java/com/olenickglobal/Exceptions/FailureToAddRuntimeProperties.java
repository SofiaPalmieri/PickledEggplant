package com.olenickglobal.Exceptions;

import java.io.IOException;

public class FailureToAddRuntimeProperties extends RuntimeException {
    public FailureToAddRuntimeProperties(IOException e) {
    }
}
