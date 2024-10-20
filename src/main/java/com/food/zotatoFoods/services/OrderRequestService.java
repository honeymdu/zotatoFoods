package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.entites.OrderRequests;

public interface OrderRequestService {

    OrderRequestsDto save(OrderRequests orderRequests);

    
} 
