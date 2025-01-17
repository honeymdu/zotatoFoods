package com.food.zotatoFoods.strategies;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.food.zotatoFoods.strategies.impl.DeliveryFareDefaultFareCalculationStrategy;
import com.food.zotatoFoods.strategies.impl.DeliveryFareSurgePricingFareCalclucationStrategy;
import com.food.zotatoFoods.strategies.impl.DeliveryPartnerMatchingHighestRatingDeliveryPartnerStartegy;
import com.food.zotatoFoods.strategies.impl.DeliveryPartnerMatchingNearestDeliveryPartnerStartegy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliveryStrategyManager {

    private final DeliveryFareDefaultFareCalculationStrategy deliveryFareDefaultFareCalculationStrategy;
    private final DeliveryFareSurgePricingFareCalclucationStrategy deliveryFareSurgePricingFareCalclucationStrategy;
    private final DeliveryPartnerMatchingHighestRatingDeliveryPartnerStartegy deliveryPartnerMatchingHighestRatingDeliveryPartnerStartegy;
    private final DeliveryPartnerMatchingNearestDeliveryPartnerStartegy deliveryPartnerMatchingNearestDeliveryPartnerStartegy;

    public DeliveryFareCalculationStrategy deliveryFareCalculationStrategy() {

        LocalTime SurgerStartTime = LocalTime.of(18, 0, 0);

        LocalTime SurgerEndTime = LocalTime.of(21, 0, 0);

        LocalTime currenTime = LocalTime.now();

        boolean isSurgerTime;

        if (currenTime.isAfter(SurgerStartTime) && currenTime.isBefore(SurgerEndTime)) {
            isSurgerTime = true;
        } else {
            isSurgerTime = false;
        }

        if (isSurgerTime) {
            return deliveryFareSurgePricingFareCalclucationStrategy;
        } else {
            return deliveryFareDefaultFareCalculationStrategy;
        }
    }

    public DeliveryPartnerMatchingStrategy deliveryPartnerMatchingStrategy(double restaurantRating) {
        if (restaurantRating >= 4.8) {
            return deliveryPartnerMatchingHighestRatingDeliveryPartnerStartegy;
        } else {
            return deliveryPartnerMatchingNearestDeliveryPartnerStartegy;
        }
    }

}

// if (ConsumerRating >= 4.8)