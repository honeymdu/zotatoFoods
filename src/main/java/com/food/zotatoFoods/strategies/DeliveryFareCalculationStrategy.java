package com.food.zotatoFoods.strategies;

import java.math.BigDecimal;

import com.food.zotatoFoods.dto.DeliveryFareGetDto;

public interface DeliveryFareCalculationStrategy {

    public static final BigDecimal RIDE_FARE_MULTIPLYIER = new BigDecimal(10);

    BigDecimal calculateDeliveryFees(DeliveryFareGetDto deliveryFareGetDto);

}
