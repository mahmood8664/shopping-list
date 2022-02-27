package io.jur.assignment.shopping_list.write.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * shopping event entity
 */
@Entity
@Table(name = "shopping_event_write")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * user id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * product id
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * number of product items
     */
    @Column(name = "count")
    private int count;

    /**
     * type of event, add or remove
     */
    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private ShoppingEventType shoppingEventType;

    /**
     * date of insert
     */
    @Column(name = "time")
    private LocalDateTime time;

    @PrePersist
    protected void onCreate() {
        time = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingEventEntity that = (ShoppingEventEntity) o;
        return id == that.id && count == that.count && Objects.equals(userId, that.userId) &&
                Objects.equals(productId, that.productId) && shoppingEventType == that.shoppingEventType &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, count, shoppingEventType, time);
    }
}
