package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.WalletDto;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.Wallet;
import com.food.zotatoFoods.entites.enums.TransactionMethod;

public interface WalletService {

        WalletDto addMoneyToWallet(User user, Double drivercut, String transactionId, Order order,
                        TransactionMethod transactionMethod);

        Wallet deductMoneyFromWallet(User user, Double platform_commission, String transactionId, Order order,
                        TransactionMethod transactionMethod);

        void withdrawAllMyMoneyFromWallet();

        Wallet findWalletById(Long WalletId);

        WalletDto createNewWallet(User user);

        Wallet findWalletByUser(User user);

}
