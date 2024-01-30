package com.olenickglobal.formatting;

import com.olenickglobal.elements.events.*;


public class PickledEventFormatter implements EventFormatter {

    private final ElementFormatter elementFormatter = new ElementFormatter();


    public String formatBeforeCapture(Event<?,?> event) {
        return "Attempting capture";
    }


    public String formatAfterCapture(Event<?,?> event) {
        return "Performed capture";
    }

    public String formatCaptureError(Event<?,?> event) {
        CaptureData data = (CaptureData) event.data();
        return String.format("Exception occurred while capturing over %s: %s ", data.element().formatBy(this.elementFormatter),event.error().toString());
    }

    public String formatBeforeClick(Event<?,?> event) {
        return "Attempting click...";
    }

    public String formatAfterClick(Event<?,?> event) {
        ClickData data = (ClickData) event.data();
        return String.format("Clicked %s",data.element().formatBy(this.elementFormatter));
    }


    public String formatClickError(Event<?,?> event) {
        ClickData data = (ClickData) event.data();
        return String.format("Error while clicking %s: %s", data.element().formatBy(this.elementFormatter),event.error().getMessage());
    }

    public String formatBeforeDragDrop(Event<?,?> event) {
        return "Attempting drag and drop...";
    }

    public String formatAfterDragDrop(Event<?,?> event) {
        DragData data = (DragData) event.data();
        return String.format("Dragged from %s to %s",data.origin().formatBy(this.elementFormatter),data.destination().formatBy(this.elementFormatter));
    }

    public String formatDragDropError(Event<?,?> event) {
        DragData data = (DragData) event.data();
        return String.format("Error while dragging from %s to %s: %s",data.origin().formatBy(this.elementFormatter),data.destination().formatBy(this.elementFormatter), event.error().getMessage() );
    }

    public String formatBeforeDragFrom(Event<?,?> event) {
        return "Starting drag...";
    }

    public String formatAfterDragFrom(Event<?,?> event) {
        DragData data = (DragData) event.data();
        return String.format("Started drag at %s", data.origin().formatBy(this.elementFormatter));
    }

    public String formatDragFromError(Event<?,?> event) {
        DragData data = (DragData) event.data();
        return String.format("Error while starting drag from %s: %s", data.origin().formatBy(this.elementFormatter), event.error().getMessage());
    }

    public String formatBeforeDragTo(Event<?,?> event) {
        DragData data = (DragData) event.data();
        return String.format("Dragging to %s", data.destination().formatBy(this.elementFormatter));
    }

    public String formatAfterDragTo(Event<?,?> event) {
        DragData data = (DragData) event.data();
        return String.format("Dragged to %s", data.destination().formatBy(this.elementFormatter));
    }

    public String formatDragToError(Event<?,?> event) {
        DragData data = (DragData) event.data();
        return String.format("Error while dragging to %s", data.destination().formatBy(this.elementFormatter));
    }

    public String formatBeforeHover(Event<?,?> event) {
        return "Hover from...";
    }

    public String formatAfterHover(Event<?,?> event) {
        HoverData data = (HoverData) event.data();
        return String.format("Hovered to %s",data.element().formatBy(this.elementFormatter));
    }

    public String formatHoverError(Event<?,?> event) {
        HoverData data = (HoverData) event.data();
        return String.format("Error while hovering to %s: %s", data.element().formatBy(this.elementFormatter),event.error().getMessage());
    }

    public String formatBeforeInteractRectCreate(Event<?,?> event) {
        return "Creating rectangle...";
    }

    public String formatAfterInteractRectCreate(Event<?,?> event ) {
        InteractiveRectCreateData data = (InteractiveRectCreateData) event.data();
        return String.format("Created interactive rectangle: %s ", this.elementFormatter.formatRectangle(data.rectangle()));
    }

    public String formatInteractRectCreateError(Event<?,?> event) {
        return String.format("Error while creating interactive rectangle: %s", event.error());
    }

    public String formatBeforeInteractRectSelect(Event<?,?> event) {
        return "Selecting interactive rectangle...";
    }

    public String formatAfterInteractRectSelect(Event<?,?> event) {
        InteractiveRectSelectData data = (InteractiveRectSelectData) event.data();
        return String.format("Selected interactive rectangle %s",this.elementFormatter.formatRectangle(data.rectangle()));
    }

    public String formatInteractRectSelectError(Event<?,?> event) {
        return String.format("Error while selecting interactive rectangle: %s", event.error().getMessage());
    }

    public String formatBeforeLocating(Event<?,?> event) {
        return "Locating element...";
    }

    public String formatAfterLocating(Event<?,?> event) {
        LocatingData<?> data = (LocatingData<?>) event.data();
        return String.format("Located %s", data.element().formatBy(this.elementFormatter));
    }

    public String formatLocatingError(Event<?,?> event) {
        return String.format("Error while locating: %s",event.error().getMessage());
    }

    public String formatBeforeOCR(Event<?,?> event) {
        OCRData data = (OCRData) event.data();
        return String.format("Starting OCR to find %s over rectangle %s",data.text(),this.elementFormatter.formatRectangle(data.rectangle()));
    }

    public String formatAfterOCR(Event<?,?> event) {
        OCRData data = (OCRData) event.data();
        return String.format("Found %s", data.text());
    }

    public String formatOCRError(Event<?,?> event) {
        OCRData data = (OCRData) event.data();
        return String.format("Error while performing OCR to find %s: %s", data.text(),event.error().getMessage());
    }

    public String formatBeforeSaveScreenshot(Event<?,?> event) {
        return "Saving screenshot...";
    }

    public String formatAfterSaveScreenshot(Event<?,?> event) {
        SaveScreenshotData data = (SaveScreenshotData) event.data();
        return String.format("Saved screenshot: %s",data.path());
    }

    public String formatSaveScreenshotError(Event<?,?> event) {
        return String.format("Error while saving screenshot: %s", event.error().getMessage());
    }

    public String formatBeforeSelectMainBounds(Event<?,?> event) {
        return "Selecting main bounds";
    }

    public String formatAfterSelectMainBounds(Event<?,?> event) {
        return "Selected main bounds";
    }

    public String formatSelectMainBoundsError(Event<?,?> event) {
        return String.format("Error while selecting main bounds: %s",event.error().getMessage());
    }

    public String formatBeforeTyping(Event<?,?> event) {
        TypingData data = (TypingData) event.data();
        return String.format("Typing %s...",data.text());
    }

    public String formatAfterTyping(Event<?,?> event) {
        TypingData data = (TypingData) event.data();
        return String.format("Typed %s", data.text());
    }

    public String formatTypingError(Event<?,?> event) {
        TypingData data = (TypingData) event.data();
        return String.format("Error while typing %s: %s", data.text(),event.error().getMessage());
    }

    public String formatTestCaseStarted(Event<?,?> event) {
        TestCaseStartedData data = (TestCaseStartedData) event.data();
        return String.format("Test case started: %s",data.event().getTestCase().getName());
    }

    public String formatTestRunStarted(Event<?,?> event) {
        return "Test run started...";
    }

    public String formatTestCaseFinished(Event<?,?> event) {
        TestCaseStartedData data = (TestCaseStartedData) event.data();
        return String.format("Test case finished: %s",data.event().getTestCase().getName());
    }

    public String formatTestStepStarted(Event<?,?> event) {
        TestStepStartedData data = (TestStepStartedData) event.data();
        return String.format("Step started: %s", data.event().getTestStep().getCodeLocation());
    }

    public String formatTestStepFinished(Event<?,?> event) {
        TestStepFinishedData data = (TestStepFinishedData) event.data();
        return String.format("Step finished: %s", data.event().getTestStep().getCodeLocation());
    }

    public String formatTestRunFinished(Event<?,?> event) {
        return "Test run finished...";
    }


}

