package com.food.zotatoFoods.services;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.Restaurant;

public interface ConsumerService {

    public Order placeOrder(Long CartId);

    public Restaurant rateRestaurant(Long RestaurantId, Integer rating);

}
