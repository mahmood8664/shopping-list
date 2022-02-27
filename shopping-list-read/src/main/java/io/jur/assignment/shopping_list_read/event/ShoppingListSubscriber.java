package io.jur.assignment.shopping_list_read.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jur.assignment.events.Event;
import io.jur.assignment.events.EventType;
import io.jur.assignment.events.shopping.ShoppingListEvent;
import io.jur.assignment.shopping_list_read.event.shopping.ShoppingListEventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * subscriber to events
 */
@Component
@AllArgsConstructor
@Slf4j
public class ShoppingListSubscriber {

    private final RedissonClient redissonClient;
    private final ObjectMapper objectMapper;
    private final ShoppingListEventHandler shoppingListEventHandler;

    @PostConstruct
    void doSubscribe() {
        subscribe();//do subscribe
    }

    public void subscribe() {
        redissonClient.getTopic(EventType.SHOPPING_LIST.getTopicName()).addListener(String.class, (channel, msgStr) -> {
            try {
                Event<ShoppingListEvent> shoppingListEvent = objectMapper.readValue(msgStr, new TypeReference<>() {
                });
                log.info("message received: {}", shoppingListEvent);
                shoppingListEventHandler.handle(shoppingListEvent.getEventData());
            } catch (JsonProcessingException e) {
                log.error("cannot convert json string into event", e);
                throw new RuntimeException(e);
            }
        });
    }

}
