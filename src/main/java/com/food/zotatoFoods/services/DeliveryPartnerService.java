package com.food.zotatoFoods.services;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.zotatoFoods.dto.DeliveryPartnerDto;
import com.food.zotatoFoods.entites.DeliveryPartner;

public interface DeliveryPartnerService {

    public void rateDeliveryPartner(Long UserId, Double rating);

    public void acceptDeliveryRequest(Long deliveryRequestId);

    public void cancelDeliveryRequest(Long deliveryRequestId);

    public void completeOrderDelivery(Long deliveryRequestId, String consumerOtp);

    public DeliveryPartnerDto save(DeliveryPartner deliveryPartner);

    public Page<DeliveryPartner> getAllDeliveryPartner(PageRequest pageRequest);

    public void pickupOrderFromRestaurant(Long deliveryRequestId, String restaurantOTP);

    public DeliveryPartner getCurrentDeliveryPartner();

    public Point getCurrentLocation();

}
