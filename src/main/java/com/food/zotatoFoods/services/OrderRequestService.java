package com.food.zotatoFoods.services;

import java.util.List;

import org.locationtech.jts.geom.Point;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.enums.PaymentMethod;

public interface OrderRequestService {

    OrderRequests save(OrderRequests orderRequests);

    OrderRequests getOrderRequestById(Long OrderRequestId);

    List<Order> getAllOrderRequestByRestaurantId(Long restaurantId);

    OrderRequests OrderRequest(Long CartId, PaymentMethod paymentMethod, Point UserLocation);
}
