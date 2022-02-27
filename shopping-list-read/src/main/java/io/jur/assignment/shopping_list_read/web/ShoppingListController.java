package io.jur.assignment.shopping_list_read.web;

import io.jur.assignment.shopping_list_read.dto.ShoppingItem;
import io.jur.assignment.shopping_list_read.dto.UserShoppingListDto;
import io.jur.assignment.shopping_list_read.model.ShoppingListEntity;
import io.jur.assignment.shopping_list_read.service.ShoppingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Shopping controller, read operations
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/shopping/")
public class ShoppingListController {

    private final ShoppingService shoppingService;

    /**
     * Read user shopping list
     *
     * @param userId user id
     * @return shopping list {@link UserShoppingListDto}
     */
    @Operation(description = "Returns user cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully perform the operation"),
    })
    @GetMapping("{user_id}")
    public ResponseEntity<UserShoppingListDto> getUserCard(@PathVariable("user_id") String userId) {
        return ResponseEntity.ok(toUserShoppingListDto(shoppingService.getUserShoppingList(userId), userId));
    }

    private UserShoppingListDto toUserShoppingListDto(List<ShoppingListEntity> userShoppingList, String userId) {
        return UserShoppingListDto.builder()
                .userId(userId)
                .shoppingItems(
                        userShoppingList.stream()
                                .map(shoppingListEntity -> ShoppingItem.builder()
                                        .productId(shoppingListEntity.getProductId())
                                        .date(shoppingListEntity.getTime())
                                        .count(shoppingListEntity.getCount())
                                        .build()).collect(Collectors.toList())
                ).build();
    }

}
