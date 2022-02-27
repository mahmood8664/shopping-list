package io.jur.assignment.events;

import lombok.Getter;

/**
 * Event type, also determines topic name of event bus
 */
@Getter
public enum EventType {
    SHOPPING_LIST("shopping_list_event");

    private final String topicName;

    EventType(String topicName) {
        this.topicName = topicName;
    }
}
