package com.food.zotatoFoods.services.impl;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.OrderRequestsRepository;
import com.food.zotatoFoods.services.OrderRequestService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderRequestServiceImpl implements OrderRequestService {

    private final OrderRequestsRepository orderRequestsRepository;

    @Override
    public OrderRequests save(OrderRequests orderRequests) {
        return orderRequestsRepository.save(orderRequests);

    }

    @Override
    public OrderRequests getOrderRequestById(long OrderRequestId) {
        return orderRequestsRepository.findById(OrderRequestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order Request Not found with OrderRequestId =" + OrderRequestId));
    }

}
