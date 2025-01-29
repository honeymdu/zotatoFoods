package com.food.zotatoFoods.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.enums.OrderRequestStatus;

@Repository
public interface OrderRequestsRepository extends JpaRepository<OrderRequests, Long> {

    List<OrderRequests> findByRestaurantId(Long restaurantId);

    List<OrderRequests> findByRestaurantIdAndOrderRequestStatus(Long restaurantId, OrderRequestStatus pending);

}
