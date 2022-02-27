package io.jur.assignment.shopping_list_read.repository;

import io.jur.assignment.shopping_list_read.model.ShoppingListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * shopping list repository
 */
@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity, Integer> {

    void deleteAllByUserIdAndProductId(String userId, String productId);

}
