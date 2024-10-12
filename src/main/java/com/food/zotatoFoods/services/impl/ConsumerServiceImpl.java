package com.food.zotatoFoods.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.services.CartService;
import com.food.zotatoFoods.services.ConsumerService;
import com.food.zotatoFoods.services.OrderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final CartService cartService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Override
    public OrderDto createOrderRequest(Long CartId) {
        return null;
    }

    @Override
    public Restaurant rateRestaurant(Long RestaurantId, Integer rating) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rateRestaurant'");
    }

    @Override
    public Consumer createNewConsumer(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNewConsumer'");
    }

    @Override
    public Consumer getConsumerById(Long consumerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getConsumerById'");
    }

}
