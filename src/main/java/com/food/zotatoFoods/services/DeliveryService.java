package com.food.zotatoFoods.services;

import com.food.zotatoFoods.entites.Order;

public interface DeliveryService {

    public void accecptDeliveryRequest(Order order);

    public void cancelDeliveryRequest(Order order);

    public void updateDeliveryStatus(Order order);

    public void viewOrderDetails(Order order);
    

}
