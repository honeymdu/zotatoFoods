package com.food.zotatoFoods.services;

import org.locationtech.jts.geom.Point;

import com.food.zotatoFoods.dto.PreOrderRequestDto;
import com.food.zotatoFoods.entites.Cart;

public interface PreOrderRequestService {

    Double calculateTotalPrice(PreOrderRequestDto preOrderRequestDto);

    // Function to validate the preorder request (e.g., check if restaurant is
    // active, if user exists)
    void validatePreOrderRequest(Long restaurantId, Long ConsumerId, Cart cart);

    PreOrderRequestDto createPreOrderRequest(Cart cart, Point UserLocation);

    boolean isDeliveryAddressValid(Point UserLocation);

    Double calculateDeliverPrice(Cart cart, Point UserLocation);

}
