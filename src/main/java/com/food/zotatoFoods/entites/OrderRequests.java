package com.food.zotatoFoods.entites;

import java.math.BigDecimal;

import com.food.zotatoFoods.entites.enums.OrderRequestStatus;
import com.food.zotatoFoods.entites.enums.PaymentMethod;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OrderRequests", indexes = {
        @Index(name = "idx_orderrequests_cart_id", columnList = "cart_id"),
        @Index(name = "idx_orderrequests", columnList = "restaurant_id"),
        @Index(name = "idx_orderrequests_consumer_id", columnList = "consumer_id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    private BigDecimal foodAmount;
    private BigDecimal platformFee;
    private BigDecimal totalPrice;
    private BigDecimal deliveryFee;
    @Enumerated(EnumType.STRING)
    private OrderRequestStatus orderRequestStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "restaurant_id")
    private Restaurant restaurant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "consumer_id")
    private Consumer consumer;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

}
