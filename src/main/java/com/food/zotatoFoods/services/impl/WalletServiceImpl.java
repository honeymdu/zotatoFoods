package com.food.zotatoFoods.services.impl;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.Wallet;
import com.food.zotatoFoods.entites.enums.TransactionMethod;
import com.food.zotatoFoods.services.WalletService;

@Service
public class WalletServiceImpl implements WalletService {

    @Override
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Order order,
            TransactionMethod transactionMethod) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMoneyToWallet'");
    }

    @Override
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Order order,
            TransactionMethod transactionMethod) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deductMoneyFromWallet'");
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdrawAllMyMoneyFromWallet'");
    }

    @Override
    public Wallet findWalletById(Long WalletId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findWalletById'");
    }

    @Override
    public Wallet createNewWallet(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNewWallet'");
    }

    @Override
    public Wallet findWalletByUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findWalletByUser'");
    }

}
