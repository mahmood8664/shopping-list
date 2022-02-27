package io.jur.assignment.shopping_list_read.event.shopping;

import io.jur.assignment.events.shopping.ShoppingEventType;
import io.jur.assignment.events.shopping.ShoppingListEvent;
import io.jur.assignment.shopping_list_read.model.ShoppingListEntity;
import io.jur.assignment.shopping_list_read.repository.ShoppingListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * add to shopping list event handler
 */
@Component
@AllArgsConstructor
@Slf4j
public class AddToShoppingListEventHandler {

    private final ShoppingListRepository repository;

    /**
     * add to shopping list
     *
     * @param event
     */
    @Transactional
    public void addToShoppingList(ShoppingListEvent event) {
        if (event.shoppingEventType() != ShoppingEventType.ADD) {
            throw new RuntimeException("invalid event type");
        }
        try {
            //first delete previous related shopping list
            repository.deleteAllByUserIdAndProductId(event.userId(), event.productId());
            //then add the new shopping list
            repository.save(ShoppingListEntity.builder()
                    .id(event.id())
                    .productId(event.productId())
                    .userId(event.userId())
                    .time(event.time())
                    .count(event.count())
                    .build());
        } catch (Exception e) {
            log.error("can not add to the shopping list : {} ", event, e);
            throw new RuntimeException(e);
        }
    }

}
