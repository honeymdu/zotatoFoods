package com.food.zotatoFoods.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.WalletDto;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.Wallet;
import com.food.zotatoFoods.entites.WalletTransaction;
import com.food.zotatoFoods.entites.enums.TransactionMethod;
import com.food.zotatoFoods.entites.enums.TransactionType;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.WalletRepository;
import com.food.zotatoFoods.services.WalletService;
import com.food.zotatoFoods.services.WalletTransactionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;
    private final WalletTransactionService walletTransactionService;

    @Override
    @Transactional
    public WalletDto addMoneyToWallet(User user, Double amount, String transactionId, Order order,
            TransactionMethod transactionMethod) {
        Wallet wallet = findWalletByUser(user);
        wallet.setBalance(wallet.getBalance() + (amount));

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .TransactionId(transactionId)
                .order(order)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .Amount(amount)
                .build();

        walletTransactionService
                .CreateNewWalletTransaction(walletTransaction);

        return modelMapper.map(walletRepository.save(wallet), WalletDto.class);
    }

    @Override
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Order order,
            TransactionMethod transactionMethod) {
        Wallet wallet = findWalletByUser(user);
        wallet.setBalance(wallet.getBalance() - amount);
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .TransactionId(transactionId)
                .order(order)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .Amount(amount)
                .build();

        // wallet.getWalletTransaction().add(walletTransaction);
        walletTransactionService
                .CreateNewWalletTransaction(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdrawAllMyMoneyFromWallet'");
    }

    @Override
    public Wallet findWalletById(Long WalletId) {
        return walletRepository.findById(WalletId)
                .orElseThrow(() -> new ResourceNotFoundException("wallet Not Found for WalletId" + WalletId));
    }

    @Override
    public WalletDto createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(0.0);
        return modelMapper.map(walletRepository.save(wallet), WalletDto.class);

    }

    @Override
    public Wallet findWalletByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("wallet Not Found for UserId" + user.getId()));
    }

}
