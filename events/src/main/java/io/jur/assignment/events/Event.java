package io.jur.assignment.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * event of system
 * @param <T> extends {@link EventData}
 */
@Data
@Builder
@AllArgsConstructor
public class Event<T extends EventData> {
    /**
     * event type @link {@link EventType}
     */
    EventType eventType;
    /**
     * event data
     */
    T eventData;
}
