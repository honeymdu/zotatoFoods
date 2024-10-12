package com.food.zotatoFoods.entites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CartItem", indexes = {
        @Index(name = "idx_cartitem_cart_id", columnList = "cart_id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;
    private Integer quantity;
    private Double totalPrice;
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

}
