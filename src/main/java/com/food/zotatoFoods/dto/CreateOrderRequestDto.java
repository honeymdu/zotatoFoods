package com.food.zotatoFoods.dto;

import com.food.zotatoFoods.entites.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDto {

    private PointDto userLocation;

    private PaymentMethod paymentMethod;

}
