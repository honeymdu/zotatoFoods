package com.food.zotatoFoods.strategies.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.DeliveryFareGetDto;
import com.food.zotatoFoods.services.DistanceService;
import com.food.zotatoFoods.strategies.DeliveryFareCalculationStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryFareSurgePricingFareCalclucationStrategy implements DeliveryFareCalculationStrategy {

    private static final BigDecimal SURGE_FACTOR = new BigDecimal(2);
    private final DistanceService distanceService;

    @Override
    public BigDecimal calculateDeliveryFees(DeliveryFareGetDto deliveryFareGetDto) {
        double distance = distanceService.CalculateDistance(deliveryFareGetDto.getPickupLocation(),
                deliveryFareGetDto.getDropLocation());
        return RIDE_FARE_MULTIPLYIER.multiply(SURGE_FACTOR).multiply(new BigDecimal(distance));
    }

}
