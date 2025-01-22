package com.food.zotatoFoods.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.zotatoFoods.entites.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByConsumerIdAndRestaurantIdAndValidCart(Long id, Long restaurantId, boolean b);

}
