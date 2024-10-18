package com.food.zotatoFoods.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.WalletDto;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.Wallet;
import com.food.zotatoFoods.entites.enums.TransactionMethod;
import com.food.zotatoFoods.repositories.WalletRepository;
import com.food.zotatoFoods.services.WalletService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;

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
    public WalletDto createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(0.0);
        return modelMapper.map(walletRepository.save(wallet), WalletDto.class);

    }

    @Override
    public Wallet findWalletByUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findWalletByUser'");
    }

}
