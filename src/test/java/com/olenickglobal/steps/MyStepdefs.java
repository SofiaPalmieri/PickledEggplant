package com.olenickglobal.steps;

import com.olenickglobal.elements.ImageElement;
import com.olenickglobal.elements.ScreenElement;
import com.olenickglobal.elements.TextElement;
import com.olenickglobal.entities.SUT;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.InteractionFailedException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;

import java.awt.RenderingHints;

public class MyStepdefs implements ConcurrentEventListener {
    private final ScreenElement mainParent;
    private final SUT sut;

    public MyStepdefs(SUT sut) {
        this.sut = sut;
        mainParent = sut.selectMainParentBoundariesElement();
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
        application.click(5);
    }

    @When("search for {string}")
    public void searchForApplication(String appName) throws ElementNotFoundException, InteractionFailedException, FindFailed, InterruptedException {
        sut.typeText(appName);
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
    }

    @When("I open a {string} from iManage")
    public void openADocumentFromIManage(String document) throws InterruptedException, FindFailed {
        ImageElement iManageSearchField = new ImageElement(mainParent, "iManageSearchField.png");
        iManageSearchField.click(15);
        sut.sleep(2);
        sut.typeText(document);
        sut.typeText(Keys.ENTER);
        TextElement documentName = new TextElement(mainParent, document + ".docx");
        documentName.doubleClick(15);

    }

    @When("I open iManage")
    public void openImanage() {
        ImageElement iManageWork10DesktopIcon = new ImageElement(mainParent, "iManageWork10DesktopIcon.png");
        iManageWork10DesktopIcon.doubleClick(15);
        ImageElement ukButton = new ImageElement(mainParent, "ukButton.png");
        ukButton.click(15);
    }

    @And("{string} is checked out")
    public void documentIsCheckedOut(String document) {
        ImageElement wordMinimizeButton = new ImageElement(mainParent, "wordMinimizeButton.png");
        wordMinimizeButton.click(15);
        ImageElement iManageCheckedOutIcon = new ImageElement(mainParent, "iManageCheckedOutIcon.png");
        iManageCheckedOutIcon.waitFor(15);
        ImageElement wordTaskbarIcon = new ImageElement(mainParent, "wordTaskbarIcon.png");
        wordTaskbarIcon.click(15);
    }

    @And("Close word")
    public void closeWord() {
        ImageElement wordCloseButton = new ImageElement(mainParent, "wordCloseButton.png");
        wordCloseButton.click(15);
    }

    @Then("{string} is checked in")
    public void documentIsCheckedIn() {
        ImageElement edgeRefreshButton = new ImageElement(mainParent, "edgeRefreshButton.png");
        edgeRefreshButton.click(15);
        ImageElement iManageCheckedInDocument = new ImageElement(mainParent, "iManageCheckedInDocument.png");
        iManageCheckedInDocument.waitFor(15);
        ImageElement edgeCloseButton = new ImageElement(mainParent, "edgeCloseButton.png");
        edgeCloseButton.click(15);
    }
}
