package com.food.zotatoFoods.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.zotatoFoods.entites.RestaurantPartner;

@Repository
public interface RestaurantPartnerRepository extends JpaRepository<RestaurantPartner, Long> {

}
