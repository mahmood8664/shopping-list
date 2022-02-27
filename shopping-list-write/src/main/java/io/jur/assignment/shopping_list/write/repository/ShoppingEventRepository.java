package io.jur.assignment.shopping_list.write.repository;

import io.jur.assignment.shopping_list.write.model.ShoppingEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Shopping event repository
 */
@Repository
public interface ShoppingEventRepository extends JpaRepository<ShoppingEventEntity, Integer> {
}
