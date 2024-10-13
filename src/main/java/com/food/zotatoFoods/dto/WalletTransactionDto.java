package com.food.zotatoFoods.dto;

import java.time.LocalDateTime;

import com.food.zotatoFoods.entites.enums.TransactionMethod;
import com.food.zotatoFoods.entites.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class WalletTransactionDto {

    private Long id;
    private Double Amount;
    private TransactionType transactionType;
    private TransactionMethod transactionMethod;
    private OrderDto order;
    private WalletDto wallet;
    private LocalDateTime timeStamp;

}
