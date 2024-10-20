package com.food.zotatoFoods.strategies;

import java.util.List;

import com.food.zotatoFoods.entites.DeliveryPartner;

public interface DeliveryPartnerMatchingStrategy {
    List<DeliveryPartner> findMatchingDeliveryPartner();
}