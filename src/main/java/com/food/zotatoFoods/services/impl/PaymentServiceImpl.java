package com.food.zotatoFoods.services.impl;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.Payment;
import com.food.zotatoFoods.entites.enums.PaymentStatus;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.PaymentRepository;
import com.food.zotatoFoods.services.PaymentService;
import com.food.zotatoFoods.strategies.PaymentStrategyManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void processPayment(Order order) {
        Payment payment = paymentRepository.findByOrder(order).orElseThrow(
                () -> new ResourceNotFoundException("Payment not found for the Order with Id " + order.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).ProcessPayment(payment);

    }

    @Override
    public Payment CreateNewPayment(Order order) {
        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(order.getPaymentMethod())
                .amount(order.getTotalPrice())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
    }

}
