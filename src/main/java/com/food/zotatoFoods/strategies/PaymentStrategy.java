package com.food.zotatoFoods.strategies;

import com.food.zotatoFoods.entites.Payment;

public interface PaymentStrategy {

    Double PLATFORM_COMMISSION = 0.3;

    void ProcessPayment(Payment payment);

}
