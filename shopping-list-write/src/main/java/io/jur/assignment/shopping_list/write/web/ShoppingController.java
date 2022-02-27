package io.jur.assignment.shopping_list.write.web;

import io.jur.assignment.shopping_list.write.dto.AddToShoppingListRequest;
import io.jur.assignment.shopping_list.write.dto.DeleteFromShoppingListRequest;
import io.jur.assignment.shopping_list.write.service.ShoppingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Shopping Controller
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/shopping/")
public class ShoppingController {

    private final ShoppingService shoppingService;

    /**
     * Add to shopping list
     *
     * @param request {@link AddToShoppingListRequest}
     * @return void
     */
    @Operation(description = "Add to shopping list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully perform the operation"),
            @ApiResponse(responseCode = "400", description = "The Request property was not provide correctly."),
    })
    @PostMapping
    public ResponseEntity<Void> addToShoppingList(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "request for add to shopping list")
            @RequestBody @Valid AddToShoppingListRequest request) {
        shoppingService.addToShoppingList(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Remove from shopping list
     *
     * @param request {@link DeleteFromShoppingListRequest}
     * @return void
     */
    @Operation(description = "remove from shopping list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully perform the operation"),
            @ApiResponse(responseCode = "400", description = "The Request property was not provide correctly."),
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteFromShoppingList(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "request body for removing from shopping list")
            @RequestBody @Valid DeleteFromShoppingListRequest request) {
        shoppingService.deleteFromShoppingList(request);
        return ResponseEntity.ok().build();
    }
}
