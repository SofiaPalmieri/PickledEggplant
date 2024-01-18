package com.olenickglobal.Exceptions;

import java.io.IOException;

public class FailureToAddRuntimeProperties extends RuntimeException {
    public FailureToAddRuntimeProperties(IOException e) {
        super("Failure to add runtime properties. Remember to set the flag -DRUNTIME_CONFIG_FILE in your VM Options");
    }
}
