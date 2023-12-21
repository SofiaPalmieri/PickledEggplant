package com.olenickglobal.elements.events;

import com.olenickglobal.elements.Offset;
import com.olenickglobal.elements.ScreenElement;

import java.awt.*;

/**
 * Data for a drag event.
 * @param timeout Timeout in seconds.
 * @param modifiers Modifier bits.
 * @param origin Origin element--if any.
 * @param originRectangle Origin element rectangle--if any.
 * @param originOffset Origin offset--if any.
 * @param originTarget Origin target point.
 * @param destination Destination element--if any.
 * @param destinationRectangle Destination element rectangle--if any.
 * @param destinationOffset Destination offset--if any.
 * @param destinationTarget Destination target point.
 * @param delayMouseDown Delay before mouseDown event.
 * @param delayDrag Delay before drag event.
 * @param delayDrop Delay before drop event.
 */
public record DragData(double timeout, int modifiers, ScreenElement origin, Rectangle originRectangle,
                       Offset originOffset, Point originTarget, ScreenElement destination,
                       Rectangle destinationRectangle, Offset destinationOffset, Point destinationTarget,
                       double delayMouseDown, double delayDrag, double delayDrop) {
    public DragData(int modifiers, Point originTarget, Point destinationTarget, double delayMouseDown, double delayDrag, double delayDrop) {
        this(0.0, modifiers, null, null, null, originTarget, null, null, null, destinationTarget, delayMouseDown, delayDrag, delayDrop);
    }
}
