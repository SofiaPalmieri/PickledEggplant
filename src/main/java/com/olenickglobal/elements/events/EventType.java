package com.olenickglobal.elements.events;

public enum EventType {
    BEFORE_CAPTURE(CaptureData.class),
    AFTER_CAPTURE(CaptureData.class),
    CAPTURE_ERROR(CaptureData.class),
    BEFORE_CLICK(ClickData.class),
    AFTER_CLICK(ClickData.class),
    CLICK_ERROR(ClickData.class),
    BEFORE_DRAG_DROP(DragData.class),
    AFTER_DRAG_DROP(DragData.class),
    DRAG_DROP_ERROR(DragData.class),
    BEFORE_DRAG_FROM(DragData.class),
    AFTER_DRAG_FROM(DragData.class),
    DRAG_FROM_ERROR(DragData.class),
    BEFORE_DRAG_TO(DragData.class),
    AFTER_DRAG_TO(DragData.class),
    DRAG_TO_ERROR(DragData.class),
    BEFORE_HOVER(HoverData.class),
    AFTER_HOVER(HoverData.class),
    HOVER_ERROR(HoverData.class),
    BEFORE_INTERACT_RECT_CREATE(InteractiveRectCreateData.class),
    AFTER_INTERACT_RECT_CREATE(InteractiveRectCreateData.class),
    INTERACT_RECT_CREATE_ERROR(InteractiveRectCreateData.class),
    BEFORE_INTERACT_RECT_SELECT(InteractiveRectSelectData.class),
    AFTER_INTERACT_RECT_SELECT(InteractiveRectSelectData.class),
    INTERACT_RECT_SELECT_ERROR(InteractiveRectSelectData.class),
    BEFORE_LOCATING(LocatingData.class),
    AFTER_LOCATING(LocatingData.class),
    LOCATING_ERROR(LocatingData.class),
    BEFORE_OCR(OCRData.class),
    AFTER_OCR(OCRData.class),
    OCR_ERROR(OCRData.class),
    BEFORE_SAVE_SCREENSHOT(SaveScreenshotData.class),
    AFTER_SAVE_SCREENSHOT(SaveScreenshotData.class),
    SAVE_SCREENSHOT_ERROR(SaveScreenshotData.class),
    BEFORE_SELECT_MAIN_BOUNDS(SelectMainBoundsData.class),
    AFTER_SELECT_MAIN_BOUNDS(SelectMainBoundsData.class),
    SELECT_MAIN_BOUNDS_ERROR(SelectMainBoundsData.class),
    BEFORE_TYPING(TypingData.class),
    AFTER_TYPING(TypingData.class),
    TYPING_ERROR(TypingData.class);

    public final Class<?> dataClass;

    EventType(Class<?> dataClass) {
        this.dataClass = dataClass;
    }
}
