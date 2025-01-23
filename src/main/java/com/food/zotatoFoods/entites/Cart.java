package com.food.zotatoFoods.entites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Cart", indexes = {
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
    @JoinColumn(name = "user_id", nullable = false)
    private Consumer consumer;
    @ManyToOne
    private Restaurant restaurant;
    private Double totalPrice;
    private Boolean validCart;

}
