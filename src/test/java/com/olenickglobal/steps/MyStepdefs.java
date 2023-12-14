package com.olenickglobal.steps;

import com.olenickglobal.elements.ImageElement;
import com.olenickglobal.elements.ScreenElement;
import com.olenickglobal.entities.SUT;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.InteractionFailedException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;

public class MyStepdefs implements ConcurrentEventListener {
    private final ScreenElement mainParent;
    private final SUT sut;

    public MyStepdefs(SUT sut) {
        this.sut = sut;
        mainParent = sut.getMainParentElement();
    }

    @Then("{string} launches")
    public void applicationLaunches(String appName) throws ElementNotFoundException, InteractionFailedException {
        ImageElement application = new ImageElement(mainParent, appName + "Taskbaricon");
        application.waitFor(10);
    }

    @When("I click windows start button")
    public void clickWindowsStartButton() throws ElementNotFoundException, InteractionFailedException {
        ImageElement clickWindowsStartButton = new ImageElement(mainParent, "windowsStartButton.png");
        clickWindowsStartButton.click(2);
    }

    @When("launch {string}")
    public void launchApplication(String appName) throws ElementNotFoundException, InteractionFailedException {
        ImageElement application = new ImageElement(mainParent, appName + "Icon");
        application.waitFor(15);
        application.click(15);
    }

    @When("search for {string}")
    public void searchForApplication(String appName) throws ElementNotFoundException, InteractionFailedException {
        sut.typeText(appName);
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
    }
}
