package com.food.zotatoFoods.strategies.impl;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.DeliveryFareGetDto;
import com.food.zotatoFoods.services.DistanceService;
import com.food.zotatoFoods.strategies.DeliveryFareCalculationStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryFareSurgePricingFareCalclucationStrategy implements DeliveryFareCalculationStrategy {

    private static final Double SURGE_FACTOR = 2.0;
    private final DistanceService distanceService;

    @Override
    public Double calculateDeliveryFees(DeliveryFareGetDto deliveryFareGetDto) {
        double distance = distanceService.CalculateDistance(deliveryFareGetDto.getPickupLocation(),
                deliveryFareGetDto.getDropLocation());
        return RIDE_FARE_MULTIPLYIER * SURGE_FACTOR * distance;
    }

}
