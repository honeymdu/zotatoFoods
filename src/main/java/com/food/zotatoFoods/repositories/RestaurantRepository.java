package com.food.zotatoFoods.repositories;

import java.util.List;
import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByIdAndRestaurantPartner(Long restaurantId, RestaurantPartner restaurantPartner);

    List<Restaurant> findByRestaurantPartner(RestaurantPartner restaurantPartner);

    Optional<Restaurant> findByNameAndRestaurantPartner(String name, RestaurantPartner restaurantPartner);

    Page<Restaurant> findByIsAvailableAndIsVarified(PageRequest pageRequest, boolean i, boolean b);

    List<Restaurant> findByIsAvailableAndIsVarified(boolean b, boolean c);

    @Query(value = "SELECT r.* "
            + "FROM Restaurant r " +
            "WHERE r.is_available = true AND r.is_varified = true AND ST_DWithin(r.restaurant_location , :userLocation, 15000) "
            + "LIMIT 10", nativeQuery = true)
    List<Restaurant> findTopTenNearestRestaurant(Point userLocation);

    boolean existsByIdAndIsAvailableAndIsVarified(Long restaurantId, boolean b, boolean c);

}