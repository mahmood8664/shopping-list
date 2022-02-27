package io.jur.assignment.shopping_list_read.dto;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * user shopping list items
 */
public record ShoppingItem(String productId, LocalDateTime date, int count) {
    @Builder
    public ShoppingItem {
    }

}
