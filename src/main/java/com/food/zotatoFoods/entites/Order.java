package com.food.zotatoFoods.entites;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

import com.food.zotatoFoods.entites.enums.OrderStatus;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_order", indexes = {
        @Index(name = "idx_order_user_id", columnList = "consumer_id"),
        @Index(name = "idx_order_deliverypartner_id", columnList = "deliveryPartner_id"),
        @Index(name = "idx_order_orderstatus", columnList = "orderStatus"),
        @Index(name = "idx_order_payment_id", columnList = "payment_id"),
        @Index(name = "idx_order_restaurant_id", columnList = "restaurant_id"),
        @Index(name = "idx_order_ordercreationtime", columnList = "OrderCreationTime")
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "consumer_id", nullable = false)
    private Consumer consumer;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    private Double foodAmount;
    private Double platformFee;
    private Double totalPrice;
    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point pickupLocation;
    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point dropoffLocation;
    @OneToOne
    private Payment payment;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @CreationTimestamp
    private LocalDateTime OrderCreationTime;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @OneToOne
    private DeliveryPartner deliveryPartner;
    private LocalDateTime DeliveryTime;
    private String otp;


}
