package com.food.zotatoFoods.entites;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Cart", indexes = {
        @Index(name = "idx_cart_restaurant_id", columnList = "restaurant_id"),
        @Index(name = "idx_cart_validcart", columnList = "ValidCart"),
        @Index(name = "idx_cart_user_id", columnList = "user_id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "consumer_id", nullable = false)
    private Consumer consumer;
    @OneToOne
    private Restaurant restaurant;
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();
    private Double totalPrice;
    private Double deliveryFee;
    private Double foodAmount;
    private Boolean ValidCart;

}
