package com.food.zotatoFoods.dto;

import java.util.List;

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
public class WalletDto {

    private Long id;
    private Double Balance;
    private UserDto user;
    private List<WalletTransactionDto> WalletTransaction;


}
