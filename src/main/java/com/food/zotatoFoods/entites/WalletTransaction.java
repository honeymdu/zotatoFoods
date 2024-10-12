package com.food.zotatoFoods.entites;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.food.zotatoFoods.entites.enums.TransactionMethod;
import com.food.zotatoFoods.entites.enums.TransactionType;

@Entity
@Table(name = "WalletTransaction", indexes = {
        @Index(name = "idx_wallettransaction", columnList = "wallet_id"),
        @Index(name = "idx_wallettransaction_order", columnList = "order_id")
})
@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double Amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionMethod transactionMethod;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;
    @CreationTimestamp
    private LocalDateTime timeStamp;
}