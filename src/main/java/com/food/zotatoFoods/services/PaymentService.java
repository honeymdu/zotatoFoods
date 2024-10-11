package com.food.zotatoFoods.services;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.Payment;
import com.food.zotatoFoods.entites.enums.PaymentStatus;

public interface PaymentService {

    public void processPayment(Order order);

    public Payment CreateNewPayment(Order order);

    public void updatePaymentStatus(Payment payment, PaymentStatus status);

}
