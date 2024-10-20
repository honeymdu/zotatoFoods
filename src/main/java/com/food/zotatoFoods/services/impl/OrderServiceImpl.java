package com.food.zotatoFoods.services.impl;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.services.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    @Override
    public Order updateOrderStatus(Long OrderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderStatus'");
    }

    @Override
    public Order viewOrder(Long OrderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewOrder'");
    }

    @Override
    public Order createOrder(OrderRequests orderRequests) {

        return null;
    }

    @Override
    public Order cancelOrder(Long OrderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelOrder'");
    }

}
