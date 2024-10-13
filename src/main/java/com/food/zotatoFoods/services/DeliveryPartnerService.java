package com.food.zotatoFoods.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.zotatoFoods.dto.DeliveryPartnerDto;
import com.food.zotatoFoods.entites.DeliveryPartner;
import com.food.zotatoFoods.entites.User;

public interface DeliveryPartnerService {

    public User rateDeliveryPartner(Long UserId, Integer rating);

    public void acceptOrder(Long orderId);

    public void cancelOrder(Long orderId);

    public void pickupOrderFromRestaurant(Long orderId, String restaurantOTP);

    public void startOrderRide(Long orderId);

    public void endOrderRide(Long orderId, String otp);

    public DeliveryPartnerDto AddNewDeliveryPartner(DeliveryPartner deliveryPartner);

    public Page<DeliveryPartner> getAllDeliveryPartner(PageRequest pageRequest);

    public Boolean removeDeliveryPartner(Long userId);

}
