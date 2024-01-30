package com.olenickglobal.elements.events;

import com.olenickglobal.formatting.EventFormatter;


import java.util.function.BiFunction;


public enum EventType{
    BEFORE_CAPTURE(CaptureData.class, EventFormatter::formatBeforeCapture),
    AFTER_CAPTURE(CaptureData.class, EventFormatter::formatAfterCapture),
    CAPTURE_ERROR(CaptureData.class, EventFormatter::formatCaptureError),
    BEFORE_CLICK(ClickData.class, EventFormatter::formatBeforeClick),
    AFTER_CLICK(ClickData.class, EventFormatter::formatAfterClick),
    CLICK_ERROR(ClickData.class, EventFormatter::formatClickError),
    BEFORE_DRAG_DROP(DragData.class, EventFormatter::formatBeforeDragDrop),
    AFTER_DRAG_DROP(DragData.class, EventFormatter::formatAfterDragDrop),
    DRAG_DROP_ERROR(DragData.class, EventFormatter::formatDragDropError),
    BEFORE_DRAG_FROM(DragData.class, EventFormatter::formatBeforeDragFrom),
    AFTER_DRAG_FROM(DragData.class, EventFormatter::formatAfterDragFrom),
    DRAG_FROM_ERROR(DragData.class, EventFormatter::formatDragFromError),
    BEFORE_DRAG_TO(DragData.class, EventFormatter::formatBeforeDragTo),
    AFTER_DRAG_TO(DragData.class, EventFormatter::formatAfterDragTo),
    DRAG_TO_ERROR(DragData.class, EventFormatter::formatDragToError),
    BEFORE_HOVER(HoverData.class, EventFormatter::formatBeforeHover),
    AFTER_HOVER(HoverData.class, EventFormatter::formatAfterHover),
    HOVER_ERROR(HoverData.class, EventFormatter::formatHoverError),
    BEFORE_INTERACT_RECT_CREATE(InteractiveRectCreateData.class, EventFormatter::formatBeforeInteractRectCreate),
    AFTER_INTERACT_RECT_CREATE(InteractiveRectCreateData.class, EventFormatter::formatAfterInteractRectCreate),
    INTERACT_RECT_CREATE_ERROR(InteractiveRectCreateData.class, EventFormatter::formatInteractRectCreateError),
    BEFORE_INTERACT_RECT_SELECT(InteractiveRectSelectData.class, EventFormatter::formatBeforeInteractRectSelect),
    AFTER_INTERACT_RECT_SELECT(InteractiveRectSelectData.class, EventFormatter::formatAfterInteractRectSelect),
    INTERACT_RECT_SELECT_ERROR(InteractiveRectSelectData.class, EventFormatter::formatInteractRectSelectError),
    BEFORE_LOCATING(LocatingData.class, EventFormatter::formatBeforeLocating),
    AFTER_LOCATING(LocatingData.class, EventFormatter::formatAfterLocating),
    LOCATING_ERROR(LocatingData.class, EventFormatter::formatLocatingError),
    BEFORE_OCR(OCRData.class, EventFormatter::formatBeforeOCR),
    AFTER_OCR(OCRData.class, EventFormatter::formatAfterOCR),
    OCR_ERROR(OCRData.class, EventFormatter::formatOCRError),
    BEFORE_SAVE_SCREENSHOT(SaveScreenshotData.class, EventFormatter::formatBeforeSaveScreenshot),
    AFTER_SAVE_SCREENSHOT(SaveScreenshotData.class, EventFormatter::formatAfterSaveScreenshot),
    SAVE_SCREENSHOT_ERROR(SaveScreenshotData.class, EventFormatter::formatSaveScreenshotError),
    BEFORE_SELECT_MAIN_BOUNDS(SelectMainBoundsData.class, EventFormatter::formatBeforeSelectMainBounds),
    AFTER_SELECT_MAIN_BOUNDS(SelectMainBoundsData.class, EventFormatter::formatAfterSelectMainBounds),
    SELECT_MAIN_BOUNDS_ERROR(SelectMainBoundsData.class, EventFormatter::formatSelectMainBoundsError),
    BEFORE_TYPING(TypingData.class, EventFormatter::formatBeforeTyping),
    AFTER_TYPING(TypingData.class, EventFormatter::formatAfterTyping),
    TYPING_ERROR(TypingData.class, EventFormatter::formatTypingError),
    TEST_CASE_STARTED(TestCaseStartedData.class, EventFormatter::formatTestCaseStarted),
    TEST_RUN_STARTED(TestRunStartedData.class, EventFormatter::formatTestRunStarted),
    TEST_CASE_FINISHED(TestCaseFinishedData.class, EventFormatter::formatTestCaseFinished),
    TEST_STEP_STARTED(TestStepStartedData.class, EventFormatter::formatTestStepStarted),
    TEST_STEP_FINISHED(TestStepFinishedData.class, EventFormatter::formatTestStepFinished),
    TEST_RUN_FINISHED(TestRunFinishedData.class, EventFormatter::formatTestRunFinished);

    public final Class<?> dataClass;

    public final BiFunction<EventFormatter,Event<?,?>,String> writeLogFunction;

    EventType(Class<?> dataClass,BiFunction<EventFormatter,Event<?,?>,String>   writeLogFunction) {
        this.dataClass = dataClass;
        this.writeLogFunction = writeLogFunction;
    }


    public String writeLog(EventFormatter eventFormatter, Event<?,?> e) {
        return this.writeLogFunction.apply(eventFormatter,e);
    }
}
