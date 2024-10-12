package com.food.zotatoFoods.entites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "OrderItem", indexes = {
        @Index(name = "idx_orderitem_order_id", columnList = "order_id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;
    private Integer quantity;
    private Double totalPrice;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
