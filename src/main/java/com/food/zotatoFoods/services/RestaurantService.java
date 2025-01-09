package com.food.zotatoFoods.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.entites.WalletTransaction;

public interface RestaurantService {

    Boolean AddNewRestaurant(Restaurant newRestaurant);

    Restaurant getRestaurantById(Long restaurantId);
    
    Restaurant getRestaurantByIdAndRestaurantPartner(Long restaurantId, RestaurantPartner restaurantPartner);
    
    Page<MenuItem> getMenuFromRestaurant(Long restaurantId, Pageable pageable, String sortBy);

    Order acceptOrderRequest(Long orderRequestId);

    Order cancelOrderRequest(Long orderRequestId);

    List<Order> getALlOrders(Long restaurantId);

    Restaurant getProfile(Long restaurantId);

    List<MenuItem> getMenuItems(Long restaurantId);

    List<Order> getAllOrderRequest(Long restaurantId);

    List<WalletTransaction> getAllWalletTransaction(Long restaurantId);

    public Page<Restaurant> findAllRestaurant(Pageable pageRequest);

    public List<Restaurant> getRestaurantByRestaurantPartner(RestaurantPartner restaurantPartner);

    Boolean removeRestaurant(Long RestaurantId);

    Boolean IsRestaurentAlreadyExist(Restaurant newRestaurant);

    Boolean save(Restaurant restaurant);

    Page<Restaurant> getAllVarifiedRestaurant(PageRequest pageRequest);

}
