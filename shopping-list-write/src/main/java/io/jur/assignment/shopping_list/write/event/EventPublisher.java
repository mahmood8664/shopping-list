package io.jur.assignment.shopping_list.write.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jur.assignment.events.Event;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Event publisher, responsible to send events to event bus
 */
@Component
@Slf4j
@AllArgsConstructor
public class EventPublisher {

    private final RedissonClient redissonClient;
    private final ObjectMapper objectMapper;

    /**
     * send event to event bus
     *
     * @param event {@link  Event}
     */
    public void sendEvent(Event<?> event) {
        try {
            redissonClient.getTopic(event.getEventType().getTopicName()).publish(objectMapper.writeValueAsString(event));
        } catch (Exception e) {
            log.error("cannot send event: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
