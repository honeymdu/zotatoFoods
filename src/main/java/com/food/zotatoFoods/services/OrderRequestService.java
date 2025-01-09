package com.food.zotatoFoods.services;

import com.food.zotatoFoods.entites.OrderRequests;

public interface OrderRequestService {

    OrderRequests save(OrderRequests orderRequests);

    OrderRequests getOrderRequestById(long OrderRequestId);

}
