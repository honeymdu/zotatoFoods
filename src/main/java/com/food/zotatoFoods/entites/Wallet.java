package com.food.zotatoFoods.entites;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Wallet", indexes = {
        @Index(name = "idx_wallet_user_id", columnList = "user_id")
})
@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double Balance;
    @OneToOne(fetch = FetchType.LAZY ,optional = false)
    private User user;
    @OneToMany(mappedBy = "wallet",fetch = FetchType.LAZY)
    private List<WalletTransaction> WalletTransaction;


}