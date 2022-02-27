package io.jur.assignment.shopping_list.write.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Delete from shopping list request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteFromShoppingListRequest {
    /**
     * user id
     */
    @NotNull
    private String userId;
    /**
     * product id
     */
    @NotNull
    private String productId;
}
