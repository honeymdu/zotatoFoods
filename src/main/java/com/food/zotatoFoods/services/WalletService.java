package com.food.zotatoFoods.services;

import java.math.BigDecimal;

import com.food.zotatoFoods.dto.WalletDto;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.Wallet;
import com.food.zotatoFoods.entites.enums.TransactionMethod;

public interface WalletService {

        WalletDto addMoneyToWallet(User user, BigDecimal drivercut, String transactionId, Order order,
                        TransactionMethod transactionMethod);

        Wallet deductMoneyFromWallet(User user, BigDecimal platform_commission, String transactionId, Order order,
                        TransactionMethod transactionMethod);

        void withdrawAllMyMoneyFromWallet();

        Wallet findWalletById(Long WalletId);

        WalletDto createNewWallet(User user);

        Wallet findWalletByUser(User user);

}
