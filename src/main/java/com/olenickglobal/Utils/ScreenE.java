package com.olenickglobal.Utils;

import java.awt.*;
import java.io.IOException;

public abstract class ScreenE {



    public void move(double timeout) throws FindFailedHandler, IOException, AWTException {
        Rectangle rect = this.find(timeout);
        new PickledRobot().moveTo(rect);
    }

    public boolean found(double timeout) throws FindFailedHandler, IOException, AWTException {
        Rectangle rect = this.find(timeout);
        return true;
    }

    public void dragTo(ScreenE scr){

    }

    public long getTimeoutLimit(double wait){
        long t= System.currentTimeMillis();
        long MILLISECONDS_IN_SECOND = 1000;
        return (long) (t+wait * MILLISECONDS_IN_SECOND);
    }

    public boolean notTimedOut(long limit){
        return System.currentTimeMillis() < limit;
    }

    public abstract Rectangle find(double timeout) throws FindFailedHandler, IOException, AWTException;

}
