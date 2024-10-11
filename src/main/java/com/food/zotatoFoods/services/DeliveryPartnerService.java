package com.food.zotatoFoods.services;

import com.food.zotatoFoods.entites.User;

public interface DeliveryPartnerService {

    public User rateDeliveryPartner(Long UserId, Integer rating);

    public void acceptOrder(Long orderId);

    public void cancelOrder(Long orderId);

    public void pickupOrderFromRestaurant(Long orderId, String restaurantOTP);

    public void startOrderRide(Long orderId);

    public void endOrderRide(Long orderId, String otp);

}
