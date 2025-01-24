package com.food.zotatoFoods.services;

import java.math.BigDecimal;

import org.locationtech.jts.geom.Point;

import com.food.zotatoFoods.dto.PreOrderRequestDto;
import com.food.zotatoFoods.entites.Cart;

public interface PreOrderRequestService {

    BigDecimal calculateTotalPrice(PreOrderRequestDto preOrderRequestDto);

    // Function to validate the preorder request (e.g., check if restaurant is
    // active, if user exists)
    void validatePreOrderRequest(PreOrderRequestDto preOrderRequestDto);

    PreOrderRequestDto createPreOrderRequest(Cart cart, Point UserLocation);

    boolean isDeliveryAddressValid(PreOrderRequestDto preOrderRequestDto);

    BigDecimal calculateDeliverPrice(Cart cart, Point UserLocation);

}
