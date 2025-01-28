package com.food.zotatoFoods.services;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.zotatoFoods.dto.DeliveryPartnerDto;
import com.food.zotatoFoods.entites.DeliveryPartner;

public interface DeliveryPartnerService {

    void rateDeliveryPartner(Long UserId, Double rating);

    void acceptDeliveryRequest(Long deliveryRequestId);

    void cancelDeliveryRequest(Long deliveryRequestId);

    void completeOrderDelivery(Long deliveryRequestId, String consumerOtp);

    DeliveryPartnerDto save(DeliveryPartner deliveryPartner);

    Page<DeliveryPartner> getAllDeliveryPartner(PageRequest pageRequest);

    void pickupOrderFromRestaurant(Long deliveryRequestId, String restaurantOTP);

    DeliveryPartner getCurrentDeliveryPartner();

    Point getCurrentLocation();

}
