package com.food.zotatoFoods.strategies;

import org.springframework.stereotype.Component;

import com.food.zotatoFoods.entites.enums.PaymentMethod;
import com.food.zotatoFoods.strategies.impl.CashPaymentStrategy;
import com.food.zotatoFoods.strategies.impl.WalletPaymentStrategy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod) {

        return switch (paymentMethod) {
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
            default -> throw new RuntimeException("Invalid Payment Method");
        };
    }

}
