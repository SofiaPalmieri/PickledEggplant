package com.olenickglobal.steps;


import com.olenickglobal.Utils.FindFailedHandler;
import com.olenickglobal.Utils.ImageElement;
import com.olenickglobal.Utils.SUT;
import com.olenickglobal.Utils.TextElement;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import org.sikuli.script.FindFailed;

import java.awt.*;
import java.io.IOException;

public class MyStepdefs implements ConcurrentEventListener {

        @When("I click windows start button")
        public void clickWindowsStartButton() throws  FindFailed {
            ImageElement clickWindowsStartButton = new ImageElement( "windowsStartButton.png");
            clickWindowsStartButton.click(20);
        }

        @When("search for {string}")
        public void searchForApplication(String appName) throws FindFailed {
            SUT sut = new SUT();
            sut.typeText(appName);
        }

        @When("launch {string}")
        public void launchApplication(String appName) throws  FindFailed {
            ImageElement application = new ImageElement( appName+"Icon.png");
            application.click(15);
        }

        @Then("{string} launches")
        public void applicationLaunches(String appName) throws FindFailed {
            ImageElement application = new ImageElement(appName+"TaskBarIcon.png");
            application.waitFor(10);
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {

    }


}
