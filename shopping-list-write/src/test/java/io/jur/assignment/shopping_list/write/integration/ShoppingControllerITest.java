package io.jur.assignment.shopping_list.write.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jur.assignment.events.Event;
import io.jur.assignment.events.EventType;
import io.jur.assignment.events.shopping.ShoppingListEvent;
import io.jur.assignment.shopping_list.write.dto.AddToShoppingListRequest;
import io.jur.assignment.shopping_list.write.dto.DeleteFromShoppingListRequest;
import io.jur.assignment.shopping_list.write.model.ShoppingEventEntity;
import io.jur.assignment.shopping_list.write.model.ShoppingEventType;
import io.jur.assignment.shopping_list.write.repository.ShoppingEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class ShoppingControllerITest extends BaseTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @Autowired
    private ShoppingEventRepository repository;
    @Autowired
    private RedissonClient redissonClient;

    @BeforeEach
    void beforeEach(){
        repository.deleteAll();
        redissonClient.getTopic(EventType.SHOPPING_LIST.getTopicName()).removeAllListeners();
    }

    @Test
    void AddToShoppingListTest() throws Exception {

        //wait for redis event
        CountDownLatch countDownLatch = new CountDownLatch(1);
        redissonClient.getTopic(EventType.SHOPPING_LIST.getTopicName()).addListener(String.class, (channel, msgStr) -> {
            try {
                Event<ShoppingListEvent> shoppingListEvent = json.readValue(msgStr, new TypeReference<>() {
                });
                Assertions.assertThat(shoppingListEvent.getEventType()).isEqualTo(EventType.SHOPPING_LIST);
                Assertions.assertThat(shoppingListEvent.getEventData().shoppingEventType())
                        .isEqualTo(io.jur.assignment.events.shopping.ShoppingEventType.ADD);

                Assertions.assertThat(shoppingListEvent.getEventData().id()).isPositive();
                Assertions.assertThat(shoppingListEvent.getEventData().time()).isNotNull();
                Assertions.assertThat(shoppingListEvent.getEventData().userId()).isEqualTo("123");
                Assertions.assertThat(shoppingListEvent.getEventData().productId()).isEqualTo("1234");
                Assertions.assertThat(shoppingListEvent.getEventData().count()).isEqualTo(10);
                countDownLatch.countDown();
            } catch (JsonProcessingException e) {
                log.error("cannot deserialize json event: ", e);
                throw new RuntimeException(e);
            }
        });

        //Now calling the service
        AddToShoppingListRequest request = AddToShoppingListRequest.builder()
                .userId("123")
                .productId("1234")
                .count(10)
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/shopping/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isOk());

        List<ShoppingEventEntity> allEvent = repository.findAll();
        Assertions.assertThat(allEvent.size()).isEqualTo(1);
        Assertions.assertThat(allEvent.get(0).getShoppingEventType()).isEqualTo(ShoppingEventType.ADD);
        Assertions.assertThat(allEvent.get(0).getUserId()).isEqualTo("123");
        Assertions.assertThat(allEvent.get(0).getProductId()).isEqualTo("1234");

        boolean waitResult = countDownLatch.await(5, TimeUnit.SECONDS);

        Assertions.assertThat(waitResult).isTrue();
    }

    @Test
    void RemoveFromShoppingListTest() throws Exception {

        //wait for redis event
        CountDownLatch countDownLatch = new CountDownLatch(1);
        redissonClient.getTopic(EventType.SHOPPING_LIST.getTopicName()).addListener(String.class, (channel, msgStr) -> {
            try {
                Event<ShoppingListEvent> shoppingListEvent = json.readValue(msgStr, new TypeReference<>() {
                });
                Assertions.assertThat(shoppingListEvent.getEventType()).isEqualTo(EventType.SHOPPING_LIST);
                Assertions.assertThat(shoppingListEvent.getEventData().shoppingEventType())
                        .isEqualTo(io.jur.assignment.events.shopping.ShoppingEventType.REMOVE);

                Assertions.assertThat(shoppingListEvent.getEventData().id()).isPositive();
                Assertions.assertThat(shoppingListEvent.getEventData().time()).isNotNull();
                Assertions.assertThat(shoppingListEvent.getEventData().userId()).isEqualTo("123");
                Assertions.assertThat(shoppingListEvent.getEventData().productId()).isEqualTo("1234");
                Assertions.assertThat(shoppingListEvent.getEventData().count()).isEqualTo(0);
                countDownLatch.countDown();

            } catch (JsonProcessingException e) {
                log.error("cannot deserialize json event: ", e);
                throw new RuntimeException(e);
            }
        });

        //Now calling the service
        DeleteFromShoppingListRequest request = DeleteFromShoppingListRequest.builder()
                .userId("123")
                .productId("1234")
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/shopping/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isOk());

        List<ShoppingEventEntity> allEvent = repository.findAll();
        Assertions.assertThat(allEvent.size()).isEqualTo(1);
        Assertions.assertThat(allEvent.get(0).getShoppingEventType()).isEqualTo(ShoppingEventType.REMOVE);
        Assertions.assertThat(allEvent.get(0).getUserId()).isEqualTo("123");
        Assertions.assertThat(allEvent.get(0).getProductId()).isEqualTo("1234");

        boolean waitResult = countDownLatch.await(5, TimeUnit.SECONDS);

        Assertions.assertThat(waitResult).isTrue();
    }


}
