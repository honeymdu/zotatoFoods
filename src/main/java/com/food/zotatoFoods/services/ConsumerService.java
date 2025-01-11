package com.food.zotatoFoods.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.zotatoFoods.dto.CreateOrderRequest;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.CartItem;
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

    public Cart PrepareCart(Long RestaurantId, Long MenuItemId);

    public Cart removeCartItem(Long CartId, CartItem cartItem);

    public void clearCart(Long CartId);

    public Menu viewMenuByRestaurantId(Long RestaurantId);

}
