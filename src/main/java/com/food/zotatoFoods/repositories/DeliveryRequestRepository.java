package com.food.zotatoFoods.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.zotatoFoods.entites.DeliveryRequest;

@Repository
public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest, Long> {

    Optional<DeliveryRequest> findByOrderId(Long orderId);

}
