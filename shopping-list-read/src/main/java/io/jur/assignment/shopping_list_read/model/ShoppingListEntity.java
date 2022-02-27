package io.jur.assignment.shopping_list_read.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data model for shopping list
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "shopping_list_read", indexes = {@Index(name = "USER_ID_INDEX", columnList = "user_id"), @Index(name = "TIME_INDEX", columnList = "time")})
public class ShoppingListEntity {

    @Id
    private int id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "count")
    private int count;

    @Column(name = "time")
    private LocalDateTime time;

    @Version
    private long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingListEntity that = (ShoppingListEntity) o;
        return id == that.id && count == that.count && version == that.version &&
                Objects.equals(userId, that.userId) && Objects.equals(productId, that.productId) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, count, time, version);
    }
}
