package com.olenickglobal.elements;

import com.olenickglobal.entities.Screen;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.ImageNotFoundException;
import com.olenickglobal.exceptions.InteractionFailedException;

import java.awt.*;

public abstract class ScreenElement {
    protected final Screen screen;

    protected Offset offset;

    public ScreenElement(Screen screen, Offset offset) {
        this.screen = screen;
        this.offset = offset;
    }

    /**
     * Gets a region match.
     * @param timeout Search timeout in seconds.
     * @return Screen rectangle where the match was found.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    // TODO: Caching policy?
    abstract Rectangle getMatch(double timeout) throws ElementNotFoundException;

    /**
     * Clicks the location of this element.
     * @param timeout Search timeout in seconds.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement click(double timeout) throws ElementNotFoundException, InteractionFailedException {
        return click(timeout, 0);
    }

    /**
     * Clicks the location of this element.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement click(double timeout, int modifiers) throws ElementNotFoundException, InteractionFailedException {
        return click(timeout, modifiers, getOffset());
    }

    /**
     * Clicks the location of this element.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param delay Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement click(double timeout, int modifiers, double delay) throws ElementNotFoundException, InteractionFailedException {
        return click(timeout, modifiers, getOffset(), delay);
    }

    /**
     * Clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param offset Offset specification.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement click(double timeout, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        return click(timeout, 0, offset);
    }

    /**
     * Clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param offset Offset specification.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement click(double timeout, int modifiers, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        screen.click(getTarget(timeout, offset), modifiers);
        return this;
    }

    /**
     * Clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param offset Offset specification.
     * @param delay Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement click(double timeout, Offset offset, double delay) throws ElementNotFoundException, InteractionFailedException {
        return click(timeout, 0, offset, delay);
    }

    /**
     * Clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param offset Offset specification.
     * @param delay Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement click(double timeout, int modifiers, Offset offset, double delay) throws ElementNotFoundException, InteractionFailedException {
        screen.click(getTarget(timeout, offset), modifiers, delay);
        return this;
    }

    /**
     * Double-clicks the location of this element.
     * @param timeout Search timeout in seconds.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement doubleClick(double timeout) throws ElementNotFoundException, InteractionFailedException {
        return doubleClick(timeout, 0);
    }

    /**
     * Double-clicks the location of this element.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement doubleClick(double timeout, int modifiers) throws ElementNotFoundException, InteractionFailedException {
        return doubleClick(timeout, modifiers, getOffset());
    }

    /**
     * Double-clicks the location of this element.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param delay Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement doubleClick(double timeout, int modifiers, double delay) throws ElementNotFoundException, InteractionFailedException {
        return doubleClick(timeout, modifiers, getOffset(), delay);
    }

    /**
     * Double-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param offset Offset specification.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement doubleClick(double timeout, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        return doubleClick(timeout, 0, offset);
    }

    /**
     * Double-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param offset Offset specification.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement doubleClick(double timeout, int modifiers, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        screen.doubleClick(getTarget(timeout, offset), modifiers);
        return this;
    }

    /**
     * Double-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param offset Offset specification.
     * @param delay Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement doubleClick(double timeout, Offset offset, double delay) throws ElementNotFoundException, InteractionFailedException {
        return doubleClick(timeout, 0, offset, delay);
    }

    /**
     * Double-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param offset Offset specification.
     * @param delay Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement doubleClick(double timeout, int modifiers, Offset offset, double delay) throws ElementNotFoundException, InteractionFailedException {
        screen.doubleClick(getTarget(timeout, offset), modifiers, delay);
        return this;
    }

    /**
     * Drags this element from its target point to the specified destination.
     * @param timeout Search timeout in seconds.
     * @param destination Point where to drag the target of this element.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement dragTo(double timeout, Point destination) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, 0, destination);
    }

    /**
     * Drags this element from its target point to the specified destination.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param destination Point where to drag the target of this element.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement dragTo(double timeout, int modifiers, Point destination) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, modifiers, getOffset(), destination);
    }

    /**
     * Drags this element from its target point to the specified destination.
     * @param timeout Search timeout in seconds.
     * @param originOffset Origin (this element) offset.
     * @param destination Point where to drag the target of this element.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement dragTo(double timeout, Offset originOffset, Point destination) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, 0, originOffset, destination);
    }

    /**
     * Drags this element from its target point to the specified destination.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param originOffset Origin (this element) offset.
     * @param destination Point where to drag the target of this element.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement dragTo(double timeout, int modifiers, Offset originOffset, Point destination) throws ElementNotFoundException, InteractionFailedException {
        screen.dragDrop(getTarget(timeout, originOffset), modifiers, destination);
        return this;
    }

    /**
     * Drags this element from its target point to the specified destination.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param originOffset Origin (this element) offset.
     * @param destination Point where to drag the target of this element.
     * @param delayMouseDown Delay before mouse down on source, in seconds.
     * @param delayDrag Delay after mouse down and before mouse move, in seconds.
     * @param delayDrop Delay after mouse move and before mouse up, in seconds.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement dragTo(double timeout, int modifiers, Offset originOffset, Point destination, double delayMouseDown, double delayDrag, double delayDrop) throws ElementNotFoundException, InteractionFailedException {
        screen.dragDrop(getTarget(timeout, originOffset), modifiers, destination, delayMouseDown, delayDrag, delayDrop);
        return this;
    }

    /**
     * Drags this element from its target to the specified destination target.
     * @param timeout Search timeout in seconds.
     * @param destination Element where to drag the target of this element.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement dragTo(double timeout, ScreenElement destination) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, 0, destination);
    }

    /**
     * Drags this element from its target to the specified destination target.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param destination Element where to drag the target of this element.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement dragTo(double timeout, int modifiers, ScreenElement destination) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, modifiers, destination.getTarget(timeout));
    }

    /**
     * Drags this element from its target to the specified destination target.
     * @param timeout Search timeout in seconds.
     * @param originOffset Origin (this element) offset.
     * @param destination Element where to drag the target of this element.
     * @param destinationOffset Destination offset.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement dragTo(double timeout, Offset originOffset, ScreenElement destination, Offset destinationOffset) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, 0, originOffset, destination, destinationOffset);
    }

    /**
     * Drags this element from its target to the specified destination target.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param originOffset Origin (this element) offset.
     * @param destination Element where to drag the target of this element.
     * @param destinationOffset Destination offset.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement dragTo(double timeout, int modifiers, Offset originOffset, ScreenElement destination, Offset destinationOffset) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, modifiers, originOffset, destination.getTarget(timeout, destinationOffset));
    }

    /**
     * Drags this element from its target to the specified destination target.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param originOffset Origin (this element) offset.
     * @param destination Element where to drag the target of this element.
     * @param destinationOffset Destination offset.
     * @param delayMouseDown Delay before mouse down on source, in seconds.
     * @param delayDrag Delay after mouse down and before mouse move, in seconds.
     * @param delayDrop Delay after mouse move and before mouse up, in seconds.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement dragTo(double timeout, int modifiers, Offset originOffset, ScreenElement destination, Offset destinationOffset, double delayMouseDown, double delayDrag, double delayDrop) throws ElementNotFoundException, InteractionFailedException {
        return dragTo(timeout, modifiers, originOffset, destination.getTarget(timeout, destinationOffset), delayMouseDown, delayDrag, delayDrop);
    }

    /**
     * Get offset specification.
     * @return Offset specification.
     */
    public Offset getOffset() {
        return offset;
    }

    /**
     * Get the target point of this element (usually its center point, see {@link #getOffset} and {@link #setOffset}).
     * @param timeout Search timeout in seconds.
     * @return Target point, specified by this element's rectangle and the configured offset.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public Point getTarget(double timeout) throws ElementNotFoundException {
        return getTarget(timeout, getOffset());
    }

    /**
     * Get the target point of this element, calculated using its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param offset Offset specification.
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
     * Position mouse over the location of this element.
     * @param timeout Search timeout in seconds.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement hover(double timeout) throws ElementNotFoundException, InteractionFailedException {
        return hover(timeout, 0);
    }

    /**
     * Position mouse over the location of this element.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement hover(double timeout, int modifiers) throws ElementNotFoundException, InteractionFailedException {
        screen.hover(getTarget(timeout), modifiers);
        return this;
    }

    /**
     * Position mouse over the location of this element.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param linger Lingering time on target in seconds.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement hover(double timeout, int modifiers, double linger) throws ElementNotFoundException, InteractionFailedException {
        return hover(timeout, modifiers, getOffset(), linger);
    }

    /**
     * Position mouse over the target point of this element, calculated using its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param offset Offset specification.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement hover(double timeout, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        return hover(timeout, 0, offset);
    }

    /**
     * Position mouse over the target point of this element, calculated using its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param offset Offset specification.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement hover(double timeout, int modifiers, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        screen.hover(getTarget(timeout, offset), modifiers);
        return this;
    }

    /**
     * Position mouse over the target point of this element, calculated using its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param offset Offset specification.
     * @param linger Lingering time on target in seconds.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement hover(double timeout, Offset offset, double linger) throws ElementNotFoundException, InteractionFailedException {
        return hover(timeout, 0, offset, linger);
    }

    /**
     * Position mouse over the target point of this element, calculated using its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param offset Offset specification.
     * @param linger Lingering time on target in seconds.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement hover(double timeout, int modifiers, Offset offset, double linger) throws ElementNotFoundException, InteractionFailedException {
        screen.hover(getTarget(timeout, offset), modifiers, linger);
        return this;
    }

    /**
     * Checks if element is displayed on the screen.
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
     * Checks if element is displayed on the screen.
     * @param timeout Search timeout in seconds.
     * @return true if visible, false otherwise.
     */
    public Boolean isVisible(double timeout) {
        return isDisplayed(timeout);
    }

    /**
     * Right-clicks the location of this element.
     * @param timeout Search timeout in seconds.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement rightClick(double timeout) throws ElementNotFoundException, InteractionFailedException {
        return rightClick(timeout, 0);
    }

    /**
     * Right-clicks the location of this element.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement rightClick(double timeout, int modifiers) throws ElementNotFoundException, InteractionFailedException {
        return rightClick(timeout, modifiers, getOffset());
    }

    /**
     * Right-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param delay Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement rightClick(double timeout, int modifiers, double delay) throws ElementNotFoundException, InteractionFailedException {
        return rightClick(timeout, modifiers, getOffset(), delay);
    }

    /**
     * Right-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param offset Offset specification.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement rightClick(double timeout, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        return rightClick(timeout, 0, offset);
    }

    /**
     * Right-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param offset Offset specification.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement rightClick(double timeout, int modifiers, Offset offset) throws ElementNotFoundException, InteractionFailedException {
        screen.rightClick(getTarget(timeout, offset), modifiers);
        return this;
    }

    /**
     * Right-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param offset Offset specification.
     * @param delay Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement rightClick(double timeout, Offset offset, double delay) throws ElementNotFoundException, InteractionFailedException {
        return rightClick(timeout, 0, offset, delay);
    }

    /**
     * Right-clicks the target point of this element, specified by its rectangle and the offset passed as parameter.
     * @param timeout Search timeout in seconds.
     * @param modifiers Modifiers (see {@link java.awt.event.InputEvent}).
     * @param offset Offset specification.
     * @param delay Click delay in seconds, from 0.0 to &lt;1.0.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement rightClick(double timeout, int modifiers, Offset offset, double delay) throws ElementNotFoundException, InteractionFailedException {
        screen.rightClick(getTarget(timeout, offset), modifiers, delay);
        return this;
    }

    /**
     * Set offset specification.
     * @param offset Offset specification.
     * @return This element.
     */
    public ScreenElement setOffset(Offset offset) {
        this.offset = offset;
        return this;
    }

    /**
     * Waits for this element to be visible.
     * @param timeout Search timeout in seconds.
     * @return This element.
     * @throws ElementNotFoundException when element cannot be located on the screen.
     */
    public ScreenElement waitFor(double timeout) throws ElementNotFoundException {
        getMatch(timeout);
        return this;
    }
}
