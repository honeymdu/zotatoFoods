package com.food.zotatoFoods.services;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;

public interface RestaurantService {

    Restaurant AddNewRestaurant(RestaurantPartner restaurantPartner, RestaurantDto restaurantDto);

    Restaurant getRestaurantById(Long restaurantId);

    Menu viewMenu(Long restaurantId);

    Restaurant viewProfile(Long restaurantId);

    public Page<Restaurant> findAllRestaurant(Pageable pageRequest);

    public List<Restaurant> getRestaurantByRestaurantPartner(RestaurantPartner restaurantPartner);

    Boolean IsRestaurentAlreadyExist(Restaurant newRestaurant);

    Restaurant save(Restaurant restaurant);

    Page<Restaurant> getAllVarifiedRestaurant(PageRequest pageRequest);

    List<Restaurant> getAllVarifiedAndActiveRestaurant();

    List<Restaurant> getTopTenNearestRestaurants(Point UserSrc);

}
