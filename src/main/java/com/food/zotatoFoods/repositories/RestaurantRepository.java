package com.food.zotatoFoods.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByIdAndRestaurantPartner(Long restaurantId, RestaurantPartner restaurantPartner);

    List<Restaurant> findByRestaurantPartner(RestaurantPartner restaurantPartner);

    Optional<Restaurant> findByNameAndRestaurantPartner(String name, RestaurantPartner restaurantPartner);

    Page<Restaurant> findByIsAvailableAndIsVarified(PageRequest pageRequest, boolean i, boolean b);

}