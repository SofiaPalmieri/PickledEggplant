package com.olenickglobal.formatting;

import com.olenickglobal.elements.events.*;

public interface EventFormatter {

    String logBeforeCapture(Event<?, ?> event);

    String logAfterCapture(Event<?, ?> event);

    String logCaptureError(Event<?, ?> event);

    String logBeforeClick(Event<?, ?> event);

    String logAfterClick(Event<?, ?> event);

    String logClickError(Event<?, ?> event);

    String logBeforeDragDrop(Event<?, ?> event);

    String logAfterDragDrop(Event<?, ?> event);

    String logDragDropError(Event<?, ?> event);

    String logBeforeDragFrom(Event<?, ?> event);

    String logAfterDragFrom(Event<?, ?> event);

    String logDragFromError(Event<?, ?> event);

    String logBeforeDragTo(Event<?, ?> event);

    String logAfterDragTo(Event<?, ?> event);

    String logDragToError(Event<?, ?> event);

    String logBeforeHover(Event<?, ?> event);

    String logAfterHover(Event<?, ?> event);

    String logHoverError(Event<?, ?> event);

    String logBeforeInteractRectCreate(Event<?, ?> event);

    String logAfterInteractRectCreate(Event<?, ?> event);

    String logInteractRectCreateError(Event<?, ?> event);

    String logBeforeInteractRectSelect(Event<?, ?> event);

    String logAfterInteractRectSelect(Event<?, ?> event);

    String logInteractRectSelectError(Event<?, ?> event);

    String logBeforeLocating(Event<?, ?> event);

    String logAfterLocating(Event<?, ?> event);

    String logLocatingError(Event<?, ?> event);

    String logBeforeOCR(Event<?, ?> event);

    String logAfterOCR(Event<?, ?> event);

    String logOCRError(Event<?, ?> event);

    String logBeforeSaveScreenshot(Event<?, ?> event);

    String logAfterSaveScreenshot(Event<?, ?> event);

    String logSaveScreenshotError(Event<?, ?> event);

    String logBeforeSelectMainBounds(Event<?, ?> event);

    String logAfterSelectMainBounds(Event<?, ?> event);

    String logSelectMainBoundsError(Event<?, ?> event);

    String logBeforeTyping(Event<?, ?> event);

    String logAfterTyping(Event<?, ?> event);

    String logTypingError(Event<?, ?> event);

    String logTestCaseStarted(Event<?, ?> event);

    String logTestRunStarted(Event<?, ?> event);

    String logTestCaseFinished(Event<?, ?> event);

    String logTestStepStarted(Event<?, ?> event);

    String logTestStepFinished(Event<?, ?> event);

    String logTestRunFinished(Event<?, ?> event);
}

