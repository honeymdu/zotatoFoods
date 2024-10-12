package com.food.zotatoFoods.services;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;

public interface ConsumerService {

    public OrderDto createOrderRequest(Long CartId);

    public Restaurant rateRestaurant(Long RestaurantId, Integer rating);

    public Consumer createNewConsumer(User user);

    public Consumer getConsumerById(Long consumerId);

    

}
