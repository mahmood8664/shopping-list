package io.jur.assignment.shopping_list.write.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * add to shopping list request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToShoppingListRequest {
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
    /**
     * number of products
     */
    @Schema(name = "count", description = "number of product items")
    private int count = 1;
}
