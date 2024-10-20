package com.food.zotatoFoods.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.repositories.OrderRequestsRepository;
import com.food.zotatoFoods.services.OrderRequestService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderRequestServiceImpl implements OrderRequestService {

    private final OrderRequestsRepository orderRequestsRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderRequestsDto save(OrderRequests orderRequests) {
        orderRequestsRepository.save(orderRequests);
        return modelMapper.map(orderRequests, OrderRequestsDto.class);

    }

}
