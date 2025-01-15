package com.food.zotatoFoods.strategies.impl;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.DeliveryFareGetDto;
import com.food.zotatoFoods.services.DistanceService;
import com.food.zotatoFoods.strategies.DeliveryFareCalculationStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryFareDefaultFareCalculationStrategy implements DeliveryFareCalculationStrategy {

    private final DistanceService distanceService;

    @Override
    public double calculateDeliveryFees(DeliveryFareGetDto deliveryFareGetDto) {
        double distance = distanceService.CalculateDistance(deliveryFareGetDto.getPickupLocation(),
                deliveryFareGetDto.getDropLocation());
        return distance * RIDE_FARE_MULTIPLYIER;
    }

}
