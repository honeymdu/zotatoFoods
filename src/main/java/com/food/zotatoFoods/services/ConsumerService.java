package com.food.zotatoFoods.services;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.zotatoFoods.dto.CartDto;
import com.food.zotatoFoods.dto.CreateOrderRequest;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.dto.PreOrderRequestDto;
import com.food.zotatoFoods.dto.PrePaidOrderRequestsDto;
import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;

public interface ConsumerService {

    OrderRequestsDto createOrderRequest(Long RestaurantId, CreateOrderRequest createOrderRequest);

    PrePaidOrderRequestsDto createPrePaidOrderRequest(Long RestaurantId, CreateOrderRequest createOrderRequest);

    Boolean rateRestaurant(Long RestaurantId, Double rating);

    Consumer createNewConsumer(User user);

    Consumer getConsumerById(Long consumerId);

    Page<Restaurant> getAllRestaurant(PageRequest pageRequest);

    Consumer getCurrentConsumer();

    CartDto viewCart(Long RestaurantId);

    CartDto PrepareCart(Long RestaurantId, Long MenuItemId);

    CartDto removeCartItem(Long CartId, Long cartItemId);

    void clearCart(Long RestaurantId);

    Menu viewMenuByRestaurantId(Long RestaurantId);

    PreOrderRequestDto viewPreOrderRequest(Long RestaurantId, Point UserLocation);

    Boolean PreProcessPayment();

}
