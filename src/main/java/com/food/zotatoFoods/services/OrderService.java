package com.food.zotatoFoods.services;

import com.food.zotatoFoods.entites.Order;

public interface OrderService {

    public Order updateOrderStatus(Long OrderId);

    public Order viewOrder(Long OrderId);

    public Order createOrder(Long CartId);

    public Order cancelOrder(Long OrderId);

}
