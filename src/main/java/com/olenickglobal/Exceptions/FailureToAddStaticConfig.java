package com.olenickglobal.Exceptions;

import java.io.IOException;

public class FailureToAddStaticConfig extends RuntimeException {
    public FailureToAddStaticConfig(IOException e) {
        super("Failure to add static configurations, remember to set -DSTATIC_CONFIG_FILE in your vm options");
    }
}
