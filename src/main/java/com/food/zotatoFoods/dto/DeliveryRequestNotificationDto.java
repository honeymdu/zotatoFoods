package com.food.zotatoFoods.dto;

import org.locationtech.jts.geom.Point;

import com.food.zotatoFoods.entites.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryRequestNotificationDto {

    private Long id;
    private Point PickupLocation;
    private Point DropLocation;
    private Order order;

}
