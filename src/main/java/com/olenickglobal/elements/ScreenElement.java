package com.olenickglobal.elements;

import com.olenickglobal.elements.events.CaptureData;
import com.olenickglobal.elements.events.ClickData;
import com.olenickglobal.elements.events.ClickData.ClickActionType;
import com.olenickglobal.elements.events.DragData;
import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.EventData;
import com.olenickglobal.elements.events.EventDataBuilder;
import com.olenickglobal.elements.events.EventEmitter;
import com.olenickglobal.elements.events.EventListener;
import com.olenickglobal.elements.events.EventType;
import com.olenickglobal.elements.events.HoverData;
import com.olenickglobal.elements.events.OCRData;
import com.olenickglobal.entities.Screen;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.ImageNotFoundException;
import com.olenickglobal.exceptions.InteractionFailedException;
import com.olenickglobal.utils.FunctionWithException;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public abstract class ScreenElement {
    protected final EventEmitter eventEmitter;
    protected Rectangle lastMatchLocation;
    protected Offset offset;
    protected ScreenElement parent;
    protected Screen screen;
    protected boolean useCachedParentLocation = true;

    public ScreenElement(Screen screen, Offset offset) {
        this(screen, null, offset);
    }

    public ScreenElement(Screen screen, ScreenElement parent, Offset offset) {
        eventEmitter = new EventEmitter();
        this.screen = screen;
        this.parent = parent == null ? screen.getSUT().getMainParentBoundariesElement() : parent;
        this.offset = offset;
    }

    /**
     * Gets a region match.
     * <p><b>IMPORTANT FOR IMPLEMENTORS:</b> This method will need to use {@link #getLastMatchLocation} and
     * {@link #setLastMatchLocation} methods appropriately.</p>
     *
     * @param timeout Search timeout in seconds.
     * @return Screen rectangle where the match was found.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    // TODO: Caching policy?
    abstract Rectangle getMatch(double timeout) throws ElementNotFoundException;

    /**
     * Add an event listener.
     *
     * @param type     Event type.
     * @param listener Event listener for the event type.
     * @return True if the listener was added, false otherwise.
     */
    public boolean addEventListener(EventType type, EventListener<?, ?> listener) {
        return eventEmitter.addEventListener(type, listener);
    }

    /**
     * Captures the element's rectangle from the screen.
     *
     * @param timeout Search timeout in seconds.
     * @return Captured buffered image.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    @SuppressWarnings("unchecked")
    public BufferedImage capture(final double timeout) throws ElementNotFoundException {
        return performAction(EventType.BEFORE_CAPTURE, EventType.AFTER_CAPTURE, EventType.CAPTURE_ERROR,
                new Class[]{ElementNotFoundException.class}, new CaptureData(timeout, this),
                (CaptureData.CaptureDataBuilder builder) -> {
                    Rectangle rectangle;
                    BufferedImage image;
                    builder.withRectangle(rectangle = getMatch(timeout)).withImage(image = screen.capture(rectangle));
                    return image;
                });
    }

    /**
     * Clicks the location of this element.
     *
     * @param timeout Search timeout in seconds.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement click(double timeout) throws ElementNotFoundException, InteractionFailedException {
        return click(timeout, 0);
    }

    /**
     * Clicks the location of this element.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement click(double timeout, int modifiers) throws ElementNotFoundException, InteractionFailedException {
        return click(timeout, modifiers, getOffset());
    }

    /**
     * Clicks the location of this element.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @param delay     Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement click(double timeout, int modifiers, double delay) throws ElementNotFoundException, InteractionFailedException {
        return click(timeout, modifiers, getOffset(), delay);
    }

    /**
     * Clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout Search timeout in seconds.
     * @param offset  Offset specification.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement click(double timeout, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        return click(timeout, 0, offset);
    }

    /**
     * Clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @param offset    Offset specification.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement click(double timeout, int modifiers, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        return click(timeout, modifiers, offset, 0.0);
    }

    /**
     * Clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout Search timeout in seconds.
     * @param offset  Offset specification.
     * @param delay   Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement click(double timeout, Offset offset, double delay) throws ElementNotFoundException, InteractionFailedException {
        return click(timeout, 0, offset, delay);
    }

    /**
     * Clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @param offset    Offset specification.
     * @param delay     Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement click(double timeout, int modifiers, Offset offset, double delay) throws ElementNotFoundException, InteractionFailedException {
        return performClickAction(ClickActionType.LEFT_CLICK, timeout, modifiers, offset, delay);
    }

    /**
     * Dispatch event to this element's listeners.
     *
     * @param event Event to be dispatched.
     * @return This element.
     */
    public ScreenElement dispatchEvent(Event<?, ?> event) {
        eventEmitter.emit(event);
        return this;
    }

    /**
     * Double-clicks the location of this element.
     *
     * @param timeout Search timeout in seconds.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement doubleClick(double timeout) throws ElementNotFoundException, InteractionFailedException {
        return doubleClick(timeout, 0);
    }

    /**
     * Double-clicks the location of this element.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement doubleClick(double timeout, int modifiers) throws ElementNotFoundException, InteractionFailedException {
        return doubleClick(timeout, modifiers, getOffset());
    }

    /**
     * Double-clicks the location of this element.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @param delay     Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement doubleClick(double timeout, int modifiers, double delay) throws ElementNotFoundException, InteractionFailedException {
        return doubleClick(timeout, modifiers, getOffset(), delay);
    }

    /**
     * Double-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout Search timeout in seconds.
     * @param offset  Offset specification.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement doubleClick(double timeout, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        return doubleClick(timeout, 0, offset);
    }

    /**
     * Double-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @param offset    Offset specification.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement doubleClick(double timeout, int modifiers, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        return doubleClick(timeout, modifiers, offset, 0.0);
    }

    /**
     * Double-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout Search timeout in seconds.
     * @param offset  Offset specification.
     * @param delay   Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement doubleClick(double timeout, Offset offset, double delay) throws ElementNotFoundException, InteractionFailedException {
        return doubleClick(timeout, 0, offset, delay);
    }

    /**
     * Double-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @param offset    Offset specification.
     * @param delay     Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement doubleClick(final double timeout, final int modifiers, final Offset offset, final double delay) throws ElementNotFoundException, InteractionFailedException {
        return performClickAction(ClickActionType.DOUBLE_CLICK, timeout, modifiers, offset, delay);
    }

    /**
     * Drags this element from its target point to the specified destination.
     *
     * @param timeout     Search timeout in seconds.
     * @param destination Point where to drag the target of this element.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement dragTo(double timeout, Point destination) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, 0, destination);
    }

    /**
     * Drags this element from its target point to the specified destination.
     *
     * @param timeout     Search timeout in seconds.
     * @param modifiers   Modifiers (see {@link InputEvent}).
     * @param destination Point where to drag the target of this element.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement dragTo(double timeout, int modifiers, Point destination) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, modifiers, getOffset(), destination);
    }

    /**
     * Drags this element from its target point to the specified destination.
     *
     * @param timeout      Search timeout in seconds.
     * @param originOffset Origin (this element) offset.
     * @param destination  Point where to drag the target of this element.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement dragTo(double timeout, Offset originOffset, Point destination) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, 0, originOffset, destination);
    }

    /**
     * Drags this element from its target point to the specified destination.
     *
     * @param timeout      Search timeout in seconds.
     * @param modifiers    Modifiers (see {@link InputEvent}).
     * @param originOffset Origin (this element) offset.
     * @param destination  Point where to drag the target of this element.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement dragTo(double timeout, int modifiers, Offset originOffset, Point destination) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, modifiers, originOffset, destination, 0.0, 0.0, 0.0);
    }

    /**
     * Drags this element from its target point to the specified destination.
     *
     * @param timeout        Search timeout in seconds.
     * @param modifiers      Modifiers (see {@link InputEvent}).
     * @param originOffset   Origin (this element) offset.
     * @param destination    Point where to drag the target of this element.
     * @param delayMouseDown Delay before mouse down on source, in seconds.
     * @param delayDrag      Delay after mouse down and before mouse move, in seconds.
     * @param delayDrop      Delay after mouse move and before mouse up, in seconds.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement dragTo(double timeout, int modifiers, Offset originOffset, Point destination, double delayMouseDown, double delayDrag, double delayDrop) throws ElementNotFoundException, InteractionFailedException {
        EventType endEventType = EventType.AFTER_DRAG_FROM;
        RuntimeException error = null;
        LocalDateTime startTime = LocalDateTime.now();
        DragData eventData = new DragData(timeout, modifiers, this, null, originOffset, null, null, null, null, destination, delayMouseDown, delayDrag, delayDrop);
        DragData.DragDataBuilder dataBuilder = eventData.builder();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_DRAG_FROM, eventData));
        try {
            Point originPoint;
            dataBuilder
                    .withOriginTarget(originPoint = getTarget(timeout, originOffset))
                    .withOriginRectangle(getLastMatchLocation());
            screen.dragDrop(originPoint, modifiers, destination, delayMouseDown, delayDrag, delayDrop);
        } catch (RuntimeException e) {
            endEventType = EventType.DRAG_FROM_ERROR;
            throw error = e;
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, dataBuilder.build(), error));
        }
        return this;
    }

    /**
     * Drags this element from its target to the specified destination target.
     *
     * @param timeout     Search timeout in seconds.
     * @param destination Element where to drag the target of this element.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement dragTo(double timeout, ScreenElement destination) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, 0, destination);
    }

    /**
     * Drags this element from its target to the specified destination target.
     *
     * @param timeout     Search timeout in seconds.
     * @param modifiers   Modifiers (see {@link InputEvent}).
     * @param destination Element where to drag the target of this element.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement dragTo(double timeout, int modifiers, ScreenElement destination) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, modifiers, getOffset(), destination, destination.getOffset());
    }

    /**
     * Drags this element from its target to the specified destination target.
     *
     * @param timeout           Search timeout in seconds.
     * @param originOffset      Origin (this element) offset.
     * @param destination       Element where to drag the target of this element.
     * @param destinationOffset Destination offset.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement dragTo(double timeout, Offset originOffset, ScreenElement destination, Offset destinationOffset) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, 0, originOffset, destination, destinationOffset);
    }

    /**
     * Drags this element from its target to the specified destination target.
     *
     * @param timeout           Search timeout in seconds.
     * @param modifiers         Modifiers (see {@link InputEvent}).
     * @param originOffset      Origin (this element) offset.
     * @param destination       Element where to drag the target of this element.
     * @param destinationOffset Destination offset.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement dragTo(double timeout, int modifiers, Offset originOffset, ScreenElement destination, Offset destinationOffset) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, modifiers, originOffset, destination, destinationOffset, 0.0, 0.0, 0.0);
    }

    /**
     * Drags this element from its target to the specified destination target.
     *
     * @param timeout           Search timeout in seconds.
     * @param modifiers         Modifiers (see {@link InputEvent}).
     * @param originOffset      Origin (this element) offset.
     * @param destination       Element where to drag the target of this element.
     * @param destinationOffset Destination offset.
     * @param delayMouseDown    Delay before mouse down on source, in seconds.
     * @param delayDrag         Delay after mouse down and before mouse move, in seconds.
     * @param delayDrop         Delay after mouse move and before mouse up, in seconds.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement dragTo(double timeout, int modifiers, Offset originOffset, ScreenElement destination, Offset destinationOffset, double delayMouseDown, double delayDrag, double delayDrop) throws ElementNotFoundException, InteractionFailedException {
        EventType endEventTypeFrom = EventType.AFTER_DRAG_FROM;
        EventType endEventTypeTo = EventType.AFTER_DRAG_TO;
        RuntimeException error = null;
        LocalDateTime startTime = LocalDateTime.now();
        DragData eventData = new DragData(timeout, modifiers, this, null, originOffset, null, destination, null, destinationOffset, null, delayMouseDown, delayDrag, delayDrop);
        DragData.DragDataBuilder dataBuilder = eventData.builder();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_DRAG_FROM, eventData));
        destination.dispatchEvent(new Event<>(startTime, EventType.BEFORE_DRAG_TO, eventData));
        try {
            Point originPoint, destinationPoint;
            dataBuilder
                    .withOriginTarget(originPoint = getTarget(timeout, originOffset))
                    .withOriginRectangle(getLastMatchLocation())
                    .withDestinationTarget(destinationPoint = destination.getTarget(timeout, destinationOffset))
                    .withDestinationRectangle(destination.getLastMatchLocation());
            screen.dragDrop(originPoint, modifiers, destinationPoint, delayMouseDown, delayDrag, delayDrop);
        } catch (RuntimeException e) {
            endEventTypeFrom = EventType.DRAG_FROM_ERROR;
            endEventTypeTo = EventType.DRAG_TO_ERROR;
            throw error = e;
        } finally {
            DragData endData = dataBuilder.build();
            LocalDateTime endTime = LocalDateTime.now();
            eventEmitter.emit(new Event<>(startTime, endTime, endEventTypeFrom, endData, error));
            destination.dispatchEvent(new Event<>(startTime, endTime, endEventTypeTo, endData, error));
        }
        return this;
    }

    /**
     * Get the last match rectangle.
     *
     * @return Screen rectangle where the match was last found, or null if it has never been found.
     */
    public Rectangle getLastMatchLocation() {
        return lastMatchLocation;
    }

    /**
     * Get offset specification.
     *
     * @return Offset specification.
     */
    public Offset getOffset() {
        return offset;
    }

    /**
     * Get parent element.
     *
     * @return Parent element or null.
     */
    public ScreenElement getParent() {
        return parent;
    }

    /**
     * Get screen where the element is.
     *
     * @return Screen instance.
     */
    public Screen getScreen() {
        return screen;
    }

    /**
     * Get the target point of this element (usually its center point, see {@link #getOffset} and {@link #setOffset}).
     *
     * @param timeout Search timeout in seconds.
     * @return Target point, specified by this element's rectangle and the configured offset.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public Point getTarget(double timeout) throws ElementNotFoundException {
        return getTarget(timeout, getOffset());
    }

    /**
     * Get the target point of this element, calculated using its rectangle and the offset passed as parameter.
     *
     * @param timeout Search timeout in seconds.
     * @param offset  Offset specification.
     * @return Target point, specified by this element's rectangle and the offset passed as parameter.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public Point getTarget(double timeout, Offset offset) throws ElementNotFoundException {
        try {
            return offset.apply(getMatch(timeout));
        } catch (ImageNotFoundException e) {
            throw new ElementNotFoundException(e);
        }
    }

    /**
     * Get the text in the element's rectangle on the screen.
     *
     * @param timeout Search timeout in seconds.
     * @return OCRed text.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    @SuppressWarnings("unchecked")
    public String getText(final double timeout) throws ElementNotFoundException {
        return performAction(EventType.BEFORE_OCR, EventType.AFTER_OCR, EventType.OCR_ERROR,
                new Class[]{ElementNotFoundException.class}, new OCRData(timeout, this, null, null),
                (OCRData.OCRDataBuilder builder) -> {
                    Rectangle rectangle;
                    String text;
                    builder.withRectangle(rectangle = getMatch(timeout));
                    builder.withText(text = screen.getText(rectangle));
                    return text;
                });
    }

    /**
     * Position mouse over the location of this element.
     *
     * @param timeout Search timeout in seconds.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement hover(double timeout) throws ElementNotFoundException, InteractionFailedException {
        return hover(timeout, 0);
    }

    /**
     * Position mouse over the location of this element.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement hover(double timeout, int modifiers) throws ElementNotFoundException, InteractionFailedException {
        return hover(timeout, modifiers, getOffset());
    }

    /**
     * Position mouse over the location of this element.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @param linger    Lingering time on target in seconds.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement hover(double timeout, int modifiers, double linger) throws ElementNotFoundException, InteractionFailedException {
        return hover(timeout, modifiers, getOffset(), linger);
    }

    /**
     * Position mouse over the target point of this element, calculated using its rectangle and the offset passed as parameter.
     *
     * @param timeout Search timeout in seconds.
     * @param offset  Offset specification.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement hover(double timeout, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        return hover(timeout, 0, offset);
    }

    /**
     * Position mouse over the target point of this element, calculated using its rectangle and the offset passed as parameter.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @param offset    Offset specification.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement hover(double timeout, int modifiers, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        return hover(timeout, modifiers, offset, 0.0);
    }

    /**
     * Position mouse over the target point of this element, calculated using its rectangle and the offset passed as parameter.
     *
     * @param timeout Search timeout in seconds.
     * @param offset  Offset specification.
     * @param linger  Lingering time on target in seconds.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement hover(double timeout, Offset offset, double linger) throws ElementNotFoundException, InteractionFailedException {
        return hover(timeout, 0, offset, linger);
    }

    /**
     * Position mouse over the target point of this element, calculated using its rectangle and the offset passed as parameter.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @param offset    Offset specification.
     * @param linger    Lingering time on target in seconds.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    @SuppressWarnings("unchecked")
    public ScreenElement hover(final double timeout, final int modifiers, final Offset offset, final double linger)
            throws ElementNotFoundException, InteractionFailedException {
        performAction(EventType.BEFORE_CAPTURE, EventType.AFTER_CAPTURE, EventType.CAPTURE_ERROR,
                new Class[]{ElementNotFoundException.class, InteractionFailedException.class},
                new HoverData(timeout, modifiers, this, null, offset, null, linger),
                (HoverData.HoverDataBuilder builder) -> {
                    Point target;
                    builder.withTarget(target = getTarget(timeout, offset)).withElementRectangle(getLastMatchLocation());
                    screen.hover(target, modifiers, linger);
                    return null;
                });
        return this;
    }

    /**
     * Checks if element is displayed on the screen.
     *
     * @param timeout Search timeout in seconds.
     * @return true if displayed on the screen, false otherwise.
     */
    public Boolean isDisplayed(double timeout) {
        try {
            getMatch(timeout);
            return true;
        } catch (ElementNotFoundException e) {
            return false;
        }
    }

    /**
     * Get the flag for using cached parent location.
     *
     * @return Flag.
     */
    public boolean isUsingCachedParentLocation() {
        return useCachedParentLocation;
    }

    /**
     * Checks if element is displayed on the screen.
     *
     * @param timeout Search timeout in seconds.
     * @return true if visible, false otherwise.
     */
    public Boolean isVisible(double timeout) {
        return isDisplayed(timeout);
    }

    /**
     * Remove a pre-existing event listener.
     *
     * @param type     Event type.
     * @param listener Event listener for the event type.
     * @return True if the listener was listening for events, false otherwise.
     */
    public boolean removeEventListener(EventType type, EventListener<?, ?> listener) {
        return eventEmitter.removeEventListener(type, listener);
    }

    /**
     * Right-clicks the location of this element.
     *
     * @param timeout Search timeout in seconds.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement rightClick(double timeout) throws ElementNotFoundException, InteractionFailedException {
        return rightClick(timeout, 0);
    }

    /**
     * Right-clicks the location of this element.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement rightClick(double timeout, int modifiers) throws ElementNotFoundException, InteractionFailedException {
        return rightClick(timeout, modifiers, getOffset());
    }

    /**
     * Right-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @param delay     Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement rightClick(double timeout, int modifiers, double delay) throws ElementNotFoundException, InteractionFailedException {
        return rightClick(timeout, modifiers, getOffset(), delay);
    }

    /**
     * Right-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout Search timeout in seconds.
     * @param offset  Offset specification.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement rightClick(double timeout, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        return rightClick(timeout, 0, offset);
    }

    /**
     * Right-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @param offset    Offset specification.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement rightClick(double timeout, int modifiers, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        return rightClick(timeout, modifiers, offset, 0.0);
    }

    /**
     * Right-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout Search timeout in seconds.
     * @param offset  Offset specification.
     * @param delay   Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement rightClick(double timeout, Offset offset, double delay) throws ElementNotFoundException, InteractionFailedException {
        return rightClick(timeout, 0, offset, delay);
    }

    /**
     * Right-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     *
     * @param timeout   Search timeout in seconds.
     * @param modifiers Modifiers (see {@link InputEvent}).
     * @param offset    Offset specification.
     * @param delay     Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException   when element cannot be located on the screen.
     * @throws InteractionFailedException when interaction with the screen fails.
     */
    public ScreenElement rightClick(double timeout, int modifiers, Offset offset, double delay) throws ElementNotFoundException, InteractionFailedException {
        return performClickAction(ClickActionType.RIGHT_CLICK, timeout, modifiers, offset, delay);
    }

    /**
     * Get parent element.
     *
     * @param parent Parent element or null.
     * @return This element.
     */
    public ScreenElement setParent(ScreenElement parent) {
        this.parent = parent;
        return this;
    }

    /**
     * Set offset specification.
     *
     * @param offset Offset specification.
     * @return This element.
     */
    public ScreenElement setOffset(Offset offset) {
        this.offset = offset;
        return this;
    }

    /**
     * Set the flag for using cached parent location.
     *
     * @param useCache Flag.
     * @return This element.
     */
    public ScreenElement setUsingCachedParentLocation(boolean useCache) {
        useCachedParentLocation = useCache;
        return this;
    }

    /**
     * Waits for this element to be visible.
     *
     * @param timeout Search timeout in seconds.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement waitFor(double timeout) throws ElementNotFoundException {
        getMatch(timeout);
        return this;
    }

    /**
     * Get parent bounding rectangle.
     *
     * @param timeout Search timeout in seconds.
     * @return Parent bounding rectangle, or screen bounds if no parent.
     */
    protected Rectangle getParentBoundingRectangle(double timeout) {
        if (parent == null) {
            return screen.getBounds();
        }
        Rectangle lastParentLocation = parent.getLastMatchLocation();
        // TODO: See if it would make sense to check the elapsed time and reduce the timeout at the calling methods.
        return isUsingCachedParentLocation() && lastParentLocation != null ? lastParentLocation : parent.getMatch(timeout);
    }

    protected <D extends EventData<B>, B extends EventDataBuilder<D>, R, E extends Throwable> R performAction(
            EventType startType, EventType endType, EventType errorType, Class<? extends E>[] exceptionClasses,
            D startEventData, FunctionWithException<B, R, E> action) throws E {
        EventType endEventType = endType;
        E error = null;
        LocalDateTime startTime = LocalDateTime.now();
        B dataBuilder = startEventData.builder();
        eventEmitter.emit(new Event<>(startTime, startType, startEventData));
        try {
            return action.apply(dataBuilder);
        } catch (Throwable e) {
            endEventType = errorType;
            for (Class<? extends E> exceptionClass : exceptionClasses) {
                if (exceptionClass.isInstance(e)) {
                    throw error = exceptionClass.cast(e);
                }
            }
            throw e; // Why does this work without any casting whatsoever?
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, dataBuilder.build(), error));
        }
    }

    @SuppressWarnings("unchecked")
    protected ScreenElement performClickAction(final ClickActionType clickActionType, final double timeout,
                                               final int modifiers, final Offset offset, final double delay)
            throws ElementNotFoundException, InteractionFailedException {
        performAction(EventType.BEFORE_CLICK, EventType.AFTER_CLICK, EventType.CLICK_ERROR,
                new Class[]{ElementNotFoundException.class, InteractionFailedException.class},
                new ClickData(clickActionType, timeout, modifiers, this, null, offset, null, delay),
                (ClickData.ClickDataBuilder builder) -> {
                    Point target;
                    builder.withTarget(target = getTarget(timeout, offset)).withElementRectangle(getLastMatchLocation());
                    screen.click(target, modifiers);
                    return null;
                });
        return this;
    }

    /**
     * Set the last matching rectangle.
     *
     * @return This element.
     */
    protected ScreenElement setLastMatchLocation(Rectangle rectangle) {
        lastMatchLocation = rectangle;
        return this;
    }
}
