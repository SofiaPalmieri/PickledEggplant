package com.olenickglobal.Utils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestCase {
    String name;
    String applicationName;

    List<Step> steps = new ArrayList<Step>();
    TestCaseResult result;

    public void execute(){
        steps.forEach(aStep -> {
            try {
                aStep.executeAction();
            } catch (FindFailedHandler | IOException e) {
                throw new RuntimeException(e);
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }
        });
        if( steps.stream().allMatch(aStep -> {
            try {
                return aStep.executeAction().result.equals("Pass");
            } catch (FindFailedHandler | IOException | AWTException e) {
                throw new RuntimeException(e);
            }
        })){
            result.result = "Pass";
        }else{
            result.result = "Fail";
        }

    }

}
