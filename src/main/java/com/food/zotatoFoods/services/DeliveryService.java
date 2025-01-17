package com.food.zotatoFoods.services;

import java.util.concurrent.ExecutionException;

import com.food.zotatoFoods.entites.DeliveryRequest;
import com.food.zotatoFoods.entites.Order;

public interface DeliveryService {

    public void AssignDeliveryPartner() throws InterruptedException, ExecutionException;

    DeliveryRequest createDeliveryRequest(Order order);

    DeliveryRequest getDeliveryRequestByOrderId(Long orderId);

}
