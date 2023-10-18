package com.olenickglobal.Utils;

import java.awt.*;

public class ExceptionManager{


    public void withException(RunnableWithException o, String errorMessage) {
        try {
            o.run();
        } catch (Exception e) {
            throw new RuntimeException(errorMessage);
        }
    }
}
