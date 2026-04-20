package com.food.zotatoFoods.services;

import com.food.zotatoFoods.entites.DeliveryPartner;
import com.food.zotatoFoods.entites.DeliveryRequest;

import java.util.List;

public interface NotificationService {
    void notifyDeliveryPartners(List<DeliveryPartner> partners, DeliveryRequest deliveryRequest);
    void notifyRestaurantOtp(DeliveryRequest deliveryRequest);
    void notifyConsumerOtp(DeliveryRequest deliveryRequest);
}
