package io.jur.assignment.shopping_list.write.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jur.assignment.events.Event;
import io.jur.assignment.events.EventData;
import io.jur.assignment.events.EventType;
import io.jur.assignment.events.shopping.ShoppingListEvent;
import io.jur.assignment.shopping_list.write.event.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {EventPublisher.class})
@ActiveProfiles("test")
@Slf4j
public class EventPublisherTest {

    @Autowired
    private EventPublisher eventPublisher;
    @MockBean
    private RedissonClient redissonClient;
    @Mock
    private RTopic rTopic;
    @MockBean
    private ObjectMapper objectMapper;

    @Test
    void publishEventTest() throws JsonProcessingException {

        when(redissonClient.getTopic(EventType.SHOPPING_LIST.getTopicName())).thenReturn(rTopic);
        when(objectMapper.writeValueAsString(any())).thenReturn("json value");
        Event<EventData> event = Event.builder()
                .eventType(EventType.SHOPPING_LIST)
                .eventData(ShoppingListEvent.builder()
                        .id(1)
                        .userId("123")
                        .productId("1234")
                        .count(10)
                        .time(LocalDateTime.MAX)
                        .build())
                .build();
        eventPublisher.sendEvent(event);

        verify(objectMapper).writeValueAsString(event);
        rTopic.publish("json value");

    }

}
