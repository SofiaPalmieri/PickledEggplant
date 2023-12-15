package com.olenickglobal.exceptions;

public class ConfigurationNotFound extends RuntimeException {
    public ConfigurationNotFound(String config) {
        super("The following configuration was not found: " + config);
    }
}
