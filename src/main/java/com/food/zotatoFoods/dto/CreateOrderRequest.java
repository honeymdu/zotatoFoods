package com.food.zotatoFoods.dto;

import org.locationtech.jts.geom.Point;

import com.food.zotatoFoods.entites.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    private Point UserLocation;

    private PaymentMethod paymentMethod;

}
