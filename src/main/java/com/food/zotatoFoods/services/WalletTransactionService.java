package com.food.zotatoFoods.services;

import java.util.List;

import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.WalletTransaction;

public interface WalletTransactionService {

    void CreateNewWalletTransaction(WalletTransaction WalletTransaction);

    List<WalletTransaction> getAllWalletTransactionByUser(User user);

}
