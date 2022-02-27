package io.jur.assignment.shopping_list.write.service;

import io.jur.assignment.events.Event;
import io.jur.assignment.events.EventType;
import io.jur.assignment.events.shopping.ShoppingListEvent;
import io.jur.assignment.shopping_list.write.dto.AddToShoppingListRequest;
import io.jur.assignment.shopping_list.write.dto.DeleteFromShoppingListRequest;
import io.jur.assignment.shopping_list.write.event.EventPublisher;
import io.jur.assignment.shopping_list.write.model.ShoppingEventEntity;
import io.jur.assignment.shopping_list.write.model.ShoppingEventType;
import io.jur.assignment.shopping_list.write.repository.ShoppingEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Shopping Service
 */
@Service
@AllArgsConstructor
public class ShoppingService {

    private final ShoppingEventRepository shoppingEventRepository;
    private final EventPublisher publisher;

    /**
     * add to shopping list event
     *
     * @param request {@link AddToShoppingListRequest}
     */
    public void addToShoppingList(AddToShoppingListRequest request) {

        ShoppingEventEntity shoppingEventEntity = shoppingEventRepository.save(ShoppingEventEntity.builder()
                .shoppingEventType(ShoppingEventType.ADD)
                .productId(request.getProductId())
                .userId(request.getUserId())
                .count(request.getCount())
                .build());

        publisher.sendEvent(Event.builder()
                .eventType(EventType.SHOPPING_LIST)
                .eventData(ShoppingListEvent.builder()
                        .id(shoppingEventEntity.getId())
                        .userId(shoppingEventEntity.getUserId())
                        .productId(shoppingEventEntity.getProductId())
                        .time(shoppingEventEntity.getTime())
                        .count(shoppingEventEntity.getCount())
                        .shoppingEventType(io.jur.assignment.events.shopping.ShoppingEventType.ADD)
                        .build())
                .build());
    }

    /**
     * Delete from shopping list event
     *
     * @param request {@link DeleteFromShoppingListRequest}
     */
    public void deleteFromShoppingList(DeleteFromShoppingListRequest request) {

        ShoppingEventEntity shoppingEventEntity = shoppingEventRepository.save(ShoppingEventEntity.builder()
                .shoppingEventType(ShoppingEventType.REMOVE)
                .productId(request.getProductId())
                .userId(request.getUserId())
                .count(0)
                .build());

        publisher.sendEvent(Event.builder()
                .eventType(EventType.SHOPPING_LIST)
                .eventData(ShoppingListEvent.builder()
                        .id(shoppingEventEntity.getId())
                        .userId(shoppingEventEntity.getUserId())
                        .productId(shoppingEventEntity.getProductId())
                        .time(shoppingEventEntity.getTime())
                        .shoppingEventType(io.jur.assignment.events.shopping.ShoppingEventType.REMOVE)
                        .build())
                .build());
    }


}
