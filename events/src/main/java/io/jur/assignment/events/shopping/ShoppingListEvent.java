package io.jur.assignment.events.shopping;

import io.jur.assignment.events.EventData;
import lombok.Builder;

import java.time.LocalDateTime;


public record ShoppingListEvent(int id, String userId, String productId,
                                ShoppingEventType shoppingEventType,
                                LocalDateTime time, int count) implements EventData {
    @Builder
    public ShoppingListEvent {
    }
}
