package com.food.zotatoFoods.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.WalletTransaction;

public interface RestaurantService {

    Restaurant getRestaurantById(Long restaurantId);

    Page<MenuItem> getMenuFromRestaurant(Long restaurantId, Pageable pageable, String sortBy);

    Restaurant getRestaurant(Long restaurantId);

    Order acceptOrderRequest(Long orderRequestId);

    Order cancelOrderRequest(Long orderRequestId);

    List<Order> getALlOrders(Long restaurantId);

    Restaurant getProfile(Long restaurantId);

    List<MenuItem> getMenuItems(Long restaurantId);

    List<Order> getAllOrderRequest(Long restaurantId);

    List<WalletTransaction> getAllWalletTransaction(Long restaurantId);

    public Restaurant save(Restaurant restaurant);

    public Page<Restaurant> findAllRestaurant(Pageable pageRequest);

    Restaurant getRestaurantByUser(User user);

    Boolean removeRestaurant(Restaurant restaurant);

}
