package com.olenickglobal.elements.events;

import formatting.EventFormatter;


import java.util.function.BiFunction;


public enum EventType{
    BEFORE_CAPTURE(CaptureData.class, EventFormatter::logBeforeCapture),
    AFTER_CAPTURE(CaptureData.class, EventFormatter::logAfterCapture),
    CAPTURE_ERROR(CaptureData.class, EventFormatter::logCaptureError),
    BEFORE_CLICK(ClickData.class, EventFormatter::logBeforeClick),
    AFTER_CLICK(ClickData.class, EventFormatter::logAfterClick),
    CLICK_ERROR(ClickData.class, EventFormatter::logClickError),
    BEFORE_DRAG_DROP(DragData.class, EventFormatter::logBeforeDragDrop),
    AFTER_DRAG_DROP(DragData.class, EventFormatter::logAfterDragDrop),
    DRAG_DROP_ERROR(DragData.class, EventFormatter::logDragDropError),
    BEFORE_DRAG_FROM(DragData.class, EventFormatter::logBeforeDragFrom),
    AFTER_DRAG_FROM(DragData.class, EventFormatter::logAfterDragFrom),
    DRAG_FROM_ERROR(DragData.class, EventFormatter::logDragFromError),
    BEFORE_DRAG_TO(DragData.class, EventFormatter::logBeforeDragTo),
    AFTER_DRAG_TO(DragData.class, EventFormatter::logAfterDragTo),
    DRAG_TO_ERROR(DragData.class, EventFormatter::logDragToError),
    BEFORE_HOVER(HoverData.class, EventFormatter::logBeforeHover),
    AFTER_HOVER(HoverData.class, EventFormatter::logAfterHover),
    HOVER_ERROR(HoverData.class, EventFormatter::logHoverError),
    BEFORE_INTERACT_RECT_CREATE(InteractiveRectCreateData.class, EventFormatter::logBeforeInteractRectCreate),
    AFTER_INTERACT_RECT_CREATE(InteractiveRectCreateData.class, EventFormatter::logAfterInteractRectCreate),
    INTERACT_RECT_CREATE_ERROR(InteractiveRectCreateData.class, EventFormatter::logInteractRectCreateError),
    BEFORE_INTERACT_RECT_SELECT(InteractiveRectSelectData.class, EventFormatter::logBeforeInteractRectSelect),
    AFTER_INTERACT_RECT_SELECT(InteractiveRectSelectData.class, EventFormatter::logAfterInteractRectSelect),
    INTERACT_RECT_SELECT_ERROR(InteractiveRectSelectData.class, EventFormatter::logInteractRectSelectError),
    BEFORE_LOCATING(LocatingData.class, EventFormatter::logBeforeLocating),
    AFTER_LOCATING(LocatingData.class, EventFormatter::logAfterLocating),
    LOCATING_ERROR(LocatingData.class, EventFormatter::logLocatingError),
    BEFORE_OCR(OCRData.class, EventFormatter::logBeforeOCR),
    AFTER_OCR(OCRData.class, EventFormatter::logAfterOCR),
    OCR_ERROR(OCRData.class, EventFormatter::logOCRError),
    BEFORE_SAVE_SCREENSHOT(SaveScreenshotData.class, EventFormatter::logBeforeSaveScreenshot),
    AFTER_SAVE_SCREENSHOT(SaveScreenshotData.class, EventFormatter::logAfterSaveScreenshot),
    SAVE_SCREENSHOT_ERROR(SaveScreenshotData.class, EventFormatter::logSaveScreenshotError),
    BEFORE_SELECT_MAIN_BOUNDS(SelectMainBoundsData.class, EventFormatter::logBeforeSelectMainBounds),
    AFTER_SELECT_MAIN_BOUNDS(SelectMainBoundsData.class, EventFormatter::logAfterSelectMainBounds),
    SELECT_MAIN_BOUNDS_ERROR(SelectMainBoundsData.class, EventFormatter::logSelectMainBoundsError),
    BEFORE_TYPING(TypingData.class, EventFormatter::logBeforeTyping),
    AFTER_TYPING(TypingData.class, EventFormatter::logAfterTyping),
    TYPING_ERROR(TypingData.class, EventFormatter::logTypingError),
    TEST_CASE_STARTED(TestCaseStartedData.class, EventFormatter::logTestCaseStarted),
    TEST_RUN_STARTED(TestRunStartedData.class, EventFormatter::logTestRunStarted),
    TEST_CASE_FINISHED(TestCaseFinishedData.class, EventFormatter::logTestCaseFinished),
    TEST_STEP_STARTED(TestStepStartedData.class, EventFormatter::logTestStepStarted),
    TEST_STEP_FINISHED(TestStepFinishedData.class, EventFormatter::logTestStepFinished),
    TEST_RUN_FINISHED(TestRunFinishedData.class, EventFormatter::logTestRunFinished);

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
