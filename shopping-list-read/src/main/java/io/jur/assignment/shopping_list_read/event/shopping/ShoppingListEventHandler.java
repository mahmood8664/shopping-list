package io.jur.assignment.shopping_list_read.event.shopping;

import io.jur.assignment.events.shopping.ShoppingListEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * shopping event handler
 */
@Component
@AllArgsConstructor
public class ShoppingListEventHandler {

    private final AddToShoppingListEventHandler addToShoppingListEventHandler;
    private final DeleteFromShoppingListEventHandler deleteFromShoppingListEventHandler;

    /**
     * handle {@link ShoppingListEvent} events
     *
     * @param event {@link ShoppingListEvent}
     */
    public void handle(ShoppingListEvent event) {
        switch (event.shoppingEventType()) {
            case ADD -> addToShoppingListEventHandler.addToShoppingList(event);
            case REMOVE -> deleteFromShoppingListEventHandler.deleteFromShoppingList(event);
        }
    }
}
