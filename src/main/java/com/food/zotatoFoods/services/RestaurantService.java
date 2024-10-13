package com.food.zotatoFoods.services;

import java.sql.Driver;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;
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

    public List<Restaurant> getRestaurantByRestaurantPartner(RestaurantPartner restaurantPartner);

    Boolean removeRestaurant(Long RestaurantId);

}
