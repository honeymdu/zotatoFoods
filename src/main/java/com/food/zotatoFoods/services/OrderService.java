package com.food.zotatoFoods.services;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.enums.OrderStatus;

public interface OrderService {

    Order updateOrderStatus(Long OrderId, OrderStatus orderStatus);

    Order getOrderById(Long OrderId);

    Order createOrder(OrderRequests orderRequests);

    Order cancelOrder(Long OrderId);

    Order saveOrder(Order order);

}
