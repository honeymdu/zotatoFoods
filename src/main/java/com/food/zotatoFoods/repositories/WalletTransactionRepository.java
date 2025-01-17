package com.food.zotatoFoods.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.zotatoFoods.entites.WalletTransaction;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    List<WalletTransaction> findAllByWalletId(Long id);

} 


