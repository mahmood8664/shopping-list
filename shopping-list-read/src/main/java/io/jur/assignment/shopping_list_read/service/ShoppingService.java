package io.jur.assignment.shopping_list_read.service;

import io.jur.assignment.shopping_list_read.model.ShoppingListEntity;
import io.jur.assignment.shopping_list_read.repository.ShoppingListRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShoppingService {

    private final ShoppingListRepository shoppingListRepository;

    public List<ShoppingListEntity> getUserShoppingList(String userId) {
        return shoppingListRepository.findAll(Example.of(ShoppingListEntity.builder()
                .userId(userId)
                .build(), ExampleMatcher.matchingAny()));
    }
}

