package com.food.zotatoFoods.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.Wallet;
import com.food.zotatoFoods.entites.WalletTransaction;
import com.food.zotatoFoods.repositories.WalletTransactionRepository;
import com.food.zotatoFoods.services.WalletService;
import com.food.zotatoFoods.services.WalletTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletService walletService;

    @Override
    public void CreateNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }

    @Override
    public List<WalletTransaction> getAllWalletTransactionByUser(User user) {
        Wallet wallet = walletService.findWalletByUser(user);
        return walletTransactionRepository.findAllByWalletId(wallet.getId());
    }

}
