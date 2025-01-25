package com.food.zotatoFoods.strategies;

import com.food.zotatoFoods.dto.DeliveryFareGetDto;

public interface DeliveryFareCalculationStrategy {

    public static final Double RIDE_FARE_MULTIPLYIER = 10.0;

    Double calculateDeliveryFees(DeliveryFareGetDto deliveryFareGetDto);

}
