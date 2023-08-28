package com.olenickglobal.steps;

import com.olenickglobal.Utils.ActionTypeImages;
import com.olenickglobal.Utils.ActionTypeText;
import com.olenickglobal.Utils.FindFailedHandler;
import com.olenickglobal.Utils.ImageStep;
import com.olenickglobal.Utils.TextStep;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.awt.*;
import java.io.IOException;

public class MyStepdefs {

        @When("I click windows start button")
        public void clickWindowsStartButton() throws FindFailedHandler, IOException, AWTException {
            ImageStep clickWindowsStartButton = new ImageStep("Click windows start button", "Windows start menu displays", 15, "windowsStartButton.png", ActionTypeImages.CLICK);
            clickWindowsStartButton.executeAction();
        }

        @When("search for (.*)")
        public void searchForApplication(String appName) throws FindFailedHandler, IOException, AWTException {
            TextStep application = new TextStep("Search for " + appName, appName+" is listed in Windows start menu", 15, appName, ActionTypeText.TYPE_TEXT);
            application.executeAction();
        }

        @When("launch (.*)")
        public void launchApplication(String appName) throws FindFailedHandler, IOException, AWTException {
            ImageStep application = new ImageStep("Launch "+ appName , appName+" launches", 15, appName+"Icon.png", ActionTypeImages.CLICK);
            application.executeAction();
        }

        @Then("(.*) launches")
        public void applicationLaunches(String appName) throws FindFailedHandler, IOException, AWTException {
            ImageStep application = new ImageStep("Verify "+appName+ "launches", appName+" launches successfully", 15, appName+"TaskBarIcon.png", ActionTypeImages.MOVE_TO);
            application.executeAction();
    }

}
