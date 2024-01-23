package com.olenickglobal.elements.events;

import com.olenickglobal.formatting.EventFormatter;

/**
 * Event data.
 *
 * @param <B> Builder type.
 */
public interface EventData<B> {
    B builder();

}
