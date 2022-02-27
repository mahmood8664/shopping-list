package io.jur.assignment.shopping_list_read.event.shopping;

import io.jur.assignment.events.shopping.ShoppingEventType;
import io.jur.assignment.events.shopping.ShoppingListEvent;
import io.jur.assignment.shopping_list_read.repository.ShoppingListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Delete from shopping list handler
 */
@Component
@AllArgsConstructor
@Slf4j
public class DeleteFromShoppingListEventHandler {

    private final ShoppingListRepository repository;

    /**
     * Delete from shopping list
     *
     * @param event {@link ShoppingListEvent}
     */
    @Transactional
    public void deleteFromShoppingList(ShoppingListEvent event) {
        if (event.shoppingEventType() != ShoppingEventType.REMOVE) {
            throw new RuntimeException("invalid event type");
        }
        try {
            repository.deleteAllByUserIdAndProductId(event.userId(), event.productId());
        } catch (Exception e) {
            log.error("cannot delete data from shopping list : {}", event, e);
            throw new RuntimeException(e);
        }
    }
}
