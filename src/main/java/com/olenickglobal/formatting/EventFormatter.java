package com.olenickglobal.formatting;

import com.olenickglobal.elements.events.*;

public interface EventFormatter {


    String formatBeforeCapture(Event<?,?> event);

    String formatAfterCapture(Event<?,?> event);

    String formatCaptureError(Event<?,?> event);

    String formatBeforeClick(Event<?, ?> event);

    String formatAfterClick(Event<?, ?> event);

    String formatClickError(Event<?, ?> event);

    String formatBeforeDragDrop(Event<?, ?> event);

    String formatAfterDragDrop(Event<?, ?> event);

    String formatDragDropError(Event<?, ?> event);

    String formatBeforeDragFrom(Event<?, ?> event);

    String formatAfterDragFrom(Event<?, ?> event);

    String formatDragFromError(Event<?, ?> event);

    String formatBeforeDragTo(Event<?, ?> event);

    String formatAfterDragTo(Event<?, ?> event);

    String formatDragToError(Event<?, ?> event);

    String formatBeforeHover(Event<?, ?> event);

    String formatAfterHover(Event<?, ?> event);

    String formatHoverError(Event<?, ?> event);

    String formatBeforeInteractRectCreate(Event<?, ?> event);

    String formatAfterInteractRectCreate(Event<?, ?> event);

    String formatInteractRectCreateError(Event<?, ?> event);

    String formatBeforeInteractRectSelect(Event<?, ?> event);

    String formatAfterInteractRectSelect(Event<?, ?> event);

    String formatInteractRectSelectError(Event<?, ?> event);

    String formatBeforeLocating(Event<?, ?> event);

    String formatAfterLocating(Event<?, ?> event);

    String formatLocatingError(Event<?, ?> event);

    String formatBeforeOCR(Event<?, ?> event);

    String formatAfterOCR(Event<?, ?> event);

    String formatOCRError(Event<?, ?> event);

    String formatBeforeSaveScreenshot(Event<?, ?> event);

    String formatAfterSaveScreenshot(Event<?, ?> event);

    String formatSaveScreenshotError(Event<?, ?> event);

    String formatBeforeSelectMainBounds(Event<?, ?> event);

    String formatAfterSelectMainBounds(Event<?, ?> event);

    String formatSelectMainBoundsError(Event<?, ?> event);

    String formatBeforeTyping(Event<?, ?> event);

    String formatAfterTyping(Event<?, ?> event);

    String formatTypingError(Event<?, ?> event);

    String formatTestCaseStarted(Event<?, ?> event);

    String formatTestRunStarted(Event<?, ?> event);

    String formatTestCaseFinished(Event<?, ?> event);

    String formatTestStepStarted(Event<?, ?> event);

    String formatTestStepFinished(Event<?, ?> event);

    String formatTestRunFinished(Event<?, ?> event);
}

