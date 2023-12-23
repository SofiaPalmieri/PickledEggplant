package com.olenickglobal.elements.events;

import com.olenickglobal.elements.Offset;
import com.olenickglobal.elements.ScreenElement;

import java.awt.*;

/**
 * Data for a drag event.
 *
 * @param timeout              Timeout in seconds.
 * @param modifiers            Modifier bits.
 * @param origin               Origin element--if any.
 * @param originRectangle      Origin element rectangle--if any.
 * @param originOffset         Origin offset--if any.
 * @param originTarget         Origin target point.
 * @param destination          Destination element--if any.
 * @param destinationRectangle Destination element rectangle--if any.
 * @param destinationOffset    Destination offset--if any.
 * @param destinationTarget    Destination target point.
 * @param delayMouseDown       Delay before mouseDown event.
 * @param delayDrag            Delay before drag event.
 * @param delayDrop            Delay before drop event.
 */
public record DragData(double timeout, int modifiers, ScreenElement origin, Rectangle originRectangle,
                       Offset originOffset, Point originTarget, ScreenElement destination,
                       Rectangle destinationRectangle, Offset destinationOffset, Point destinationTarget,
                       double delayMouseDown, double delayDrag, double delayDrop)
        implements EventData<DragData.DragDataBuilder> {
    public static class DragDataBuilder implements EventDataBuilder<DragData> {
        protected double timeout;
        protected int modifiers;
        protected ScreenElement origin;
        protected Rectangle originRectangle;
        protected Offset originOffset;
        protected Point originTarget;
        protected ScreenElement destination;
        protected Rectangle destinationRectangle;
        protected Offset destinationOffset;
        protected Point destinationTarget;
        protected double delayMouseDown;
        protected double delayDrag;
        protected double delayDrop;

        public DragDataBuilder(DragData data) {
            this.timeout = data.timeout;
            this.modifiers = data.modifiers;
            this.origin = data.origin;
            this.originRectangle = data.originRectangle;
            this.originOffset = data.originOffset;
            this.originTarget = data.originTarget;
            this.destination = data.destination;
            this.destinationRectangle = data.destinationRectangle;
            this.destinationOffset = data.destinationOffset;
            this.destinationTarget = data.destinationTarget;
            this.delayMouseDown = data.delayMouseDown;
            this.delayDrag = data.delayDrag;
            this.delayDrop = data.delayDrop;
        }

        public DragData build() {
            return new DragData(timeout, modifiers, origin, originRectangle, originOffset, originTarget,
                    destination, destinationRectangle, destinationOffset, destinationTarget,
                    delayMouseDown, delayDrag, delayDrop);
        }

        public DragDataBuilder withTimeout(double timeout) {
            this.timeout = timeout;
            return this;
        }

        public DragDataBuilder withModifiers(int modifiers) {
            this.modifiers = modifiers;
            return this;
        }

        public DragDataBuilder withOrigin(ScreenElement origin) {
            this.origin = origin;
            return this;
        }

        public DragDataBuilder withOriginRectangle(Rectangle originRectangle) {
            this.originRectangle = originRectangle;
            return this;
        }

        public DragDataBuilder withOriginOffset(Offset originOffset) {
            this.originOffset = originOffset;
            return this;
        }

        public DragDataBuilder withOriginTarget(Point originTarget) {
            this.originTarget = originTarget;
            return this;
        }

        public DragDataBuilder withDestination(ScreenElement destination) {
            this.destination = destination;
            return this;
        }

        public DragDataBuilder withDestinationRectangle(Rectangle destinationRectangle) {
            this.destinationRectangle = destinationRectangle;
            return this;
        }

        public DragDataBuilder withDestinationOffset(Offset destinationOffset) {
            this.destinationOffset = destinationOffset;
            return this;
        }

        public DragDataBuilder withDestinationTarget(Point destinationTarget) {
            this.destinationTarget = destinationTarget;
            return this;
        }

        public DragDataBuilder withDelayMouseDown(double delayMouseDown) {
            this.delayMouseDown = delayMouseDown;
            return this;
        }

        public DragDataBuilder withDelayDrag(double delayDrag) {
            this.delayDrag = delayDrag;
            return this;
        }

        public DragDataBuilder withDelayDrop(double delayDrop) {
            this.delayDrop = delayDrop;
            return this;
        }
    }

    public DragData(int modifiers, Point originTarget, Point destinationTarget, double delayMouseDown, double delayDrag, double delayDrop) {
        this(0.0, modifiers, null, null, null, originTarget, null, null, null, destinationTarget, delayMouseDown, delayDrag, delayDrop);
    }

    public DragDataBuilder builder() {
        return new DragDataBuilder(this);
    }
}
