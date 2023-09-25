package com.olenickglobal.Utils;

import org.sikuli.script.FindFailed;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class ImageStep extends Step{

    String imageName;

    ActionTypeImages actionType;



    public StepResult executeAction() throws FindFailedHandler, IOException, AWTException {
        StepResult stepResult = new StepResult();
        ScreenSection image = new ScreenSection(imageName);

        try{
            image.executeAction(timeout, actionType);
            stepResult.result = "Pass";
            stepResult.expected = expectedResult;

        }catch(FindFailedHandler | FindFailed e){
            PickledRobot robot = new PickledRobot(this);
            //robot.saveScreenshot();
            stepResult.result = "Fail";
            stepResult.expected = expectedResult;
            throw new FindFailedHandler(image.screen, image.patternHandler);
        } catch (IOException e) {
            PickledRobot robot = new PickledRobot(this);
            //robot.saveScreenshot();
            stepResult.result = "Fail";
            stepResult.expected = expectedResult;
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return stepResult;
    }

    public ImageStep(String name,String expectedResult,int timeout, String imageName, ActionTypeImages actionType){
        this.name = name;
        this.expectedResult = expectedResult;
        this.timeout = timeout;
        this.imageName = imageName;
        this.actionType = actionType;
    }
    }





