package io.jur.assignment.shopping_list_read.unit;

import io.jur.assignment.events.shopping.ShoppingEventType;
import io.jur.assignment.events.shopping.ShoppingListEvent;
import io.jur.assignment.shopping_list_read.event.shopping.AddToShoppingListEventHandler;
import io.jur.assignment.shopping_list_read.event.shopping.DeleteFromShoppingListEventHandler;
import io.jur.assignment.shopping_list_read.event.shopping.ShoppingListEventHandler;
import io.jur.assignment.shopping_list_read.model.ShoppingListEntity;
import io.jur.assignment.shopping_list_read.repository.ShoppingListRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ShoppingListEventHandler.class, AddToShoppingListEventHandler.class,
        DeleteFromShoppingListEventHandler.class})
@ActiveProfiles("test")
@Slf4j
public class ShoppingListEventHandlerTest {

    @Autowired
    private ShoppingListEventHandler eventHandler;
    @MockBean
    private ShoppingListRepository repository;

    @Test
    void handleEvent_ADD_Test() {

        ShoppingListEvent event = ShoppingListEvent.builder()
                .shoppingEventType(ShoppingEventType.ADD)
                .productId("1234")
                .userId("123")
                .time(LocalDateTime.now())
                .count(10)
                .id(1)
                .build();

        eventHandler.handle(event);

        verify(repository).deleteAllByUserIdAndProductId("123", "1234");
        verify(repository).save(ShoppingListEntity.builder()
                        .id(event.id())
                        .time(event.time())
                        .count(event.count())
                        .userId(event.userId())
                        .productId(event.productId())
                .build());
    }

    @Test
    void handleEvent_REMOVE_Test() {

        ShoppingListEvent event = ShoppingListEvent.builder()
                .shoppingEventType(ShoppingEventType.REMOVE)
                .productId("1234")
                .userId("123")
                .time(LocalDateTime.now())
                .id(1)
                .build();

        eventHandler.handle(event);

        verify(repository).deleteAllByUserIdAndProductId("123", "1234");
    }

}
