package com.food.zotatoFoods.strategies.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.food.zotatoFoods.dto.DeliveryFareGetDto;
import com.food.zotatoFoods.entites.DeliveryPartner;
import com.food.zotatoFoods.repositories.DeliveryPartnerRepository;
import com.food.zotatoFoods.strategies.DeliveryPartnerMatchingStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliveryPartnerMatchingHighestRatingDeliveryPartnerStartegy implements DeliveryPartnerMatchingStrategy {

    private final DeliveryPartnerRepository deliveryPartnerRepository;

    @Override
    public List<DeliveryPartner> findMatchingDeliveryPartner(DeliveryFareGetDto deliveryFareGetDto) {
        return deliveryPartnerRepository.findTenNearByTopRatedDeliveryPartner(deliveryFareGetDto.getPickupLocation());
    }

}
