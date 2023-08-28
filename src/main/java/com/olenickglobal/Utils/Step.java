package com.olenickglobal.Utils;

import java.awt.*;
import java.io.IOException;

public abstract class Step {
    String name;
    String expectedResult;
    int timeout;

    public abstract StepResult executeAction() throws FindFailedHandler, IOException, AWTException;
}
