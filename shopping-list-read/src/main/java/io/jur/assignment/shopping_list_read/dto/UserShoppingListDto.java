package io.jur.assignment.shopping_list_read.dto;

import lombok.Builder;

import java.util.List;

/**
 * user shopping list response
 */
public record UserShoppingListDto(String userId, List<ShoppingItem> shoppingItems) {
    @Builder
    public UserShoppingListDto {
    }
}
