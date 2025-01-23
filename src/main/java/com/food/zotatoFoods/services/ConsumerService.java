package com.food.zotatoFoods.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.zotatoFoods.dto.CartDto;
import com.food.zotatoFoods.dto.CreateOrderRequest;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;

public interface ConsumerService {

    public OrderRequestsDto createOrderRequest(Long CartId, CreateOrderRequest createOrderRequest);

    public Boolean rateRestaurant(Long RestaurantId, Double rating);

    public Consumer createNewConsumer(User user);

    public Consumer getConsumerById(Long consumerId);

    public Page<Restaurant> getAllRestaurant(PageRequest pageRequest);

    public Consumer getCurrentConsumer();

    public CartDto viewCart(Long RestaurantId);

    public CartDto PrepareCart(Long RestaurantId, Long MenuItemId);

    public CartDto removeCartItem(Long CartId, Long cartItemId);

    public void clearCart(Long RestaurantId);

    public Menu viewMenuByRestaurantId(Long RestaurantId);

}
