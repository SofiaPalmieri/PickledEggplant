package formatting;

import com.olenickglobal.elements.events.*;


public class EventFormatter {

    private final ElementFormatter elementFormatter = new ElementFormatter();

    private <T> T getData(Object data) throws RuntimeException{
        try {
            @SuppressWarnings("unchecked")
            T result = (T) data;
            return result;
        } catch (ClassCastException e) {
            // Handle the exception, e.g., log it or return a default value
            System.out.println("Warning: ClassCastException occurred.");
            throw e;
        }
    }



    public String logBeforeCapture(Event<?,?> event) {
        return "Attempting capture";
    }


    public String logAfterCapture(Event<?,?> event) {
        return "Performed capture";
    }

    public String logCaptureError(Event<?,?> event) {
        CaptureData data = this.getData(event.data());
        return String.format("Exception occurred while capturing over %s: %s ", data.element().formatBy(this.elementFormatter),event.error().toString());
    }

    public String logBeforeClick(Event<?,?> event) {
        ClickData data = this.getData(event.data());
        return "Attempting click...";
    }

    public String logAfterClick(Event<?,?> event) {
        ClickData data = this.getData(event.data());
        return String.format("Clicked %s",data.element().formatBy(this.elementFormatter));
    }


    public String logClickError(Event<?,?> event) {
        ClickData data = this.getData(event.data());
        return String.format("Error while clicking %s: %s", data.element().formatBy(this.elementFormatter),event.error().getMessage());
    }

    public String logBeforeDragDrop(Event<?,?> event) {
        return "Attempting drag and drop...";
    }

    public String logAfterDragDrop(Event<?,?> event) {
        DragData data = this.getData(event.data());
        return String.format("Dragged from %s to %s",data.origin().formatBy(this.elementFormatter),data.destination().formatBy(this.elementFormatter));
    }

    public String logDragDropError(Event<?,?> event) {
        DragData data = this.getData(event.data());
        return String.format("Error while dragging from %s to %s: %s",data.origin().formatBy(this.elementFormatter),data.destination().formatBy(this.elementFormatter), event.error().getMessage() );
    }

    public String logBeforeDragFrom(Event<?,?> event) {
            return "Starting drag...";
    }

    public String logAfterDragFrom(Event<?,?> event) {
        DragData data = this.getData(event.data());
        return String.format("Started drag at %s", data.origin().formatBy(this.elementFormatter));
    }

    public String logDragFromError(Event<?,?> event) {
        DragData data = this.getData(event.data());
        return String.format("Error while starting drag from %s: %s", data.origin().formatBy(this.elementFormatter), event.error().getMessage());
    }

    public String logBeforeDragTo(Event<?,?> event) {
        DragData data = this.getData(event.data());
        return String.format("Dragging to %s", data.destination().formatBy(this.elementFormatter));
    }

    public String logAfterDragTo(Event<?,?> event) {
        DragData data = this.getData(event.data());
        return String.format("Dragged to %s", data.destination().formatBy(this.elementFormatter));
    }

    public String logDragToError(Event<?,?> event) {
        DragData data = this.getData(event.data());
        return String.format("Error while dragging to %s", data.destination().formatBy(this.elementFormatter));
    }

    public String logBeforeHover(Event<?,?> event) {
        return "Hover from...";
    }

    public String logAfterHover(Event<?,?> event) {
        HoverData data = this.getData(event.data());
        return String.format("Hovered to %s",data.element().formatBy(this.elementFormatter));
    }

    public String logHoverError(Event<?,?> event) {
        HoverData data = this.getData(event.data());
        return String.format("Error while hovering to %s: %s", data.element().formatBy(this.elementFormatter),event.error().getMessage());
    }

    public String logBeforeInteractRectCreate(Event<?,?> event) {
        return "Creating reactangle...";
    }

    public String logAfterInteractRectCreate(Event<?,?> event) {
        InteractiveRectCreateData data = this.getData(event.data());
        return String.format("Created interactive rectangle: %s ", this.elementFormatter.formatRectangle(data.rectangle()));
    }

    public String logInteractRectCreateError(Event<?,?> event) {
        return String.format("Error while creating interactive rectangle: %s", event.error());
    }

    public String logBeforeInteractRectSelect(Event<?,?> event) {
        return "Selecting interactive rectangle...";
    }

    public String logAfterInteractRectSelect(Event<?,?> event) {
        InteractiveRectSelectData data = this.getData(event.data());
        return String.format("Selected interactive rectangle %s",this.elementFormatter.formatRectangle(data.rectangle()));
    }

    public String logInteractRectSelectError(Event<?,?> event) {
        return String.format("Error while selecting interactive rectangle: %s", event.error().getMessage());
    }

    public String logBeforeLocating(Event<?,?> event) {
        return "Locating element...";
    }

    public String logAfterLocating(Event<?,?> event) {
        LocatingData<?> data = this.getData(event.data());
        return String.format("Located %s", data.element().formatBy(this.elementFormatter));
    }

    public String logLocatingError(Event<?,?> event) {
        return String.format("Error while locating: %s",event.error().getMessage());
    }

    public String logBeforeOCR(Event<?,?> event) {
        OCRData data = this.getData(event.data());
        return String.format("Starting OCR to find %s over rectangle %s",data.text(),this.elementFormatter.formatRectangle(data.rectangle()));
    }

    public String logAfterOCR(Event<?,?> event) {
        OCRData data = this.getData(event.data());
        return String.format("Found %s", data.text());
    }

    public String logOCRError(Event<?,?> event) {
        OCRData data = this.getData(event.data());
        return String.format("Error while performing OCR to find %s: %s", data.text(),event.error().getMessage());
    }

    public String logBeforeSaveScreenshot(Event<?,?> event) {
        return "Saving screenshot...";
    }

    public String logAfterSaveScreenshot(Event<?,?> event) {
        SaveScreenshotData data = this.getData(event.data());
        return String.format("Saved screenshot: %s",data.path());
    }

    public String logSaveScreenshotError(Event<?,?> event) {
        return String.format("Error while saving screenshot: %s", event.error().getMessage());
    }

    public String logBeforeSelectMainBounds(Event<?,?> event) {
        return "Selecting main bounds";
    }

    public String logAfterSelectMainBounds(Event<?,?> event) {
        return "Selected main bounds";
    }

    public String logSelectMainBoundsError(Event<?,?> event) {
        return String.format("Error while selecting main bounds: %s",event.error().getMessage());
    }

    public String logBeforeTyping(Event<?,?> event) {
        TypingData data = this.getData(event.data());
        return String.format("Typing %s...",data.text());
    }

    public String logAfterTyping(Event<?,?> event) {
        TypingData data = this.getData(event.data());
        return String.format("Typed %s", data.text());
    }

    public String logTypingError(Event<?,?> event) {
        TypingData data = this.getData(event.data());
        return String.format("Error while typing %s: %s", data.text(),event.error().getMessage());
    }

    public String logTestCaseStarted(Event<?,?> event) {
        TestCaseStartedData data = this.getData(event.data());
        return String.format("Test case started: %s",data.event().getTestCase().getName());
    }

    public String logTestRunStarted(Event<?,?> event) {
        return "Test run started...";
    }

    public String logTestCaseFinished(Event<?,?> event) {
        TestCaseStartedData data = this.getData(event.data());
        return String.format("Test case finished: %s",data.event().getTestCase().getName());
    }

    public String logTestStepStarted(Event<?,?> event) {
        TestStepStartedData data = this.getData(event.data());
        return String.format("Step started: %s", data.event().getTestStep().getCodeLocation());
    }

    public String logTestStepFinished(Event<?,?> event) {
        TestStepFinishedData data = this.getData(event.data());
        return String.format("Step finished: %s", data.event().getTestStep().getCodeLocation());
    }

    public String logTestRunFinished(Event<?,?> event) {
        return "Test run finished...";
    }


}

