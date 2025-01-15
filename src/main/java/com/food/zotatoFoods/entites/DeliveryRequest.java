package com.food.zotatoFoods.entites;

import java.time.LocalDateTime;
import org.locationtech.jts.geom.Point;

import com.food.zotatoFoods.entites.enums.DeliveryRequestStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class DeliveryRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Point PickupLocation;
    private Point DropLocation;
    private LocalDateTime deliveryTime;
    private DeliveryRequestStatus deliveryRequestStatus;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @OneToOne
    private DeliveryPartner deliveryPartner;
    private String restaurantOtp;
    private String consumerOtp;

}
