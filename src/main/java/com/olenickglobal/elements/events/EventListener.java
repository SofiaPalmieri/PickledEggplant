package com.olenickglobal.elements.events;

import java.util.Objects;

/**
 * Event listener.
 * @param <T> Class of data objects.
 * @param <U> Class of error exceptions.
 */
@FunctionalInterface
public interface EventListener<T, U extends Throwable> {
    /**
     * Performs this operation on the given argument.
     * @param event Event to be dispatched.
     */
    void listen(Event<T, U> event) throws Exception;

     /**
      * Returns a composed {@code EventListener} that performs, in sequence, this
      * operation followed by the {@code after} operation. If performing either
      * operation throws an exception, it is relayed to the caller of the
      * composed operation. If performing this operation throws an exception,
      * the {@code after} operation will not be performed.
      *
      * @param after The operation to perform after this operation.
      * @return a composed {@code Consumer} that performs in sequence this operation followed by the {@code after} operation.
      * @throws NullPointerException if {@code after} is null.
      */
     default EventListener<T, U> andThen(EventListener<T, U> after) {
         Objects.requireNonNull(after);
         return (Event<T, U> event) -> { listen(event); after.listen(event); };
     }
}
