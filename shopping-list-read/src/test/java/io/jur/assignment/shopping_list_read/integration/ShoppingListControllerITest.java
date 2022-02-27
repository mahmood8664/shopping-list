package io.jur.assignment.shopping_list_read.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jur.assignment.events.Event;
import io.jur.assignment.events.EventData;
import io.jur.assignment.events.EventType;
import io.jur.assignment.events.shopping.ShoppingEventType;
import io.jur.assignment.events.shopping.ShoppingListEvent;
import io.jur.assignment.shopping_list_read.dto.ShoppingItem;
import io.jur.assignment.shopping_list_read.dto.UserShoppingListDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class ShoppingListControllerITest extends BaseTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @Autowired
    private RedissonClient redissonClient;

    @Test
    void shoppingListTest() throws Exception {

        sendEventToResit();
        Thread.sleep(2000);
        //Now we get shopping list from db

        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/shopping/123")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        UserShoppingListDto res = json.readValue(resultActions.andReturn().getResponse().getContentAsString(), UserShoppingListDto.class);

        Assertions.assertThat(res.userId()).isEqualTo("123");
        Assertions.assertThat(res.shoppingItems().size()).isEqualTo(2);
        Assertions.assertThat(res.shoppingItems().get(0)).isIn(ShoppingItem.builder()
                        .date(LocalDateTime.of(2022, 1, 1, 1, 1, 1))
                        .count(10)
                        .productId("1234")
                        .build(),
                ShoppingItem.builder()
                        .date(LocalDateTime.of(2022, 1, 1, 1, 1, 1))
                        .count(23)
                        .productId("aaa")
                        .build());

        Assertions.assertThat(res.shoppingItems().get(1)).isIn(ShoppingItem.builder()
                        .date(LocalDateTime.of(2022, 1, 1, 1, 1, 1))
                        .count(10)
                        .productId("1234")
                        .build(),
                ShoppingItem.builder()
                        .date(LocalDateTime.of(2022, 1, 1, 1, 1, 1))
                        .count(23)
                        .productId("aaa")
                        .build());
    }

    private void sendEventToResit() throws JsonProcessingException, InterruptedException {
        LocalDateTime dt = LocalDateTime.of(2022, 1, 1, 1, 1, 1);
        Event<EventData> event1 = Event.builder()
                .eventType(EventType.SHOPPING_LIST)
                .eventData(ShoppingListEvent.builder()
                        .time(dt)
                        .count(1)
                        .productId("1234")
                        .userId("123")
                        .shoppingEventType(ShoppingEventType.ADD)
                        .id(1)
                        .build())
                .build();

        //change event1 count to 10
        Event<EventData> event2 = Event.builder()
                .eventType(EventType.SHOPPING_LIST)
                .eventData(ShoppingListEvent.builder()
                        .time(dt)
                        .count(10)
                        .productId("1234")
                        .userId("123")
                        .shoppingEventType(ShoppingEventType.ADD)
                        .id(2)
                        .build())
                .build();

        //add event 3
        Event<EventData> event3 = Event.builder()
                .eventType(EventType.SHOPPING_LIST)
                .eventData(ShoppingListEvent.builder()
                        .time(dt)
                        .count(23)
                        .productId("aaa")
                        .userId("123")
                        .shoppingEventType(ShoppingEventType.ADD)
                        .id(3)
                        .build())
                .build();

        //add event 4
        Event<EventData> event4 = Event.builder()
                .eventType(EventType.SHOPPING_LIST)
                .eventData(ShoppingListEvent.builder()
                        .time(dt)
                        .count(30)
                        .productId("bbb")
                        .userId("123")
                        .shoppingEventType(ShoppingEventType.ADD)
                        .id(4)
                        .build())
                .build();

        //remove event 4
        Event<EventData> event5 = Event.builder()
                .eventType(EventType.SHOPPING_LIST)
                .eventData(ShoppingListEvent.builder()
                        .time(dt)
                        .productId("bbb")
                        .userId("123")
                        .shoppingEventType(ShoppingEventType.REMOVE)
                        .id(5)
                        .build())
                .build();

        redissonClient.getTopic(EventType.SHOPPING_LIST.getTopicName()).publish(json.writeValueAsString(event1));
        Thread.sleep(200);
        redissonClient.getTopic(EventType.SHOPPING_LIST.getTopicName()).publish(json.writeValueAsString(event3));
        Thread.sleep(200);
        redissonClient.getTopic(EventType.SHOPPING_LIST.getTopicName()).publish(json.writeValueAsString(event4));
        Thread.sleep(200);
        redissonClient.getTopic(EventType.SHOPPING_LIST.getTopicName()).publish(json.writeValueAsString(event2));
        Thread.sleep(200);
        redissonClient.getTopic(EventType.SHOPPING_LIST.getTopicName()).publish(json.writeValueAsString(event5));
    }


}
