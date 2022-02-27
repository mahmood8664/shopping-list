package io.jur.assignment.shopping_list_read.unit;

import io.jur.assignment.shopping_list_read.model.ShoppingListEntity;
import io.jur.assignment.shopping_list_read.repository.ShoppingListRepository;
import io.jur.assignment.shopping_list_read.service.ShoppingService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ShoppingService.class})
@ActiveProfiles("test")
@Slf4j
public class ShoppingServiceTest {

    @Autowired
    private ShoppingService service;
    @MockBean
    private ShoppingListRepository repository;

    @Test
    void getUserShoppingListTest() {

        List<ShoppingListEntity> resList = List.of(ShoppingListEntity.builder()
                        .id(1)
                        .productId("1234")
                        .userId("123")
                        .count(10)
                        .time(LocalDateTime.now())
                        .build(),
                ShoppingListEntity.builder()
                        .id(1)
                        .productId("12345")
                        .userId("123")
                        .count(5)
                        .time(LocalDateTime.now())
                        .build(),
                ShoppingListEntity.builder()
                        .id(1)
                        .productId("123456")
                        .userId("123")
                        .count(1)
                        .time(LocalDateTime.now())
                        .build());

        Example<ShoppingListEntity> example = Example.of(ShoppingListEntity.builder()
                .userId("123")
                .build(), ExampleMatcher.matchingAny());
        when(repository.findAll(example)).thenReturn(resList);

        List<ShoppingListEntity> userShoppingList = service.getUserShoppingList("123");
        verify(repository).findAll(example);
        Assertions.assertThat(userShoppingList).isEqualTo(resList);
    }

}
