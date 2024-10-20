package com.food.zotatoFoods.services;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.OrderRequests;

public interface OrderService {

    public Order updateOrderStatus(Long OrderId);

    public Order viewOrder(Long OrderId);

    public Order createOrder(OrderRequests orderRequests);

    public Order cancelOrder(Long OrderId);

}
