package com.food.zotatoFoods.entites;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import com.food.zotatoFoods.entites.enums.OrderStatus;
import com.food.zotatoFoods.entites.enums.PaymentMethod;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_order", indexes = {
        @Index(name = "idx_order_user_id", columnList = "consumer_id"),
        @Index(name = "idx_order_orderstatus", columnList = "orderStatus"),
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
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @CreationTimestamp
    private LocalDateTime OrderCreationTime;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

}
