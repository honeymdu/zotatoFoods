package com.food.zotatoFoods.entites;

import java.time.LocalDateTime;

import com.food.zotatoFoods.entites.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DeliveryItem", indexes = {
        @Index(name = "idx_deliveryitem", columnList = "deliveryPartner_id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String deliveryAddress;
    private LocalDateTime deliveryTime;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @OneToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;
    @ManyToOne
    private DeliveryPartner deliveryPartner; 

}
