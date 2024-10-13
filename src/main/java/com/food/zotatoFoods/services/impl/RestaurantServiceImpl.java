package com.food.zotatoFoods.services.impl;

import java.sql.Driver;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.WalletTransaction;
import com.food.zotatoFoods.repositories.RestaurantRepository;
import com.food.zotatoFoods.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRestaurantById'");
    }

    @Override
    public Page<MenuItem> getMenuFromRestaurant(Long restaurantId, Pageable pageable, String sortBy) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMenuFromRestaurant'");
    }

    @Override
    public Restaurant getRestaurant(Long restaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRestaurant'");
    }

    @Override
    public Order acceptOrderRequest(Long orderRequestId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'acceptOrderRequest'");
    }

    @Override
    public Order cancelOrderRequest(Long orderRequestId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelOrderRequest'");
    }

    @Override
    public List<Order> getALlOrders(Long restaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getALlOrders'");
    }

    @Override
    public Restaurant getProfile(Long restaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProfile'");
    }

    @Override
    public List<MenuItem> getMenuItems(Long restaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMenuItems'");
    }

    @Override
    public List<Order> getAllOrderRequest(Long restaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrderRequest'");
    }

    @Override
    public List<WalletTransaction> getAllWalletTransaction(Long restaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllWalletTransaction'");
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Page<Restaurant> findAllRestaurant(Pageable pageRequest) {
        return restaurantRepository.findAll(pageRequest);
    }

    @Override
    public List<Restaurant> getRestaurantByRestaurantPartner(RestaurantPartner restaurantPartner) {
        return restaurantRepository.findByRestaurantPartner(restaurantPartner);
    }

    @Override
    public Boolean removeRestaurant(Long RestaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeRestaurant'");
    }

}
