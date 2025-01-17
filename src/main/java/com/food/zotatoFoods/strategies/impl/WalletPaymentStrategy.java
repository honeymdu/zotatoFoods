package com.food.zotatoFoods.strategies.impl;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.Payment;
import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.entites.enums.PaymentStatus;
import com.food.zotatoFoods.entites.enums.TransactionMethod;
import com.food.zotatoFoods.repositories.PaymentRepository;
import com.food.zotatoFoods.services.WalletService;
import com.food.zotatoFoods.strategies.PaymentStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

        private final WalletService walletService;
        private final PaymentRepository paymentRepository;

        @Override
        public void ProcessPayment(Payment payment) {
                RestaurantPartner restaurantPartner = payment.getOrder().getRestaurant().getRestaurantPartner();
                Consumer consumer = payment.getOrder().getConsumer();
                // Wallet driverwallet = walletService.findWalletById(driver.getId());
                double platform_commission = payment.getAmount() * PLATFORM_COMMISSION;
                walletService.deductMoneyFromWallet(consumer.getUser(), payment.getAmount(), null, payment.getOrder(),
                                TransactionMethod.ORDER);

                double drivercut = payment.getAmount() - platform_commission;

                walletService.addMoneyToWallet(restaurantPartner.getUser(), drivercut, null,
                                payment.getOrder(),
                                TransactionMethod.ORDER);
                payment.setPaymentStatus(PaymentStatus.CONFIRMED);
                paymentRepository.save(payment);

        }
}
