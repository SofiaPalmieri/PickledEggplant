package com.olenickglobal.exceptions;

import java.awt.*;

public class SUTRobotException extends RuntimeException {
    public SUTRobotException(AWTException e) {
        super("There was a failure loading the SUTRobot when creating a SUT instance. This should not happen so don't ask me.", e);
    }
}
