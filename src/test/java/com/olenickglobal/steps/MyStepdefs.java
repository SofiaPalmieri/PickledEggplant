package com.olenickglobal.steps;

import com.olenickglobal.elements.ImageElement;
import com.olenickglobal.entities.SUT;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import org.sikuli.script.FindFailed;

public class MyStepdefs implements ConcurrentEventListener {
    @Then("{string} launches")
    public void applicationLaunches(String appName) throws FindFailed {
        ImageElement application = new ImageElement(appName + "Taskbaricon");
        application.waitFor(10);
    }

    @When("I click windows start button")
    public void clickWindowsStartButton() throws FindFailed {
        ImageElement clickWindowsStartButton = new ImageElement("windowsStartButton.png");
        clickWindowsStartButton.click(20);
    }

    @When("launch {string}")
    public void launchApplication(String appName) throws FindFailed, InterruptedException {
        ImageElement application = new ImageElement(appName + "Icon");
        application.waitFor(15);
        application.click(15);
    }

    @When("search for {string}")
    public void searchForApplication(String appName) throws FindFailed {
        SUT sut = new SUT();
        sut.typeText(appName);
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
    }
}
