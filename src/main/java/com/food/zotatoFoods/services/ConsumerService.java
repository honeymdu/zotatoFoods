package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.enums.PaymentMethod;

public interface ConsumerService {

    public OrderRequestsDto createOrderRequest(Long CartId,PaymentMethod paymentMethod);

    public Restaurant rateRestaurant(Long RestaurantId, Integer rating);

    public Consumer createNewConsumer(User user);

    public Consumer getConsumerById(Long consumerId);

    

}
