package com.olenickglobal.elements.events;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Event emitter.
 */
public class EventEmitter {
    protected static volatile EventEmitter globalInstance;

    protected final AtomicReferenceArray<Map<EventListener<?, ?>, Boolean>> listeners =
            new AtomicReferenceArray<>(EventType.values().length);

    public static EventEmitter getGlobal() {
        if (globalInstance == null) {
            synchronized (EventEmitter.class) {
                if (globalInstance == null) {
                    globalInstance = new EventEmitter();
                }
            }
        }
        return globalInstance;
    }

    /**
     * Add an event listener.
     *
     * @param type     Event type.
     * @param listener Event listener for the event type.
     * @return True if the listener was added, false if the listener was already registered.
     */
    public boolean addEventListener(EventType type, EventListener<?, ?> listener) {
        int position = type.ordinal();
        listeners.compareAndSet(position, null, new ConcurrentHashMap<>());
        Map<EventListener<?, ?>, Boolean> listenersForType = listeners.get(position);
        return !listenersForType.containsKey(listener) && listenersForType.put(listener, Boolean.TRUE) == null;
    }

    /**
     * Notify listeners.
     *
     * @param <T>   Event data type.
     * @param <U>   Error type.
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
        EventEmitter global = getGlobal();
        if (this != global) {
            global.emit(event);
        }
    }

    /**
     * Remove a pre-existing event listener.
     *
     * @param type     Event type.
     * @param listener Event listener for the event type.
     * @return True if the listener was listening for events, false otherwise.
     */
    public boolean removeEventListener(EventType type, EventListener<?, ?> listener) {
        Map<EventListener<?, ?>, Boolean> listenersForType = listeners.get(type.ordinal());
        return listenersForType != null && listenersForType.remove(listener) == Boolean.TRUE;
    }
}
