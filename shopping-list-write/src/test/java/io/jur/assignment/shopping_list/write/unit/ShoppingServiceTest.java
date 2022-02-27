package io.jur.assignment.shopping_list.write.unit;


import io.jur.assignment.events.Event;
import io.jur.assignment.events.EventType;
import io.jur.assignment.events.shopping.ShoppingListEvent;
import io.jur.assignment.shopping_list.write.dto.AddToShoppingListRequest;
import io.jur.assignment.shopping_list.write.dto.DeleteFromShoppingListRequest;
import io.jur.assignment.shopping_list.write.event.EventPublisher;
import io.jur.assignment.shopping_list.write.model.ShoppingEventEntity;
import io.jur.assignment.shopping_list.write.model.ShoppingEventType;
import io.jur.assignment.shopping_list.write.repository.ShoppingEventRepository;
import io.jur.assignment.shopping_list.write.service.ShoppingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ShoppingService.class})
@ActiveProfiles("test")
@Slf4j
public class ShoppingServiceTest {

    @Autowired
    private ShoppingService service;
    @MockBean
    private ShoppingEventRepository repository;
    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void addToShoppingListTest() {

        AddToShoppingListRequest request = AddToShoppingListRequest.builder()
                .userId("123")
                .productId("1234")
                .count(10)
                .build();

        when(repository.save(any())).thenReturn(ShoppingEventEntity.builder()
                .id(1)
                .productId("1234")
                .userId("123")
                .count(10)
                .time(LocalDateTime.MAX)
                .shoppingEventType(ShoppingEventType.ADD)
                .build());

        service.addToShoppingList(request);

        verify(repository).save(ShoppingEventEntity.builder()
                .shoppingEventType(ShoppingEventType.ADD)
                .productId("1234")
                .userId("123")
                .count(10)
                .build());

        verify(eventPublisher).sendEvent(Event.builder()
                .eventType(EventType.SHOPPING_LIST)
                .eventData(ShoppingListEvent.builder()
                        .id(1)
                        .userId("123")
                        .productId("1234")
                        .count(10)
                        .shoppingEventType(io.jur.assignment.events.shopping.ShoppingEventType.ADD)
                        .time(LocalDateTime.MAX)
                        .build())
                .build());

    }

    @Test
    void removeFromShoppingListTest() {

        DeleteFromShoppingListRequest request = DeleteFromShoppingListRequest.builder()
                .userId("123")
                .productId("1234")
                .build();

        when(repository.save(any())).thenReturn(ShoppingEventEntity.builder()
                .id(1)
                .productId("1234")
                .userId("123")
                .count(0)
                .time(LocalDateTime.MAX)
                .shoppingEventType(ShoppingEventType.REMOVE)
                .build());

        service.deleteFromShoppingList(request);

        verify(repository).save(ShoppingEventEntity.builder()
                .shoppingEventType(ShoppingEventType.REMOVE)
                .productId("1234")
                .userId("123")
                .count(0)
                .build());

        verify(eventPublisher).sendEvent(Event.builder()
                .eventType(EventType.SHOPPING_LIST)
                .eventData(ShoppingListEvent.builder()
                        .id(1)
                        .userId("123")
                        .productId("1234")
                        .count(0)
                        .shoppingEventType(io.jur.assignment.events.shopping.ShoppingEventType.REMOVE)
                        .time(LocalDateTime.MAX)
                        .build())
                .build());

    }

}
