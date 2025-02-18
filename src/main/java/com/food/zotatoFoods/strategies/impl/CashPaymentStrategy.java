package com.food.zotatoFoods.strategies.impl;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.DeliveryPartner;
import com.food.zotatoFoods.entites.Payment;
import com.food.zotatoFoods.entites.enums.PaymentStatus;
import com.food.zotatoFoods.entites.enums.TransactionMethod;
import com.food.zotatoFoods.repositories.PaymentRepository;
import com.food.zotatoFoods.services.DeliveryService;
import com.food.zotatoFoods.services.WalletService;
import com.food.zotatoFoods.strategies.PaymentStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;
    private final DeliveryService deliveryService;

    @Override
    public void ProcessPayment(Payment payment) {
        DeliveryPartner deliveryPartner = deliveryService.getDeliveryRequestByOrderId(payment.getOrder().getId())
                .getDeliveryPartner();
        // Wallet driverwallet = walletService.findWalletById(driver.getId());
        Double platform_commission = payment.getAmount() * PLATFORM_COMMISSION;
        walletService.deductMoneyFromWallet(deliveryPartner.getUser(), platform_commission, null, payment.getOrder(),
                TransactionMethod.ORDER);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }

}
