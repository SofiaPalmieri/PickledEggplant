package com.olenickglobal.exceptions;

public class ConfigParamNotFoundError extends ConfigurationError {
    public ConfigParamNotFoundError(String config) {
        super("The following configuration parameter was not found: " + config);
    }
}
