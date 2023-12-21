package com.olenickglobal.elements.events;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class EventEmitter {
    protected final AtomicReferenceArray<Map<EventListener<?, ?>, Boolean>> listeners =
            new AtomicReferenceArray<>(EventType.values().length);

    public static Duration duration(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.ofNanos(endTime.until(endTime, ChronoUnit.NANOS));
    }

    /**
     * Add an event listener.
     * @param type Event type.
     * @param listener Event listener for the event type.
     * @return True if the listener was added, false otherwise.
     */
    public boolean addEventListener(EventType type, EventListener<?, ?> listener) {
        int position = type.ordinal();
        listeners.compareAndSet(position, null, new ConcurrentHashMap<>());
        return listeners.get(position).put(listener, Boolean.TRUE) == Boolean.TRUE;
    }

    /**
     * Notify listeners.
     * @param <T> Event data type.
     * @param <U> Error type.
     * @param event Event object.
     */
    // TODO: Should we notify threads?
    @SuppressWarnings("unchecked")
    public <T, U extends Throwable> void emit(Event<T, U> event) {
        EventType eventType = event.type();
        Map<EventListener<?, ?>, Boolean> listenersForEvent = listeners.get(eventType.ordinal());
        if (listenersForEvent != null) {
            for (EventListener<?, ?> listener : listenersForEvent.keySet()) {
                try {
                    ((EventListener<T, U>) listener).listen(event);
                } catch (Exception e) {
                    // TODO: Use logger.
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    /**
     * Remove a pre-existing event listener.
     * @param type Event type.
     * @param listener Event listener for the event type.
     * @return True if the listener was listening for events, false otherwise.
     */
    public boolean removeEventListener(EventType type, EventListener<?, ?> listener) {
        Map<EventListener<?, ?>, Boolean> listenersForType = listeners.get(type.ordinal());
        return listenersForType != null && listenersForType.remove(listener) == Boolean.TRUE;
    }
}
