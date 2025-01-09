package com.food.zotatoFoods.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.entites.User;

@Repository
public interface RestaurantPartnerRepository extends JpaRepository<RestaurantPartner, Long> {

    Optional<RestaurantPartner> findByUser(User user);

}
