package com.olenickglobal.Utils;

import org.sikuli.script.FindFailed;

import java.awt.*;
import java.io.IOException;

public class TextStep extends Step{

    String text;

    ActionTypeText actionType;


    public StepResult executeAction() throws FindFailedHandler, IOException, AWTException {
        StepResult stepResult = new StepResult();
        Text textToFind = new Text(text);

        try{
            stepResult.image = textToFind.executeAction(actionType);
            stepResult.result = "Pass";
            stepResult.expected = expectedResult;

        }catch(FindFailedHandler | FindFailed e){
            PickledRobot robot = new PickledRobot();
            robot.saveScreenshot();
            stepResult.result = "Fail";
            stepResult.expected = expectedResult;
            throw new FindFailedHandler(textToFind.screen, textToFind.patternHandler);
        } catch (IOException e) {
            PickledRobot robot = new PickledRobot();
            robot.saveScreenshot();
            stepResult.result = "Fail";
            stepResult.expected = expectedResult;
            throw new RuntimeException(e);
        }
        return stepResult;
    }
    public TextStep(String name,String expectedResult,int timeout, String text, ActionTypeText actionType){
        this.name = name;
        this.expectedResult = expectedResult;
        this.timeout = timeout;
        this.text = text;
        this.actionType = actionType;
    }
}
