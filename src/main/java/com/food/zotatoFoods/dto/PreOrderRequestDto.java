package com.food.zotatoFoods.dto;

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
@Builder
public class PreOrderRequestDto {

    private Long consumerId;
    private Long restaurantId;
    private CartDto cart;
    private Double foodAmount;
    private Double platformFee;
    private Double deliveryFee;
    private Double totalPrice;
    private PointDto currentLocation;

}
