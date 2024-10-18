package com.food.zotatoFoods.services.impl;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.repositories.ConsumerRepository;
import com.food.zotatoFoods.services.ConsumerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;

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
        Consumer consumer = Consumer.builder().user(user).rating(0.0).build();
        return consumerRepository.save(consumer);
    }

    @Override
    public Consumer getConsumerById(Long consumerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getConsumerById'");
    }

}
