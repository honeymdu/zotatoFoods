package com.food.zotatoFoods.services.impl;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.Payment;
import com.food.zotatoFoods.entites.enums.PaymentStatus;
import com.food.zotatoFoods.services.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Override
    public void processPayment(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processPayment'");
    }

    @Override
    public Payment CreateNewPayment(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'CreateNewPayment'");
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePaymentStatus'");
    }

}
