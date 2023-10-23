package com.olenickglobal.Exceptions;

import java.io.IOException;

public class FailureToAddStaticConfig extends RuntimeException {
    public FailureToAddStaticConfig(IOException e) {
    }
}
