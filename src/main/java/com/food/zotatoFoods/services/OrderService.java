package com.food.zotatoFoods.services;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.enums.OrderStatus;

public interface OrderService {

    public Order updateOrderStatus(Long OrderId, OrderStatus orderStatus);

    public Order getOrderById(Long OrderId);

    public Order createOrder(OrderRequests orderRequests);

    public Order cancelOrder(Long OrderId);

}
