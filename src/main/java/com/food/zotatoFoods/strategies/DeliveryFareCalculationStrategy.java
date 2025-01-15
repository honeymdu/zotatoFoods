package com.food.zotatoFoods.strategies;

import com.food.zotatoFoods.dto.DeliveryFareGetDto;

public interface DeliveryFareCalculationStrategy {

    public static final double RIDE_FARE_MULTIPLYIER = 10;

    double calculateDeliveryFees(DeliveryFareGetDto deliveryFareGetDto);

}
