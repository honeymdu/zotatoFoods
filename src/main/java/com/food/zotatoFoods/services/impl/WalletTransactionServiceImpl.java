package com.food.zotatoFoods.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.Wallet;
import com.food.zotatoFoods.entites.WalletTransaction;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.WalletRepository;
import com.food.zotatoFoods.repositories.WalletTransactionRepository;
import com.food.zotatoFoods.services.WalletTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletRepository walletRepository;

    @Override
    public void CreateNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }

    @Override
    public List<WalletTransaction> getAllWalletTransactionByUser(User user) {
        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with User Id  " + user.getId()));
        return walletTransactionRepository.findAllByWalletId(wallet.getId());
    }

}
