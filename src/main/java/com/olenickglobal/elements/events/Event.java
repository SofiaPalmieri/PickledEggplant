package com.olenickglobal.elements.events;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Event object.
 * @param <T> Class of data object.
 * @param <U> Class of error object (descendant class of Throwable).
 * @param time Date-time of the event's occurrence.
 * @param duration Duration of the whole action--if this is an "AFTER" kind of event.
 * @param type Type of event.
 * @param data Event data object.
 * @param error Error that may have occurred during locating action.
 */
public record Event<T, U extends Throwable>(LocalDateTime time, Duration duration, EventType type, T data, U error) {
    public Event(LocalDateTime startTime, EventType type, T data) {
        this(startTime, (Duration) null, type, data, null);
    }

    public Event(LocalDateTime startTime, LocalDateTime endTime, EventType type, T data) {
        this(startTime, endTime, type, data, null);
    }

    public Event(LocalDateTime startTime, LocalDateTime endTime, EventType type, T data, U error) {
        this(endTime, Duration.between(startTime, endTime), type, data, error);
    }
}
